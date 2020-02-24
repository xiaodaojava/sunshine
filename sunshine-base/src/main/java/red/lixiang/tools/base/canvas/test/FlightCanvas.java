package red.lixiang.tools.base.canvas.test;

import red.lixiang.tools.base.canvas.BaseCanvas;

/**
 * @author lixiang
 * @date 2020/1/11
 **/
public class FlightCanvas extends BaseCanvas {

    public FlightCanvas(int width, int height) {
        super(width, height);
    }

    /**
     * 需要子类实现的停止的方法
     */
    @Override
    protected void doStop() {

    }
}
