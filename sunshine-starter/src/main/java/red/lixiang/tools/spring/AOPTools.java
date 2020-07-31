package red.lixiang.tools.spring;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Field;

/**
 * @author lixiang
 * @date 2020/6/28
 **/
public class AOPTools {

    /**
     * 通过JDK动态代理,然后再获取真正的被代理的类
     * @param proxy
     * @return
     */
    public static  Class<?> getTarget(Object proxy)  {
        Field field = null;
        try {
            boolean cglib = false;
            if(proxy.getClass().getSimpleName().contains("CGLIB")){
                field = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
                cglib = true;
            }else {
                field = proxy.getClass().getSuperclass().getDeclaredField("h");
            }
            field.setAccessible(true);
            //获取Proxy对象中的此字段的值
            if(cglib){
                Object dynamicAdvisedInterceptor = field.get(proxy);
                Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
                advised.setAccessible(true);
                Class<?> target = ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTargetClass();
                return target;
            }
            AopProxy mapperProxy = (AopProxy) field.get(proxy);
            Field advised = mapperProxy.getClass().getDeclaredField("advised");
            advised.setAccessible(true);
            ProxyFactory proxyFactory = (ProxyFactory) advised.get(mapperProxy);
            Class<?> proxiedInterface = proxyFactory.getProxiedInterfaces()[0];
            return proxiedInterface;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static  Class<?> getTargetForCglib(Object proxy)  {
//        Field field = null;
//        try {
//            field = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
//            field.setAccessible(true);
//            //获取Proxy对象中的此字段的值
//            AopProxy mapperProxy = (AopProxy) field.get(proxy);
//            Field advised = mapperProxy.getClass().getDeclaredField("advised");
//            advised.setAccessible(true);
//            ProxyFactory proxyFactory = (ProxyFactory) advised.get(mapperProxy);
//            Class<?> proxiedInterface = proxyFactory.getProxiedInterfaces()[0];
//            return proxiedInterface;
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
