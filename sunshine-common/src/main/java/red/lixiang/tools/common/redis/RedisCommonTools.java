package red.lixiang.tools.common.redis;

import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import red.lixiang.tools.jdk.ListTools;
import red.lixiang.tools.jdk.StringTools;

import java.util.List;

/**
 * client 可以多次使用, connect 一次操作只使用一次,用完就close
 * 要有以下功能:
 * 1. scan 扫描keys
 * 2. get 某个key值
 * 3. set 某个key值
 * 4. ex 可以设置key的过期时间
 * @author lixiang
 * @date 2020/3/12
 **/
public class RedisCommonTools {

    private RedisConfig config;

    // private RedisClient client;

    private ScanCursor scanCursor;

    /**
     * 获取当前实例的游标
     * @return
     */
    public ScanCursor getScanCursor(Boolean init){
        if(init){
            return ScanCursor.INITIAL;
        }
        if(scanCursor==null ){
            scanCursor = ScanCursor.INITIAL;
        }
        if(scanCursor.isFinished()){
            return null;
        }
        return scanCursor;
    }

    public RedisCommonTools(RedisConfig config) {
        this.config = config;
        //client = config.getRedisClient();
    }


    public void setKey(String key , String value , Long seconds){
        StatefulRedisConnection<String, String> connect = config.conn();
        connect.sync().set(key,value);
        if(null != seconds){
            connect.sync().expire(key,seconds);
        }
    }

    public RedisValue get(String key){
        StatefulRedisConnection<String, String> connect = config.conn();

        // 获取key的值
        String value = connect.sync().get(key);
        //获取key还有多长时间过期
        Long ttl  = connect.sync().ttl(key);
//        connect.close();
//        client.shutdown();
        return new RedisValue(key,value,ttl);
    }
    public Long del(String key){
        StatefulRedisConnection<String, String> connect = config.conn();

        // 获取key的值
        Long del = connect.sync().del(key);
        //获取key还有多长时间过期
        // connect.close();
//        client.shutdown();
        return del;
    }

    public void ex(String key,Long seconds){
        StatefulRedisConnection<String, String> connect = config.conn();

        // 获取key的值
        connect.sync().expire(key,seconds);
        // connect.close();
//        client.shutdown();
    }

    /**
     * 扫描redis里面的key
     * @param match 匹配的值
     * @param count 要搜索的数量
     * @return
     */
    public List<String> scan(Boolean init,String match,Long count){
        count = count ==null?100L:count;
        ScanArgs scanArgs = ScanArgs.Builder.limit(count);
        if(StringTools.isNotBlank(match)){
            scanArgs.match(match+"*");
        }
        // 去连接获取数据
        StatefulRedisConnection<String, String> connect = config.conn();
        ScanCursor scanCursor = getScanCursor(init);
        if(scanCursor==null){
            return null;
        }
        KeyScanCursor<String> scan = connect.sync().scan(scanCursor, scanArgs);
        this.scanCursor = scan;

        return scan.getKeys();
    }

    public List<String> findAllKeys(){
        List<String> scan = scan(true, null, null);
        while (true){
            List<String> scan1 = scan(false, null, null);
            if(ListTools.isBlank(scan1)){
                break;
            }
            scan.addAll(scan1);
        }
        return scan;
    }

    public void close(){
        config.close();
    }



}
