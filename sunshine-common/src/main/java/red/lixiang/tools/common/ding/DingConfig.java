package red.lixiang.tools.common.ding;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * @author lixiang
 * @date 2020/2/29
 **/
public class DingConfig {

    private String url;
    private String secret;
    private String sign;

    public String getUrl() {
        Long timestamp = System.currentTimeMillis();
        String sign = getSign(timestamp);
        return url+"&timestamp="+timestamp+"&sign="+sign;
    }

    public DingConfig setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getSecret() {
        return secret;
    }

    public DingConfig setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    public String getSign(Long timestamp) {
        try {

            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            return URLEncoder.encode(Base64.getEncoder().encodeToString(signData), "UTF-8");
        }catch (Exception e){
            return null;
        }
    }


}
