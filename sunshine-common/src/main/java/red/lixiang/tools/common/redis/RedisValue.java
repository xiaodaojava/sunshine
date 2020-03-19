package red.lixiang.tools.common.redis;

/**
 * @author lixiang
 * @date 2020/3/14
 **/
public class RedisValue {
    private String key ;
    private String value;
    private Long ttl;

    public RedisValue() {
    }

    public RedisValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public RedisValue(String key, String value, Long ttl) {
        this.key = key;
        this.value = value;
        this.ttl = ttl;
    }

    public String getKey() {
        return key;
    }

    public RedisValue setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public RedisValue setValue(String value) {
        this.value = value;
        return this;
    }

    public Long getTtl() {
        return ttl;
    }

    public RedisValue setTtl(Long ttl) {
        this.ttl = ttl;
        return this;
    }
}
