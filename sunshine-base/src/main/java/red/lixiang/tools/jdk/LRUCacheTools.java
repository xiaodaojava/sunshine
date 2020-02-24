package red.lixiang.tools.jdk;

import com.alibaba.fastjson.JSON;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author lixiang
 * @CreateTime 2019/10/2
 **/
public class LRUCacheTools<K, V> {

    public static void main(String[] args) {
        List<String> list  = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        //循环 A
        for (String s : list) {
            System.out.println(s);
        }

        //循环 B
        list.forEach(System.out::println);

        //循环 C
        list.stream().filter(x->x.equals("a")).collect(Collectors.toList());

    }
}
