package red.lixiang.tools.demo.proxy.dynamic.custom;

import red.lixiang.tools.demo.Greet;
import red.lixiang.tools.demo.proxy.dynamic.normal.ProxyMain;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author lixiang
 * @date 2020/6/16
 **/
public class ProxyCustomMain {
    public static void main(String[] args) {
        InvocationHandler handler = new GreetCustomProxy();
        Greet proxy = (Greet) Proxy.newProxyInstance(ProxyMain.class.getClassLoader(), new Class[]{Greet.class}, handler);
        proxy.cheer();
    }
}
