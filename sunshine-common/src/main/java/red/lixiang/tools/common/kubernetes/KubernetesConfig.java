package red.lixiang.tools.common.kubernetes;

import red.lixiang.tools.common.yaml.YamlTools;

/**
 * @author lixiang
 * @date 2020/2/23
 **/
public class KubernetesConfig {
    private String configPath;
    private String userClientKey;
    private String userClientCertificate;
    private String serverCertificateAuthority;



    public KubernetesConfig(String configPath) {
        this.configPath = configPath;
        YamlTools tools = new YamlTools(configPath);
        userClientKey = tools.getValueByKey("users[0].user.client-key-data", null);
        userClientCertificate = tools.getValueByKey("users[0].user.client-certificate-data", null);
        serverCertificateAuthority = tools.getValueByKey("clusters[0].cluster.certificate-authority-data", null);
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
}
