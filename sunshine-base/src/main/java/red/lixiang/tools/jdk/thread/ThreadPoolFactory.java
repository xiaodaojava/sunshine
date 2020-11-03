package red.lixiang.tools.jdk.thread;

/**
 * @author lixiang
 * @date 2020/11/2
 **/
public class ThreadPoolFactory {


    private static class Instance{
        static ThreadPoolTools tools = new ThreadPoolTools();
    }


    public static ThreadPoolTools get(){
        return Instance.tools;
    }
}
