package red.lixiang.tools.common.nacos;

/**
 * @author lixiang
 * @date 2020/10/17
 **/
public class NacosServerConfig {

    private String serverAddr;
    private String dataId;
    private String group;

    public String getServerAddr() {
        return serverAddr;
    }

    public NacosServerConfig setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
        return this;
    }

    public String getDataId() {
        return dataId;
    }

    public NacosServerConfig setDataId(String dataId) {
        this.dataId = dataId;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public NacosServerConfig setGroup(String group) {
        this.group = group;
        return this;
    }
}
