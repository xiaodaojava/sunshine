package red.lixiang.tools.spring;

import org.apache.dubbo.config.spring.ReferenceBean;
import org.apache.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import red.lixiang.tools.base.KV;
import red.lixiang.tools.jdk.StringTools;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 把这个类放到了spring的工具包里面,后面的项目可以直接调用
 * @author lixiang
 */
public class ContextHolder  {

    private static ApplicationContext applicationContext;


    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextHolder.applicationContext = applicationContext;
    }
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(name, requiredType);
    }

    public static Boolean isLocal(){
        String activeProfile = applicationContext.getEnvironment().getActiveProfiles()[0];
        return "local".equals(activeProfile)||"dev".equals(activeProfile);
    }

    public static <T> T getDubboBean(Class<T> requiredType){
        ReferenceAnnotationBeanPostProcessor dubboContext = applicationContext.getBean(ReferenceAnnotationBeanPostProcessor.class);
        Collection<ReferenceBean<?>> referenceBeans = dubboContext.getReferenceBeans();
        for (ReferenceBean<?> referenceBean : referenceBeans) {
            Class<?> objectType = referenceBean.getObjectType();
            if(objectType == requiredType){
                return (T)referenceBean.getObject();
            }
        }
        return null;
    }

    public static String getProperty(String key , String  defaultValue){
        if(applicationContext == null ){
            return defaultValue;
        }
        return applicationContext.getEnvironment().getProperty(key, defaultValue);
    }

    public static Map<String,String> getPropertyMap(String key, String defaultValue){
        String properties = getProperty(key,defaultValue);

        return convertMap(properties);
    }

    public static List<KV> getPropertyList(String key, String defaultValue){
        String properties = getProperty(key,defaultValue);
        List<KV> convert = convert(properties);
        return convert;
    }

    public static List<KV> convert(String content) {
        List<KV> result = new ArrayList<>();
        if (!StringTools.isBlank(content)) {
            String[] valueArray = content.split(";");
            for (String sysDicValue : valueArray) {
                String[] sysDicValueArray = sysDicValue.split(":");
                if (sysDicValueArray.length == 2) {
                    KV sys = new KV();
                    try {
                        sys.setId(Long.parseLong(sysDicValueArray[0]));
                    } catch (Exception e) {

                    }
                    sys.setValue(sysDicValueArray[0]);
                    sys.setName(sysDicValueArray[1]);
                    result.add(sys);
                }
            }
        }
        return result;
    }

    public static Map<String,String> convertMap(String content) {
        List<KV> result = convert(content);
        return result.stream().collect(Collectors.toMap(KV::getValue,KV::getName));
    }

    public static void setDubboContext(String key , String value){
        RpcContext.getContext().setAttachment(key,value);
    }

    public static String getDubboContext(String key ){
        return RpcContext.getContext().getAttachment(key);
    }

    public static <T> Optional<T> getBean(Class<T> requiredType) throws BeansException {
        return Optional.ofNullable(applicationContext.getBean(requiredType));
    }

    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }
}
