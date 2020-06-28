package red.lixiang.tools.demo.proxy.dynamic.custom;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author lixiang
 * @date 2020/6/16
 **/
public class GreetCustomProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("所谓自定义就是,这个代理类里面并没有Greet的真正实现类");
        System.out.println("全都是加强的逻辑");
        return null;
    }
}
