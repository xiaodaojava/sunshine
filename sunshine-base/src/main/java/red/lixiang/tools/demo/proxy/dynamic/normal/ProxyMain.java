package red.lixiang.tools.demo.proxy.dynamic.normal;

import red.lixiang.tools.demo.proxy.dynamic.Greet;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author lixiang
 * @date 2020/6/16
 **/
public class ProxyMain {
    public static void main(String[] args) {
        // 真正的对象
        Greet greet = new GreetImpl();
        // 代理对象
        InvocationHandler handler = new GreetProxy(greet);
        // 使用的jdk代理生成代理类
        Greet proxy = (Greet)Proxy.newProxyInstance(ProxyMain.class.getClassLoader(), new Class[]{Greet.class}, handler);
        proxy.cheer();
    }
}
