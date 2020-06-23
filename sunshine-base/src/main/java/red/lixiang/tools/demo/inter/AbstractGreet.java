package red.lixiang.tools.demo.inter;

import red.lixiang.tools.demo.Greet;

/**
 * @author lixiang
 * @date 2020/6/18
 **/
public abstract class AbstractGreet implements Greet {
    /**
     * 加油的接口定义
     */
    @Override
    public void cheer() {
        System.out.println("这里通常会做一些通用的处理,比如资源初始化,赋初值之类的");
        doCheer();
    }

    /**
     * 留给子类去实现
     */
    public abstract void doCheer();
}
