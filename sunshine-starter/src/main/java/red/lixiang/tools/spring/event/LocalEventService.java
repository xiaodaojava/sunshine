package red.lixiang.tools.spring.event;

import java.util.List;

/**
 * 领域事件相关服务
 */
public class LocalEventService {

    private static final int MAX_RETRY = 10;

    /**
     * 一个事务支持有多个领域事件
     */
    ThreadLocal<List<Runnable>> LOCAL_EVENT_ACTIONS = new ThreadLocal<>();


    public void resetLocalEventAction(){
        LOCAL_EVENT_ACTIONS.remove();
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




}
