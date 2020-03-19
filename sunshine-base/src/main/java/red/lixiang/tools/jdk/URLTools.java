package red.lixiang.tools.jdk;

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

    public static void main(String[] args) {
        String url = "https://raw.githubusercontent.com/xiaodaojava/image1/master/1584343849924.jpg";

    }
}
