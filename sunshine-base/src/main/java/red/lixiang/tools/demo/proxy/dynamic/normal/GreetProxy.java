package red.lixiang.tools.demo.proxy.dynamic.normal;

import red.lixiang.tools.demo.Greet;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author lixiang
 * @date 2020/6/16
 **/
public class GreetProxy implements InvocationHandler {

    /** 被代理的对象,真正的逻辑,还是要请求这个 */
    private final Greet greet;

    public GreetProxy(Greet greet) {
        this.greet = greet;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("在真正调用之前");
        greet.cheer();
        System.out.println("在真正调用之后");
        return null;
    }
}
