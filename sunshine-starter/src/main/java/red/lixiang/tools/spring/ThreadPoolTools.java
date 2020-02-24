package red.lixiang.tools.spring;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author lixiang
 * @CreateTime 2019/8/19
 **/
public class ThreadPoolTools {

    /** 用单例模式来构建 */
    private static final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

    static {
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("customerExecutors");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
    }
    public static Executor getExecutor(){
        return executor;
    }
}
