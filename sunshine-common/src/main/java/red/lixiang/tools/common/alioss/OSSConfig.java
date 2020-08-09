package red.lixiang.tools.common.alioss;

/**
 * @Author lixiang
 * @CreateTime 2019/9/11
 **/
public class OSSConfig {
    private String accessKey;
    private String accessSecret;
    private String endpoint;
    private String bucket;

    public String getAccessKey() {
        return accessKey;
    }

    public OSSConfig setAccessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public OSSConfig setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
        return this;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public OSSConfig setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public String getBucket() {
        return bucket;
    }

    public OSSConfig setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }
}
