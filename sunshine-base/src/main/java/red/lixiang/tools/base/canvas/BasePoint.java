package red.lixiang.tools.base.canvas;

/**
 * @author lixiang
 * @date 2020/1/11
 **/
public abstract class BasePoint {

    /**
     * x坐标
     */
    protected int x;

    /**
     * y坐标
     */
    protected int y;

    protected char symbol;

    public BasePoint(int x, int y) {
        this(x,y,'#');
    }

    public BasePoint(int x, int y, char symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        needRefresh = true;
    }

    /** 当前点是否还需要刷新 */
    protected boolean needRefresh;

    /**
     * 刷新自己的x,y的坐标为下一个值
     */
     void refresh() {
        if (needRefresh) {
            doRefresh();
        }
    }

    /**
     * 需要子类重写的刷新的方法
     */
    protected abstract void doRefresh();

}
