package red.lixiang.tools.jdk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import red.lixiang.tools.base.conf.KubsConfig;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @author lixiang
 * @date 2020/4/7
 **/
public class JSONTools {

    public static String toJson(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static  <T>  T toObject(String json, Class<T> clazz){
        Gson gson = new Gson();
        return gson.fromJson(json,clazz);
    }

    public static  <T> List<T> toList(String json, Class<T> clazz){
        Gson gson = new Gson();
        return  gson.fromJson(json, new TypeToken<List<T>>() {}.getType());

    }

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

    /**
     * @param origin
     * @return
     */
    public static String formatJson(String origin){
        JSONObject jsonObject = JSON.parseObject(origin);
        String result = JSON.toJSONString(jsonObject, true);
        return result;
    }
}
