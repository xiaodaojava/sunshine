package red.lixiang.tools.base.canvas.test;

import red.lixiang.tools.base.canvas.BasePoint;

/**
 * @author lixiang
 * @date 2020/1/11
 **/
public class Flight extends BasePoint {

    public Flight(int x, int y) {
        super(x, y);
    }

    /**
     * 需要子类重写的刷新的方法
     */
    @Override
    protected void doRefresh() {
        this.x++;
        this.y++;
    }
}
