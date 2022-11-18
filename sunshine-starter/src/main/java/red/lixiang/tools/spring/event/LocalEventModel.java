package red.lixiang.tools.spring.event;

import java.util.Date;

public class LocalEventModel {
    /**
     * ID
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 最后更新时间
     */
    private Date gmtModified;

    /**
     * 任务类型
     */
    private String taskCode;

    /**
     * 业务ID，和taskCode一起需要唯一
     */
    private String bizId;

    /**
     * 业务数据，一般是json格式
     */
    private String bizData;

    /**
     * 状态
     */
    private String status;


    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 重试的次数
     */
    private Integer retryCount;

    /**
     * 备注，一般是失败原因
     */
    private String remark;

    /**
     * 乐观锁
     */
    private Integer version;

    /**
     * 环境
     */
    private String env;

    /**
     * 最大重试次数
     */
    private Integer maxRetry;


    public Long getId() {
        return id;
    }

    public LocalEventModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public LocalEventModel setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public LocalEventModel setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
        return this;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public LocalEventModel setTaskCode(String taskCode) {
        this.taskCode = taskCode;
        return this;
    }

    public String getBizId() {
        return bizId;
    }

    public LocalEventModel setBizId(String bizId) {
        this.bizId = bizId;
        return this;
    }

    public String getBizData() {
        return bizData;
    }

    public LocalEventModel setBizData(String bizData) {
        this.bizData = bizData;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public LocalEventModel setStatus(String status) {
        this.status = status;
        return this;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public LocalEventModel setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public LocalEventModel setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public LocalEventModel setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public LocalEventModel setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public String getEnv() {
        return env;
    }

    public LocalEventModel setEnv(String env) {
        this.env = env;
        return this;
    }

    public Integer getMaxRetry() {
        return maxRetry;
    }

    public LocalEventModel setMaxRetry(Integer maxRetry) {
        this.maxRetry = maxRetry;
        return this;
    }
}
