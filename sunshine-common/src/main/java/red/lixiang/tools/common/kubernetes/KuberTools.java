package red.lixiang.tools.common.kubernetes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import red.lixiang.tools.common.kubernetes.models.Pod;
import red.lixiang.tools.jdk.StringTools;
import red.lixiang.tools.jdk.io.IOTools;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author lixiang
 * @date 2020/2/22
 **/
public class KuberTools {
    public static void main(String[] args) throws Exception {

    }

    public static String  deletePos(Pod pod,KubernetesConfig config){
        StringBuilder builder = new StringBuilder().append(config.getServerApi())
                .append("/api/v1/namespaces/")
                .append(pod.getNamespace())
                .append("/pods/").append(pod.getName());
        String result = connect(builder.toString(), null,"DELETE", config);
        return result;
    }

    public static String getPodLogs(Integer lines,Pod pod, KubernetesConfig config){
        lines = lines == null? 1000:lines;
        StringBuilder builder  = new StringBuilder().append(config.getServerApi())
                .append("/api/v1/namespaces/")
                .append(pod.getNamespace())
                .append("/pods/").append(pod.getName())
                .append("/log?follow=false&pretty=false&previous=false&timestamps=false")
                .append("&tailLines=").append(lines);
        String result = connect(builder.toString(), null,"GET", config);
        return result;
    }

    public static List<Pod> getAllPods(String searchName,KubernetesConfig config){
        String url  = config.getServerApi()+"/api/v1/pods";
        String res = connect(url,null,"GET",config);
        JSONObject jsonObject = JSON.parseObject(res);
        JSONArray items = jsonObject.getJSONArray("items");
        List<Pod> result = new ArrayList<>();
        for (Object item : items) {
            JSONObject jObj = (JSONObject) item;
            JSONObject metadata = jObj.getJSONObject("metadata");
            String name  =  metadata.getString("name");
            String namespace = metadata.getString("namespace");
            if(StringTools.isNotBlank(searchName)){
                if(!name.contains(searchName)){
                    continue;
                }
            }
            Pod pod = new Pod();
            pod.setName(name).setNamespace(namespace);
            result.add(pod);
        }
        return result;
    }

    private static String connect(String reqUrl, String reqBody, String requestMethod,KubernetesConfig kubernetesConfig) {
        try {
            URL url = new URL(reqUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒)
            conn.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒)
            conn.setDoOutput(true); // post请求参数要放在http正文内，顾设置成true，默认是false
            conn.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true
            conn.setUseCaches(false); // Post 请求不能使用缓存
            // 设定传送的内容类型是可序列化的java对象(如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod(requestMethod);// 设定请求的方法为"POST"，默认是GET
            if (reqBody != null) {
                conn.setRequestProperty("Content-Length", reqBody.length() + "");
                conn.getOutputStream().write(reqBody.getBytes());
            }
            //验证主机
            conn.setHostnameVerifier((s, sslSession) -> true);
            try {
                SSLSocketFactory sslSocketFactory = initCert(kubernetesConfig.getUserClientKey(), kubernetesConfig.getUserClientCertificate());
                conn.setSSLSocketFactory(sslSocketFactory);
            } catch (Exception e) {
                throw new Exception("证书加载错误");
            }
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            InputStreamReader isr;
            isr = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
            String response = IOTools.readString(isr);
            isr.close();
            return response;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 加载证书
     * @param key 证书的key
     * @param pwd 证书的密码
     */
    private static SSLSocketFactory initCert(String key,String pwd ) throws Exception {
        try {
            //初始化秘钥管理器
            final KeyManager[] keyManagers = SSLUtils.keyManagers(Base64.getDecoder().decode(pwd), Base64.getDecoder().decode(key), "RSA", "", null, null);

            //信任所有证书
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException { }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException { }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            SSLContext sslContext = SSLContext.getInstance("TLS");

            // 第一个参数是授权的密钥管理器，用来授权验证。TrustManager[]第二个是被授权的证书管理器，用来验证服务器端的证书。第三个参数是一个随机数值，可以填写null
            sslContext.init(keyManagers, new TrustManager[]{tm}, null);
            return sslContext.getSocketFactory();
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }



}
