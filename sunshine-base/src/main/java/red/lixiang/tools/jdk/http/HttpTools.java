package red.lixiang.tools.jdk.http;

import com.alibaba.fastjson.JSON;
import red.lixiang.tools.jdk.io.IOTools;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lixiang
 * @date 2019/12/13
 **/
public class HttpTools {


    /**
     * Constructor.
     */
    private HttpTools() {

    }

    public static  <T> HttpResponse<T> doPost(String url,Map<String,String> paramMap,Class<T> responseType) {
        Map<String,String> map = new HashMap<>();
        map.put("Content-Type","application/json");
        return doPost(url,paramMap,map,responseType);
    }

    public static  <T> HttpResponse<T> doPost(String url,Map<String,String> paramMap,Map<String,String> headerMap,Class<T> responseType){
        return doPost(url,JSON.toJSONString(paramMap),headerMap,responseType);
    }


    public static  <T> HttpResponse<T> doPost(String url,String paramContent,Map<String,String> headerMap,Class<T> responseType) {
        try {
            InputStreamReader isr = null;
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();


            conn.setRequestMethod("POST");
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.getOutputStream().write(paramContent.getBytes());

            conn.connect();


            int statusCode = conn.getResponseCode();
            String response =null;
            try {
                isr = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
                response = IOTools.readString(isr);
                if(responseType==String.class){
                    return new HttpResponse<>(statusCode, (T)response);
                }
                return new HttpResponse<T>(statusCode,JSON.parseObject(response,responseType));
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (isr != null) {
                    try {
                        isr.close();
                    } catch (IOException ex) {
                        // ignore
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Do get operation for the http request.
     *
     * @return the response
     */
    public static  <T> HttpResponse<T> doGet(String url, Map<String,String> paramMap,final Class<T> responseType) {
        HttpRequest request = new HttpRequest(url);
        request.setParamMap(paramMap);
        return doGetWithRequest(request,responseType);
    }

    public static  <T> HttpResponse<T> doGetWithRequest(HttpRequest httpRequest,final Class<T> responseType) {
        InputStreamReader isr = null;
        InputStream inputStream = null;
        InputStreamReader esr = null;
        int statusCode;
        try {
            Map<String,String> paramMap = httpRequest.getParamMap();
            String url  = httpRequest.getUrl();
            if(null!=paramMap){
                StringBuilder querySB =  new StringBuilder("?");
                paramMap.forEach((key,value)-> querySB.append(key).append("=").append(value).append("&"));
                //去掉最后一个 &
                String query = querySB.substring(0,querySB.length()-1);
                url = url+query;
            }
            Proxy proxy = Proxy.NO_PROXY;
            if(httpRequest.getProxyHost()!=null && httpRequest.getProxyPort()!=null){
                proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(httpRequest.getProxyHost(), httpRequest.getProxyPort()));
            }
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection(proxy);

            conn.setRequestMethod("GET");

            int connectTimeout = httpRequest.getConnectTimeout();
            int readTimeout = httpRequest.getReadTimeout();
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);

            conn.connect();

            statusCode = conn.getResponseCode();
            String response =null;

            try {
                inputStream = conn.getInputStream();
            } catch (IOException ex) {
                /**
                 * according to https://docs.oracle.com/javase/7/docs/technotes/guides/net/http-keepalive.html,
                 * we should clean up the connection by reading the response body so that the connection
                 * could be reused.
                 */
                InputStream errorStream = conn.getErrorStream();

                if (errorStream != null) {
                    esr = new InputStreamReader(errorStream, StandardCharsets.UTF_8);
                    try {
                        response =IOTools.readString(esr);
                    } catch (IOException ioe) {
                        //ignore
                    }
                }

                // 200 and 304 should not trigger IOException, thus we must throw the original exception out
                if (statusCode == 200 || statusCode == 304) {
                    ex.printStackTrace();
                }
            }

            if (statusCode == 200) {
                if(responseType == byte[].class){
                    byte[] bytes = IOTools.readByte(inputStream);
                    return new HttpResponse<>(statusCode, (T)bytes);
                }
                isr = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

                response = IOTools.readString(isr);
                if(responseType==String.class){
                    return new HttpResponse<>(statusCode, (T)response);
                }
                return new HttpResponse<>(statusCode, JSON.parseObject(response,responseType));
            }

            if (statusCode == 304) {
                return new HttpResponse<>(statusCode, null);
            }
        }  catch (Throwable ex) {
           ex.printStackTrace();
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException ex) {
                    // ignore
                }
            }

            if (esr != null) {
                try {
                    esr.close();
                } catch (IOException ex) {
                    // ignore
                }
            }
        }
        return null;
    }
}
