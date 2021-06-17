package red.lixiang.tools.jdk.thread;

public class ThreadTools {

    public static void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
