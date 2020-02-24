package red.lixiang.tools.test;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lixiang
 * @date 2019/12/23
 **/
public class Main {
    public static void main(String[] args) {
        Map<String,String> map  = new HashMap<>();
        map.put("a","n");
        System.out.println(JSON.toJSON(map));
    }
}
