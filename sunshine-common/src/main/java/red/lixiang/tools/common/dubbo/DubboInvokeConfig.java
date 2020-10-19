package red.lixiang.tools.common.dubbo;

import java.util.Map;

/**
 * @author lixiang
 * @date 2020/10/18
 **/
public class DubboInvokeConfig {

    private String registryAddress;

    /**
     * 直连专用
     */
    private String url;

    /**
     * 服务方法调用超时时间(毫秒)
     */
    private Integer timeout;

    /**
     * random,roundrobin,leastactive 随机，轮询，最少活跃调用
     */
    private String loadbalance;

    /**
     * 是否异步
     */
    private boolean async;

    /**
     * 每服务消费者每服务每方法最大并发调用数
     */
    private int actives;

    /**
     * 重试次数
     */
    private int retries;

    private String interfaceName;

    private String methodName;

    private String group;

    private String version;

    private String[] argTypes;

    private Object[] argObjects;

    private Map<String, String> attachments;

    public String getRegistryAddress() {
        return registryAddress;
    }

    public DubboInvokeConfig setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public DubboInvokeConfig setUrl(String url) {
        this.url = url;
        return this;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public DubboInvokeConfig setTimeout(Integer timeout) {
        this.timeout = timeout;
        return this;
    }

    public String getLoadbalance() {
        return loadbalance;
    }

    public DubboInvokeConfig setLoadbalance(String loadbalance) {
        this.loadbalance = loadbalance;
        return this;
    }

    public boolean isAsync() {
        return async;
    }

    public DubboInvokeConfig setAsync(boolean async) {
        this.async = async;
        return this;
    }

    public int getActives() {
        return actives;
    }

    public DubboInvokeConfig setActives(int actives) {
        this.actives = actives;
        return this;
    }

    public int getRetries() {
        return retries;
    }

    public DubboInvokeConfig setRetries(int retries) {
        this.retries = retries;
        return this;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public DubboInvokeConfig setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public DubboInvokeConfig setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public DubboInvokeConfig setGroup(String group) {
        this.group = group;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public DubboInvokeConfig setVersion(String version) {
        this.version = version;
        return this;
    }

    public String[] getArgTypes() {
        return argTypes;
    }

    public DubboInvokeConfig setArgTypes(String[] argTypes) {
        this.argTypes = argTypes;
        return this;
    }

    public Object[] getArgObjects() {
        return argObjects;
    }

    public DubboInvokeConfig setArgObjects(Object[] argObjects) {
        this.argObjects = argObjects;
        return this;
    }

    public Map<String, String> getAttachments() {
        return attachments;
    }

    public DubboInvokeConfig setAttachments(Map<String, String> attachments) {
        this.attachments = attachments;
        return this;
    }
}
