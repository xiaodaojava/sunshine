package red.lixiang.tools.spring.transaction;


import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StopWatch;
import red.lixiang.tools.spring.event.LocalEventService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransactionEngine {

    /**
     * 用于立即执行一次使用
     */
    private static LocalEventService localEventService;

    /**
     *    用于注册事务执行使用
     */
    private static final ThreadLocal<List<Runnable>> TRANSACTION_REGISTER_THREAD_LOCAL = new ThreadLocal<>();


    /**
     * 中断标识
     */
    private static final ThreadLocal<Boolean> ABORT_THREAD_LOCAL = new ThreadLocal<>();


    /**
     * 业务事务模板
     */
    private static TransactionTemplate transactionTemplate;

    /**
     * 中止事务
     */
    public static void abort(){
        ABORT_THREAD_LOCAL.set(true);
        // 中止之后，还没有执行的，直接丢弃掉
        TRANSACTION_REGISTER_THREAD_LOCAL.remove();
    }

    /**
     * 重置事务引擎
     */
    public static void reset(){
        ABORT_THREAD_LOCAL.remove();
        TRANSACTION_REGISTER_THREAD_LOCAL.remove();
    }

    /**
     * 注册一个事务到持久化引擎中，
     * @param runnable
     */
    public static void register(Runnable runnable){
        if(TransactionSynchronizationManager.isSynchronizationActive()){
            // 不能在事务中注册，不能套娃
            throw new RuntimeException();
        }
        List<Runnable> runnables = TRANSACTION_REGISTER_THREAD_LOCAL.get();
        if(runnables == null){
            runnables = new ArrayList<>();
            ABORT_THREAD_LOCAL.set(false);
            TRANSACTION_REGISTER_THREAD_LOCAL.set(runnables);
        }
        runnables.add(runnable);
    }

    /**
     * 进行持久化
     */
    public static void persistence(){
        if(TransactionSynchronizationManager.isSynchronizationActive()){
            // 不能在事务中注册，不能套娃
            throw new RuntimeException();
        }

        StopWatch stopWatch = new StopWatch();

        try {
            if(Objects.equals(ABORT_THREAD_LOCAL.get(),Boolean.TRUE)){
                return;
            }

            List<Runnable> runnables = TRANSACTION_REGISTER_THREAD_LOCAL.get();
            if(runnables == null){
                return;
            }

            // 事务真正执行的位置
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    runnables.forEach(Runnable::run);
                }
            });

            // 后置处理
            localEventService.runLocalEventAction();

        }catch (Throwable e){

            throw e;
        }finally {
            reset();
        }

    }


}
