package red.lixiang.tools.spring.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import red.lixiang.tools.common.mybatis.BaseMapper;
import red.lixiang.tools.jdk.JSONTools;
import red.lixiang.tools.jdk.StringTools;
import red.lixiang.tools.spring.ContextHolder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static red.lixiang.tools.common.mybatis.MybatisToolCache.DOMAIN_DO_CACHE;


/**
 * @author lixiang
 * @date 2020/3/19
 **/
@Component
public class SimpleDataRedis {


    @Autowired
    private RedisSpringTools redisSpringTools;

    public static final String BIZ = "SIMPLECACHE:";




    public <T> T getFromCacheById(Long id,String domain){
        // 从domain中找DO.class
        Class<?> clazz = DOMAIN_DO_CACHE.get(domain);
        // 拼REDIS-KEY
        String redisKey = domain+":"+"item:"+id;
        // 去查redis
        String value = redisSpringTools.get(redisKey);
        if(StringTools.isBlank(value)){
            // 如果为空的话,去查一遍库
            // todo:这里面可以考虑去加一个分布式锁
            BaseMapper proxy = ContextHolder.getBean(domain + "Mapper", BaseMapper.class);
            Object object = proxy.findById(id);
            if(object==null){
                return null;
            }
            value = JSONTools.toJson(object);
            redisSpringTools.set(redisKey,value);
            return (T) object;
        }
        T t = (T) JSON.parseObject(value, clazz);
        return t;
    }

    /**
     * 把表中全部的数据都刷到redis中,只保留ID
     * @param domain
     */
    public void flushDomain(String domain){
        BaseMapper proxy = ContextHolder.getBean(domain + "Mapper", BaseMapper.class);
        List list = proxy.findAll();
        Class<?> clazz = DOMAIN_DO_CACHE.get(domain);
        List<Long> idList = new ArrayList<>();
        try {
            Field idField = clazz.getDeclaredField("id");
            idField.setAccessible(true);
            for (Object o : list) {
                Object idObj = idField.get(o);
                if(idObj instanceof Long){
                    Long idValue  = (Long) idObj;
                    idList.add(idValue);
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // 出错了就错了, 不处理
        }
        // 往redis中存数据
        String redisKey = BIZ+":"+domain+":ALL";
        redisSpringTools.set(redisKey,JSONTools.toJson(idList));
    }

    /**
     * 把单个的每条数据都刷到redis中
     * @param domain
     */
    public void flushDomainItem(String domain){
        BaseMapper proxy = ContextHolder.getBean(domain + "Mapper", BaseMapper.class);
        List list = proxy.findAll();
        Class<?> clazz = DOMAIN_DO_CACHE.get(domain);
        try {
            Field idField = clazz.getDeclaredField("id");
            idField.setAccessible(true);
            for (Object o : list) {
                Object idObj = idField.get(o);
                if(idObj instanceof Long){
                    Long id  = (Long) idObj;
                    // 往redis中写单个的值
                    String redisKey = domain+":"+"item:"+id;
                    redisSpringTools.set(redisKey,JSONTools.toJson(o));

                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // 出错了就错了, 不处理
        }
    }

    /**
     * 去redis中查那些ID值
     * @param domain
     * @return
     */
    public  List<Long> getAllDomain(String domain){
        String redisKey  = BIZ+":"+domain+":ALL";
        String value = redisSpringTools.get(redisKey);
        List<Long> list = JSON.parseArray(value, Long.class);
        return list;
    }

    public void flushDomainAll(String domain){
        BaseMapper proxy = ContextHolder.getBean(domain + "Mapper", BaseMapper.class);
        List list = proxy.findAllNoDataRule();
        // 往redis中存数据
        String redisKey = BIZ+":"+domain+":ALLDATA";
        redisSpringTools.set(redisKey,JSONTools.toJson(list));
    }

    public <T> List<T> getAllDomainData(String domain,Class<T> cls){
        String redisKey  = BIZ+":"+domain+":ALLDATA";
        String value = redisSpringTools.get(redisKey);
        List<T> list = JSON.parseArray(value, cls);
        return list;
    }





}
