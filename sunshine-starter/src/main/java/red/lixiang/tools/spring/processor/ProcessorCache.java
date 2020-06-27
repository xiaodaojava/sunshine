package red.lixiang.tools.spring.processor;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lixiang
 * @date 2020/6/27
 **/
public class ProcessorCache {
    public static Map<Class<?>,SimpleProcessor> DOMAIN_PROCESSOR_CACHE = new ConcurrentHashMap<>();
}
