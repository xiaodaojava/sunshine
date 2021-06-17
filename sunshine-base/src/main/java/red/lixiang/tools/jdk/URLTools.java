package red.lixiang.tools.jdk;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lixiang
 * @date 2020/3/16
 **/
public class URLTools {

    public static String getScheme(String url){

        if(url.contains("http")){
            url = url.substring(0,url.indexOf(":"));
            return url;
        }
        return null;
    }
    public static String getHost(String url){
        if(url.contains("http")){
            url = url.substring(url.indexOf("//")+2);
        }
        return url.substring(0,url.indexOf("/"));
    }

    public static String removeHost(String url ){
        String host = getHost(url);
        url = url.substring(url.indexOf(host)+host.length()+1);
        return url;
    }

    /**
     * 从URL中获取参数
     * @param url
     * @param param
     * @return
     */
    public static String paramFromUrl(String url,String param){
        String paramUrl = url.substring(url.indexOf("?") + 1);
        String[] params = paramUrl.split("&");
        Map<String,String> paramMap = new HashMap<>();
        for (String s : params) {
            String[] strings = s.split("=");
            paramMap.put(strings[0],strings[1]);
        }
        return paramMap.get(param);
    }

    public static void main(String[] args) {
        String url = "https://raw.githubusercontent.com/xiaodaojava/image1/master/1584343849924.jpg";

    }
}
