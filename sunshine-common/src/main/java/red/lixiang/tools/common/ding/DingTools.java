package red.lixiang.tools.common.ding;

import com.alibaba.fastjson.JSON;
import red.lixiang.tools.jdk.http.HttpResponse;
import red.lixiang.tools.jdk.http.HttpTools;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lixiang
 * @date 2020/2/29
 **/
public class DingTools {

    public static void sendText(String content,DingConfig config){
        DingMessage message = DingMessage.initTextMessage(content);
        String s = JSON.toJSONString(message);
        Map<String,String> map = new HashMap<>();
        map.put("Content-Type","application/json");
        HttpResponse<String> response = HttpTools.doPost(config.getUrl(),s , map, String.class);
//        System.out.println(response.getBody());
    }

    public static void sendActionCard(String title,String content,String trueUrl,String falseUrl,DingConfig config){
        DingMessage message = DingMessage.initActionCardMessage(title,content,trueUrl,falseUrl);
        String s = JSON.toJSONString(message);
        Map<String,String> map = new HashMap<>();
        map.put("Content-Type","application/json");
        HttpResponse<String> response = HttpTools.doPost(config.getUrl(),s , map, String.class);
//        System.out.println(response.getBody());
    }
}
