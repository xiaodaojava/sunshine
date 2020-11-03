package red.lixiang.tools.jdk.thread;


import java.util.concurrent.*;


/**
 * @author https://www.javastudy.cloud
 */
public class ThreadPoolTools {

    /** 线程池维护线程的最少数量 */
    private final int minPoolSize = 10;

    /** 线程池维护线程的最大数量 */
    private final int maxPoolSize = 100;

    /** 线程池维护线程所允许的空闲时间 */
    private final int idleSeconds = 1800;

    /** 线程池所使用的缓冲队列 */
    private int queueBlockSize = 100;

    private ThreadPoolExecutor executor;

    public ThreadPoolTools() {
        this.executor = new ThreadPoolExecutor(minPoolSize, maxPoolSize, idleSeconds,
                TimeUnit.SECONDS, /* 时间单位,秒 */
                new ArrayBlockingQueue<Runnable>(queueBlockSize),
                new ThreadPoolExecutor.CallerRunsPolicy()); /* 重试添加当前加入失败的任务 */
    }

    public void execute(Runnable task) {
        executor.execute(task);
    }

    public <T> Future<T> submit(Callable<T> task){
        return executor.submit(task);
    }
}
