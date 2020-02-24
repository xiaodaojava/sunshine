package red.lixiang.tools.base.canvas.test;

/**
 * @author lixiang
 * @date 2020/1/11
 **/
public class Main {
    public static void main(String[] args) {
        FlightCanvas canvas = new FlightCanvas(30,30);
        Flight flight = new Flight(0,0);
        canvas.addPoint(flight);
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            canvas.print();
        }
    }
}
