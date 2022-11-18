package red.lixiang.tools.spring.event;

import com.alibaba.fastjson.JSON;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import red.lixiang.tools.spring.transaction.TransactionTools;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 领域事件相关服务
 */
public class LocalEventService {


    @Resource
    private LocalEventRouter localEventRouter;

    @Resource
    private LocalEventRepo localEventRepo;

    private static final ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(
            40,
            40,
            5,
            TimeUnit.MINUTES,
            new LinkedBlockingDeque<>(1024)
    );

    private static final int MAX_RETRY = 10;

    /**
     * 一个事务支持有多个领域事件
     */
    ThreadLocal<List<Runnable>> LOCAL_EVENT_ACTIONS = new ThreadLocal<>();


    public void resetLocalEventAction(){
        LOCAL_EVENT_ACTIONS.remove();
    }

    public void addLocalEventAction(LocalEventModel localEventModel){
        List<Runnable> runnables = LOCAL_EVENT_ACTIONS.get();
        if(runnables == null){
            runnables = new ArrayList<>();
            LOCAL_EVENT_ACTIONS.set(runnables);
        }
        runnables.add(()-> executeLocalEvent(localEventModel));
    }

    public void runLocalEventAction(){
        List<Runnable> runnables = LOCAL_EVENT_ACTIONS.get();

        if(runnables == null){
            return;
        }
        resetLocalEventAction();

        for (Runnable runnable : runnables) {
            runnable.run();
        }
    }

    public void publishAndSyncExecuteLocalEvent(String bizId, String taskCode,Object bizData){
        TransactionTools.checkTransaction();
        LocalEventModel  localEventModel = new LocalEventModel();
        localEventModel.setBizId(bizId);
        localEventModel.setTaskCode(taskCode);
        localEventModel.setBizData(JSON.toJSONString(bizData));
        localEventRepo.createTask(localEventModel);
        addLocalEventAction(localEventModel);
    }

    private void executeLocalEvent(LocalEventModel eventModel){
        try {
            localEventRouter.process(eventModel);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 同步器中嵌套事务，会有问题，因此，这个只有在异步的时候才使用。
     * @param runnable
     */
    private void registerAfterCommit(Runnable runnable){
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCompletion(int status) {
                runnable.run();
            }
        });
    }



}
