package red.lixiang.tools.spring.event;

import java.util.List;

/**
 * 这里只定义实现
 */
public interface LocalEventRepo {

    /**
     * 创建一个任务
     * @param eventModel
     * @return
     */
    LocalEventModel createTask(LocalEventModel eventModel);

    /**
     * 通过taskCode和BizID查找任务
     * @param taskCode
     * @param bizId
     * @return
     */
    LocalEventModel queryByTaskCodeAndBizId(String taskCode, String bizId);

    /**
     * 查到taskCode对应的待调度的任务
     * @param taskCode
     * @param limit
     * @return
     */
    List<LocalEventModel> queryInitAndFailTask(List<String> taskCode,Integer limit);

    /**
     * 更新到完成
     * @param taskCode
     * @param bizId
     * @param version
     * @return
     */
    Integer updateToFinish(String taskCode, String bizId , Integer version);

    /**
     * 更新到失败
     * @param taskCode
     * @param bizId
     * @param version
     * @return
     */
    Integer updateToFail(String taskCode, String bizId , Integer version);

}
