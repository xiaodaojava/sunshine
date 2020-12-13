package red.lixiang.tools.common.mybatis;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lixiang
 * @date 2020/6/23
 **/
public class MybatisToolCache {
    public static Map<Class<?>,String> MAPPER_TABLE_CACHE  = new ConcurrentHashMap<>(50);
    public static Map<String,Class<?>> DOMAIN_QC_CACHE = new ConcurrentHashMap<>(50);
    public static Map<String,Class<?>> DOMAIN_DO_CACHE = new ConcurrentHashMap<>(50);
    public static Map<Object,Class<?>> PROXY_MAPPER_CACHE = new ConcurrentHashMap<>(50);

    public static void cacheDomain(String domain, Class<?> doClass,Class<?> qcClass){
        DOMAIN_DO_CACHE.put(domain,doClass);
        DOMAIN_QC_CACHE.put(domain,qcClass);
    }
    public static void cacheDomain(Class<?> doClass,Class<?> qcClass){
        String domainName = MapperTools.camelTableNameFromCls(doClass);
        DOMAIN_DO_CACHE.put(domainName,doClass);
        DOMAIN_QC_CACHE.put(domainName,qcClass);
    }

    public static void cacheDomain(Class<?> doClass){
        String domainName = MapperTools.camelTableNameFromCls(doClass);
        DOMAIN_DO_CACHE.put(domainName,doClass);
        DOMAIN_QC_CACHE.put(domainName,doClass);
    }
}
