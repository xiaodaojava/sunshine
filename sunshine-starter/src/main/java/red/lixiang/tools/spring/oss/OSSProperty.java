package red.lixiang.tools.spring.oss;

/**
 * @Author lixiang
 * @CreateTime 2019/9/11
 **/
public class OSSProperty {
    private String accessKey;
    private String accessSecret;
    private String endpoint;
    private String bucket;

    public String getAccessKey() {
        return accessKey;
    }

    public OSSProperty setAccessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public OSSProperty setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
        return this;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public OSSProperty setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public String getBucket() {
        return bucket;
    }

    public OSSProperty setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }
}
