package red.lixiang.tools.jdk.cache;


import java.util.*;

/**
 * @author lixiang
 * @CreateTime 2019/10/2
 **/
public class LRUCacheTools<K, V> {



    private Map<K,V> map ;

    public LRUCacheTools() {
        this(10);
    }

    public LRUCacheTools(Integer size) {

        map = new LinkedHashMap<>(size,0.75f,true){
            private static final long serialVersionUID = 1L;
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
