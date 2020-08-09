package red.lixiang.tools.spring.processor;


import org.springframework.context.ApplicationContext;
import red.lixiang.tools.spring.ContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lixiang
 * @date 2020/6/27
 **/
public class ProcessorCache {

    public static Map<Class<?>, List<SimpleProcessor>> DOMAIN_PROCESSOR_CACHE = new ConcurrentHashMap<>();

    /**
     * 通过domainCls 获取处理器
     *
     * @param domainCls
     * @return
     */
    public static List<SimpleProcessor> processorFromDomain(Class<?> domainCls) {
        return DOMAIN_PROCESSOR_CACHE.computeIfAbsent(domainCls, cls -> {
            List<SimpleProcessor> list = new ArrayList<>();
            ApplicationContext applicationContext = ContextHolder.getApplicationContext();
            Map<String, Object> processorList = applicationContext.getBeansWithAnnotation(Processor.class);
            SimpleProcessor targetProcessor = null;
            for (Map.Entry<String, Object> entry : processorList.entrySet()) {
                Object processorBean = entry.getValue();
                Processor processor = processorBean.getClass().getAnnotation(Processor.class);
                if (processor.domainType() == domainCls) {
                    targetProcessor = (SimpleProcessor) processorBean;
                    list.add(targetProcessor);
                }
            }
            return list;
        });
    }
}
