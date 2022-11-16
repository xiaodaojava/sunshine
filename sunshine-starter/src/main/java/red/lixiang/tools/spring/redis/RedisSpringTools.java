package red.lixiang.tools.spring.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Set;
import java.util.concurrent.TimeUnit;


public class RedisSpringTools {

    private Logger logger = LoggerFactory.getLogger(RedisSpringTools.class);

    //各种时间
    public static final Long ONE_HOUR = 60L*60;
    public static final Long TWELVE_HOUR = 60L * 60 * 12;
    public static final Long TEN_DAY = 60L*60*24*10;
    public static final Long THIRTY_DAY = 60L*60*24*30;



    private StringRedisTemplate redisTemplate;

    public RedisSpringTools setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        return this;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (!keys.isEmpty())
            redisTemplate.delete(keys);
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 获取key的剩余时间(秒)
     * @param key
     * @return
     */
    public Long getRemainTime(String key){
        if(exists(key)){
           return redisTemplate.getExpire(key,TimeUnit.SECONDS);
        }
        return 0L;
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public Boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    public Set<String> keys(final String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        return keys;
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public String get(final String key) {
        String result;
        ValueOperations<String , String> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, String value) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            logger.error("set redis error key is {},value is {}",key,value);
            logger.error("the error msg is",e);
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, String value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            logger.error("set redis error key is {},value is {}",key,value);
            logger.error("the error msg is",e);
        }
        return result;
    }

    public boolean expire(final String key, Long expireTime) {
        boolean result = false;
        try {
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            logger.error("expire redis error key is {}",key);
            logger.error("the error msg is",e);
        }
        return result;
    }

    public Long incr(final String key) {

            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            Long increment = operations.increment(key, 1);

        return increment;
    }

    public Long incrByExpireTime(final String key, Long expireTime) {

        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        Long increment = operations.increment(key, 1);
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        return increment;
    }


    public boolean validate(final String redisKey) {
        Object code = get(redisKey);
        if (code == null) {
            set(redisKey, "1", 3L);
            return true;
        }
        return false;
    }


}
