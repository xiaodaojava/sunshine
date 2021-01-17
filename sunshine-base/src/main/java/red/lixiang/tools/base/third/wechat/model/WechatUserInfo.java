package red.lixiang.tools.base.third.wechat.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *
 * @author lixiang
 * @date 2020/3/19
 **/
public class WechatUserInfo {
    //{"subscribe":1,
    // "openid":"xxxxxxx",
    // "nickname":"凑心",
    // "sex":1,
    // "language":"en",
    // "city":"武汉",
    // "province":"湖北",
    // "country":"中国",
    // "headimgurl":"http:\/\/thirdwx.qlogo.cn\/mmopen\/agIoFFfvY2C8yI2K1xvE5jeFyjykq2402eZUgINAEUyAsN5IqgEJaVhDZxEcG8ibTnjYBqdwrgGSyckCfRFrbGibFTE2uExgK7\/132",
    // "subscribe_time":1554185396,
    // "unionid":"xxxxxxxxxx",
    // "remark":"","groupid":0,
    // "tagid_list":[],
    // "subscribe_scene":"ADD_SCENE_QR_CODE",
    // "qr_scene":0,"qr_scene_str":""}
    @JSONField(name = "openid")
    private String openId;
    private String nickname;
    @JSONField(name = "headimgurl")
    private String headImgUrl;
    @JSONField(name = "unionid")
    private String unionId;


    public String getOpenId() {
        return openId;
    }

    public WechatUserInfo setOpenId(String openId) {
        this.openId = openId;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public WechatUserInfo setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public WechatUserInfo setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
        return this;
    }

    public String getUnionId() {
        return unionId;
    }

    public WechatUserInfo setUnionId(String unionId) {
        this.unionId = unionId;
        return this;
    }
}
