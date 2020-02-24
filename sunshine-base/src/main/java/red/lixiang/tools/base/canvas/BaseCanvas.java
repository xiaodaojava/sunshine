package red.lixiang.tools.base.canvas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 基础的场景类,里面包含一些点
 *
 * @author lixiang
 * @date 2020/1/11
 **/
public abstract class BaseCanvas {

    protected List<BasePoint> pointList;

    protected char[][] canvas;

    protected int width;
    protected int height;

    public BaseCanvas addPoint(BasePoint point) {
        pointList.add(point);
        return this;
    }

    public BaseCanvas(int width, int height) {
        this.width = width;
        this.height = height;
        this.canvas = new char[width][height];
        pointList = new ArrayList<>();
        stop = false;
    }

    boolean stop;

    public void refresh() {
        if (!stop) {
            for (BasePoint point : pointList) {
                point.refresh();
                if (point.x < width && point.y < height) {
                    canvas[point.x][point.y] = point.symbol;
                }
            }
        }
    }


    public void stop() {
        stop = true;
        for (BasePoint point : pointList) {
            point.needRefresh = false;
        }
        doStop();
    }

    /**
     * 需要子类实现的停止的方法
     */
    protected abstract void doStop();

    public void print() {
        //清空控制台
        System.out.print("\033[H\033[2J");
        System.out.println();
        System.out.println();
        for (char[] canva : canvas) {
            Arrays.fill(canva, ' ');
        }
        this.refresh();

        for (char[] canva : canvas) {
            for (char c : canva) {
                System.out.print(c);
            }
            System.out.print("\n");
        }

    }

}
