package red.lixiang.tools.spring;

import org.apache.dubbo.config.spring.ReferenceBean;
import org.apache.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import java.util.Collection;
import java.util.Optional;


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
