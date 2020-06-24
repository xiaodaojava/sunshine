package red.lixiang.tools.common.mybatis;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lixiang
 * @date 2020/6/23
 **/
public class MybatisToolCache {
    public static Map<Class<?>,String> MAPPER_TABLE_CACHE  = new ConcurrentHashMap<>(50);
}
