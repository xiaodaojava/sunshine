package red.lixiang.tools.common.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

/**
 * json工具类
 * @Author lixiang
 * @CreateTime 2019/10/16
 **/
public class JSONTools {

    /**
     * 把json里面的key都提取出来,然后加上前缀和后缀
     * @return
     */
    public static String json2keyMap(String json, Function<String,String> function){
        JSONObject jsonObject = JSON.parseObject(json);
        StringBuilder sb = new StringBuilder();
        jsonObject.forEach((key,value)->{
            String mapperKey = function.apply(key);
            sb.append(mapperKey);
        });
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));

    }
}
