package red.lixiang.tools.jdk.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lixiang
 * @date 2020/10/28
 **/
public class TimeCache {

    private static Map<String,ObjectWrapper> cache = new ConcurrentHashMap<>(100);

    public static Object setCache(String key,Object obj,long expireTime){
        ObjectWrapper wrapper = new ObjectWrapper();
        wrapper.obj =obj;
        long current = System.currentTimeMillis();
        wrapper.expireStamp= current+expireTime;
        cache.put(key,wrapper);
        return obj;
    }

    public static Object getFromCache(String key){
        ObjectWrapper wrapper = cache.get(key);
        long l = System.currentTimeMillis();
        if(l>wrapper.expireStamp){
            cache.remove(key);
            return null;
        }
        return wrapper.obj;
    }


    static class ObjectWrapper{
        Object obj;
        long expireStamp;
    }


    public static void main(String[] args) {
        TimeCache.setCache("aaaa","bbbb",20000);
        Object aaaa = TimeCache.getFromCache("aaaa");
        System.out.println(aaaa);
        Object aaaa1 = TimeCache.getFromCache("aaaa");
        System.out.println(aaaa1);
    }
}
