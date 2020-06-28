package red.lixiang.tools.demo.proxy.dynamic.normal;

import red.lixiang.tools.demo.Greet;

/**
 * @author lixiang
 * @date 2020/6/16
 **/
public class GreetImpl implements Greet {

    @Override
    public void cheer() {
        System.out.println("加油, 为了美好的明天!");
    }
}
