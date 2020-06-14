package red.lixiang.tools.common.kubernetes.models;

import java.util.List;

/**
 * @author lixiang
 * @date 2020/2/23
 **/
public class Pod {

    /** 容器名称 */
    private String name;

    /** 容器的命名空间 */
    private String namespace;

    /** 容器的创建时间 */
    private String createTime;

    private List<Container> containerList;

    record status(String phase,String podIP,String containerStatuses,String hostIP,String startTime){}


    public List<Container> getContainerList() {
        return containerList;
    }

    public Pod setContainerList(List<Container> containerList) {
        this.containerList = containerList;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Pod setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getName() {
        return name;
    }

    public Pod setName(String name) {
        this.name = name;
        return this;
    }

    public String getNamespace() {
        return namespace;
    }

    public Pod setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }
}
