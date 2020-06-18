package red.lixiang.tools.demo.inter;

import red.lixiang.tools.demo.GreetCopy;
import red.lixiang.tools.demo.proxy.dynamic.Greet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author lixiang
 * @date 2020/6/17
 **/
public class TestMain {

    public static void main(String[] args) {
        // 把这个ArrayList声明为Collection
        Collection<String> collection = new ArrayList<>();
        collection.add("hello");
        // 把这个ArrayList声明成List
        List<String> list = new ArrayList<>();
        list.add("java");
        // 两行均输出的是1
        System.out.println(collection.size());
        System.out.println(list.size());
    }
}
