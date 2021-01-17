package red.lixiang.tools.base.third.wechat;

/**
 *
 * @author lixiang
 * @date 2020/3/18
 **/
public class WechatConfig {

    private String appId;
    private String secret;

    public WechatConfig(String appId, String secret) {
        this.appId = appId;
        this.secret = secret;
    }

    public String tokenKey(){
        return "OPEN_AK_"+appId;
    }

    public String tokenUrl(){
        return String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",appId,secret);
    }

    public String getAppId() {
        return appId;
    }

    public WechatConfig setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getSecret() {
        return secret;
    }

    public WechatConfig setSecret(String secret) {
        this.secret = secret;
        return this;
    }

}
