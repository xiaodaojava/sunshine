package red.lixiang.tools.common.kubernetes;

import io.kubernetes.client.util.SSLUtils;
import red.lixiang.tools.jdk.io.IOTools;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

/**
 * @author lixiang
 * @date 2020/2/22
 **/
public class KuberTools {
    public static void main(String[] args) throws Exception {
        // file path to your KubeConfig
//        String kubeConfigPath = "/Users/lixiang/.kube/config_cf_online";

//        // loading the out-of-cluster config, a kubeconfig from file-system
//        ApiClient client =
//                ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();
//
//        // set the global default api-client to the in-cluster one from above
//        Configuration.setDefaultApiClient(client);
//
//        // the CoreV1Api loads default api-client from global configuration.
//        CoreV1Api api = new CoreV1Api();
//
//        PodLogs logs = new PodLogs();
//        InputStream inputStream = logs.streamNamespacedPodLog("cobra", "market-service-deployment-57dbb5ccc6-4htkq", "market-service", null, 1000, false);
//        try{
//            String s = IOTools.readString(inputStream);
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            inputStream.close();
//        }
//        System.out.println("aaaaa");
//        System.out.println(s);
        // invokes the CoreV1Api client
//        V1PodList list =
//                api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);
//        for (V1Pod item : list.getItems()) {
//            String name = item.getMetadata().getName();
//            if(name.contains("market")){
//                PodLogs logs = new PodLogs();
//                InputStream is = logs.streamNamespacedPodLog(item);
//                String s = IOTools.readString(is);
//                System.out.println(s);
//            }
//        }

//        String s1 = "LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSUN6VENDQWphZ0F3SUJBZ0lERStSMU1BMEdDU3FHU0liM0RRRUJDd1VBTUdveEtqQW9CZ05WQkFvVElXTmgKWWprd05tTTFOR0poWmpVME1EY3hPR1k0Tm1KbE16ZzJNelZqTlRSallqRVFNQTRHQTFVRUN4TUhaR1ZtWVhWcwpkREVxTUNnR0ExVUVBeE1oWTJGaU9UQTJZelUwWW1GbU5UUXdOekU0WmpnMlltVXpPRFl6TldNMU5HTmlNQjRYCkRUSXdNREV3T0RBNU1EUXdNRm9YRFRJek1ERXdOekE1TURrMU9Wb3dQekVWTUJNR0ExVUVDaE1NYzNsemRHVnQKT25WelpYSnpNUWt3QndZRFZRUUxFd0F4R3pBWkJnTlZCQU1URWpJMU1qY3pNakkzTmpRMk1qRTJOamcwTlRDQgpuekFOQmdrcWhraUc5dzBCQVFFRkFBT0JqUUF3Z1lrQ2dZRUF0MURsbENxT2UwdTVaZm04bjYwOS9DbjRkZ3k1Ckl0SU5UU0FNY1QyVGVXRnlZMEhQdFVIaWJ1UXhlTjZuNjkwUTVvdkdEQmFzZHRmMVFjaWhvcmhJY0hWdEIrOHoKdVgyTTAyUzhkN3BGZXNsVjJuVzRaMWdySTFuTVpLbnRKR3FJMytqR3NUMEJKMldvK1piN2hJc252RGVZSXZTdAphNldtMHlXQStHQXVKdDBDQXdFQUFhT0JxekNCcURBT0JnTlZIUThCQWY4RUJBTUNCNEF3RXdZRFZSMGxCQXd3CkNnWUlLd1lCQlFVSEF3SXdEQVlEVlIwVEFRSC9CQUl3QURBOEJnZ3JCZ0VGQlFjQkFRUXdNQzR3TEFZSUt3WUIKQlFVSE1BR0dJR2gwZEhBNkx5OWpaWEowY3k1aFkzTXVZV3hwZVhWdUxtTnZiUzl2WTNOd01EVUdBMVVkSHdRdQpNQ3d3S3FBb29DYUdKR2gwZEhBNkx5OWpaWEowY3k1aFkzTXVZV3hwZVhWdUxtTnZiUzl5YjI5MExtTnliREFOCkJna3Foa2lHOXcwQkFRc0ZBQU9CZ1FCdElVUjJOVTlDdTd4ODUyeE10T2FzdDlyN3BQZ21jSmgwVTZwWGx4MlgKTk5oZlcyVjRXSjBmRVRnTUExWTRxM0xzMXBVWXdGaHRGT1ZpN3lKelhkRnNWbFV3dFBOc1BDcWgwYURmbVFNMwpnc0p1Q2EyaVhlL2VZRzI4d3BYcjRyS0FGckNEdjlLRWppMlRtd3g5d09vV3dacFBnUTlLZkxvbndDaXlKRzR3CndRPT0KLS0tLS1FTkQgQ0VSVElGSUNBVEUtLS0tLQotLS0tLUJFR0lOIENFUlRJRklDQVRFLS0tLS0KTUlJQy96Q0NBbWlnQXdJQkFnSURFM0xDTUEwR0NTcUdTSWIzRFFFQkN3VUFNR0l4Q3pBSkJnTlZCQVlUQWtOTwpNUkV3RHdZRFZRUUlEQWhhYUdWS2FXRnVaekVSTUE4R0ExVUVCd3dJU0dGdVoxcG9iM1V4RURBT0JnTlZCQW9NCkIwRnNhV0poWW1FeEREQUtCZ05WQkFzTUEwRkRVekVOTUFzR0ExVUVBd3dFY205dmREQWVGdzB4T1RFeU1UTXcKTlRNMk1EQmFGdzB6T1RFeU1EZ3dOVFF4TWpSYU1Hb3hLakFvQmdOVkJBb1RJV05oWWprd05tTTFOR0poWmpVMApNRGN4T0dZNE5tSmxNemcyTXpWak5UUmpZakVRTUE0R0ExVUVDeE1IWkdWbVlYVnNkREVxTUNnR0ExVUVBeE1oClkyRmlPVEEyWXpVMFltRm1OVFF3TnpFNFpqZzJZbVV6T0RZek5XTTFOR05pTUlHZk1BMEdDU3FHU0liM0RRRUIKQVFVQUE0R05BRENCaVFLQmdRQ3Z2MHZ6UlJSNE1nekc0TVdQMUIxOG8zc0xhUDNUQ0lOdlJ6b21VQnplQUNzRQo4OFNUZFFXQndCdkhiTmtGZVkwSDFwdUpyR2xaL0NoYXg1Q0ZZRXU3Z204STdUTUJHYVYxTGdyb3BqWmkraDk3CjNpRDJEV0Z6bGFhUDhSeWFucFZnWk1BSlpyOG9xdU1raWRuVHVIVXoxcG0zSTBxaXc4Q2tBS0h5SUVZcTNRSUQKQVFBQm80RzZNSUczTUE0R0ExVWREd0VCL3dRRUF3SUNyREFQQmdOVkhSTUJBZjhFQlRBREFRSC9NQjhHQTFVZApJd1FZTUJhQUZJVmEvOTBqelNWdldFRnZubTFGT1p0WWZYWC9NRHdHQ0NzR0FRVUZCd0VCQkRBd0xqQXNCZ2dyCkJnRUZCUWN3QVlZZ2FIUjBjRG92TDJObGNuUnpMbUZqY3k1aGJHbDVkVzR1WTI5dEwyOWpjM0F3TlFZRFZSMGYKQkM0d0xEQXFvQ2lnSm9Za2FIUjBjRG92TDJObGNuUnpMbUZqY3k1aGJHbDVkVzR1WTI5dEwzSnZiM1F1WTNKcwpNQTBHQ1NxR1NJYjNEUUVCQ3dVQUE0R0JBRXJtSGs4UXpjR0dXN28rZERPUUlzMjJGQzl2TXc5SFJ5QWFtbC8vCmNCSFNCZGVoMjAvakhCZS92ekFiUTZzSHQwMGYxZTVralBMRFM0ZitNa2pzWlhDaUpHa05VR3Jnd1NhVDJhTzMKVzB0REtvY2p6b1p2NUlzSnlIQmNPTXhQbTBpbUN5Nm92dmFobzhHMlJsK1Y3TElGQzk1OUIyN2o3RFBET2djWAp0YTliCi0tLS0tRU5EIENFUlRJRklDQVRFLS0tLS0K=";
//        byte[] decode = Base64.getDecoder().decode(s1);
//        String ss  = new String(decode);
//        System.out.println(ss);

      //  KubernetesConfig config = new KubernetesConfig("/Users/lixiang/.kube/config_cf_online");
      //  String s = createSSLConnection("https://10.20.10.234:6443/api/v1/namespaces/cobra/pods/market-service-deployment-57dbb5ccc6-4htkq/log?container=market-service&follow=false&pretty=false&previous=false&tailLines=1000&timestamps=false", null, config);

    }

    public static String createSSLConnection(String reqUrl, String reqBody, KubernetesConfig kubernetesConfig) throws Exception{
        URL url = new URL(reqUrl);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒)
        conn.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒)
        conn.setDoOutput(true); // post请求参数要放在http正文内，顾设置成true，默认是false
        conn.setDoInput(true); // 设置是否从httpUrlConnection读入，默认情况下是true
        conn.setUseCaches(false); // Post 请求不能使用缓存
        // 设定传送的内容类型是可序列化的java对象(如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("GET");// 设定请求的方法为"POST"，默认是GET
        if(reqBody!=null){
            conn.setRequestProperty("Content-Length", reqBody.length() + "");
            conn.getOutputStream().write(reqBody.getBytes());
        }
        //验证主机
        conn.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
        try {
            SSLSocketFactory sslSocketFactory = initCert(kubernetesConfig.getUserClientKey(),kubernetesConfig.getUserClientCertificate());
            conn.setSSLSocketFactory(sslSocketFactory);
        }catch (Exception e){
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
        System.out.println(response);
        return response;
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
