package red.lixiang.tools.jdk.http;

import com.alibaba.fastjson.JSON;
import red.lixiang.tools.jdk.StringTools;
import red.lixiang.tools.jdk.io.IOTools;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

    /**
     * Do get operation for the http request.
     *
     * @return the response
     */
    public static <T> HttpResponse<T> doGet(String url, Map<String, String> paramMap, final Class<T> responseType) {
        HttpRequest request = new HttpRequest(url);
        request.setParamMap(paramMap);
        return doGetWithRequest(request, responseType);
    }

    public static <T> HttpResponse<T> doGetWithRequest(HttpRequest httpRequest, final Class<T> responseType) {
        httpRequest.setHttpMethod(HttpRequest.METHOD_GET);
        return doInvoke(httpRequest, responseType);
    }


    public static <T> HttpResponse<T> doPost(String url, Map<String, String> bodyMap, Class<T> responseType) {

        return doPost(url, bodyMap, HttpRequest.JSON_HEADER, responseType);
    }

    public static <T> HttpResponse<T> doPost(String url, Map<String, String> bodyMap, Map<String, String> headerMap, Class<T> responseType) {
        return doPost(url, JSON.toJSONString(bodyMap), headerMap, responseType);
    }

    public static <T> HttpResponse<T> doPost(String url, String bodyContent, Map<String, String> headerMap, Class<T> responseType) {
        HttpRequest request = new HttpRequest(url);
        request.setBodyContent(bodyContent);
        request.setHeaderMap(headerMap);
        request.setHttpMethod(HttpRequest.METHOD_POST);
        return doInvoke(request, responseType);
    }

    public static <T> HttpResponse<T> doInvoke(HttpRequest request, Class<T> responseType) {
        try {
            Map<String, String> paramMap = request.getParamMap();
            String url = request.getUrl();
            // 在url后面拼接参数
            if (null != paramMap) {
                StringBuilder querySB = new StringBuilder("?");
                paramMap.forEach((key, value) -> querySB.append(key).append("=").append(value).append("&"));
                //去掉最后一个 &
                String query = querySB.substring(0, querySB.length() - 1);
                url = url + query;
            }
            // 设置代理
            Proxy proxy = Proxy.NO_PROXY;
            if (request.getProxyHost() != null && request.getProxyPort() != null) {
                proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(request.getProxyHost(), request.getProxyPort()));
            }

            InputStreamReader isr = null;
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection(proxy);
            // 请置请求方式
            conn.setRequestMethod(request.getHttpMethod());
            // 添加header
            for (Map.Entry<String, String> entry : request.getHeaderMap().entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 添加请求的body
            if (StringTools.isNotBlank(request.getBodyContent())) {
                try (OutputStream outputStream = conn.getOutputStream()) {
                    outputStream.write(request.getBodyContent().getBytes());
                }
            }

            conn.connect();


            int statusCode = conn.getResponseCode();
            String response = null;
            try {
                isr = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
                response = IOTools.readString(isr);
                if (responseType == String.class) {
                    return new HttpResponse<>(statusCode, (T) response);
                }
                return new HttpResponse<T>(statusCode, JSON.parseObject(response, responseType));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (isr != null) {
                    try {
                        isr.close();
                    } catch (IOException ex) {
                        // ignore
                    }
                }
                conn.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
