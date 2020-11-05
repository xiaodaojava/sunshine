package red.lixiang.tools.common.dubbo;

import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.service.GenericService;
import red.lixiang.tools.common.mybatis.MapperTools;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lixiang
 * @date 2020/6/23
 **/
public class DubboToolCache {

    public static Map<String, ReferenceConfig<GenericService>> DUBBO_SERVICE_CACHE  = new ConcurrentHashMap<>(50);


    public static ReferenceConfig<GenericService> getFromCache(String interfaceName,String methodName){
        String key  = interfaceName+"#"+methodName;
        ReferenceConfig<GenericService> genericService = DUBBO_SERVICE_CACHE.get(key);
        return genericService;
    }





}
