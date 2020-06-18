package red.lixiang.tools.demo.inter;

import red.lixiang.tools.demo.proxy.dynamic.Greet;

/**
 * @author lixiang
 * @date 2020/6/17
 **/
public  class SecondGreet implements Greet {
    /**
     * 加油的接口定义
     */
    @Override
    public void cheer() {
        System.out.println("in abstract");
    }
}
