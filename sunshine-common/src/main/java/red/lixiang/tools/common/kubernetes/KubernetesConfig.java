package red.lixiang.tools.common.kubernetes;

import red.lixiang.tools.common.yaml.YamlTools;

/**
 * @author lixiang
 * @date 2020/2/23
 **/
public class KubernetesConfig {
    /**
     * 当前配置的标识
     */
    private Long id;
    private String tag;
    private String configPath;
    private String userClientKey;
    private String userClientCertificate;
    private String serverCertificateAuthority;
    private String serverApi;
    private String rawContent;

    public KubernetesConfig() {
    }


    public void loadConfigFromStr(String content){
        YamlTools tools = new YamlTools();
        tools.initWithString(content);
        rawContent = content;
        userClientKey = tools.getValueByKey("users[0].user.client-key-data", null);
        userClientCertificate = tools.getValueByKey("users[0].user.client-certificate-data", null);
        serverCertificateAuthority = tools.getValueByKey("clusters[0].cluster.certificate-authority-data", null);
        serverApi = tools.getValueByKey("clusters[0].cluster.server", null);

    }

    public KubernetesConfig(String configPath) {
        this.configPath = configPath;
        YamlTools tools = new YamlTools(configPath);
        userClientKey = tools.getValueByKey("users[0].user.client-key-data", null);
        userClientCertificate = tools.getValueByKey("users[0].user.client-certificate-data", null);
        serverCertificateAuthority = tools.getValueByKey("clusters[0].cluster.certificate-authority-data", null);
        serverApi = tools.getValueByKey("clusters[0].cluster.server", null);

    }

    public String getRawContent() {
        return rawContent;
    }

    public KubernetesConfig setRawContent(String rawContent) {
        this.rawContent = rawContent;
        return this;
    }

    public Long getId() {
        return id;
    }

    public KubernetesConfig setId(Long id) {
        this.id = id;
        return this;
    }

    public String getServerApi() {
        return serverApi;
    }

    public KubernetesConfig setServerApi(String serverApi) {
        this.serverApi = serverApi;
        return this;
    }

    public String getConfigPath() {
        return configPath;
    }

    public KubernetesConfig setConfigPath(String configPath) {
        this.configPath = configPath;
        return this;
    }

    public String getUserClientKey() {
        return userClientKey;
    }

    public KubernetesConfig setUserClientKey(String userClientKey) {
        this.userClientKey = userClientKey;
        return this;
    }

    public String getUserClientCertificate() {
        return userClientCertificate;
    }

    public KubernetesConfig setUserClientCertificate(String userClientCertificate) {
        this.userClientCertificate = userClientCertificate;
        return this;
    }

    public String getServerCertificateAuthority() {
        return serverCertificateAuthority;
    }

    public KubernetesConfig setServerCertificateAuthority(String serverCertificateAuthority) {
        this.serverCertificateAuthority = serverCertificateAuthority;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public KubernetesConfig setTag(String tag) {
        this.tag = tag;
        return this;
    }
}
