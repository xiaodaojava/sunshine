package red.lixiang.tools.jdk;

import com.alibaba.fastjson.JSON;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lixiang
 * @CreateTime 2019/10/2
 **/
public class LRUCacheTools<K, V> {

    /** 默认的缓存数量为10 */
    private Integer size = 10;

    private Map<K,V> map ;

    public LRUCacheTools() {
        this(3);
    }

    public LRUCacheTools(Integer size) {
        this.size = size;
        map = new LinkedHashMap<K,V>(size,0.75f,true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size()>size;
            }
        };
    }

    public V get(K key){
        return map.get(key);
    }

    public void put(K key,V value){
        map.put(key,value);
    }

    public static void main(String[] args) {
        LRUCacheTools<Integer,Integer> tools = new LRUCacheTools<>(10);

        for (int i = 0; i < 15; i++) {
            tools.put(i,i);
            tools.put(5,5);
            System.out.println(tools.map);
        }
    }

}
