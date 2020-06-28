package red.lixiang.tools.spring;

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
            field = proxy.getClass().getSuperclass().getDeclaredField("h");
            field.setAccessible(true);
            //获取Proxy对象中的此字段的值
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
}
