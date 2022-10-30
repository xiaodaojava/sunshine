package red.lixiang.tools.base.third.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import red.lixiang.tools.base.third.wechat.model.TempQRImageReq;
import red.lixiang.tools.base.third.wechat.model.WechatUserInfo;
import red.lixiang.tools.jdk.StringTools;
import red.lixiang.tools.jdk.http.HttpRequest;
import red.lixiang.tools.jdk.http.HttpResponse;
import red.lixiang.tools.jdk.http.HttpTools;

/**
 * @author lixiang
 * @date 2020/3/18
 **/
public class WechatTools {

    /**
     * 获取公众号,小程序的token
     *
     * @param config
     * @return
     */
    public static String getToken(WechatConfig config) {
        String url = config.tokenUrl();
        HttpResponse<String> resString = HttpTools.doGet(url, null, String.class);
        System.out.println(resString.getBody());
        JSONObject result = JSON.parseObject(resString.getBody());
        return result.getString("access_token");
    }

    public static WechatUserInfo getUnionIdByOpenId(String token , String openId){
        String url = String.format("https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN",token,openId);
        HttpResponse<String> response = HttpTools.doGet(url, null, String.class);
        WechatUserInfo wechatUserInfo = JSON.parseObject(response.getBody(), WechatUserInfo.class);
        if(StringTools.isNotBlank(wechatUserInfo.getHeadImgUrl())){
            wechatUserInfo.setHeadImgUrl(wechatUserInfo.getHeadImgUrl().replaceFirst("http","https"));
        }
        return wechatUserInfo;
    }

    public static String getTempQRImageByToken(String token, String sceneId, Long time) {

        String result;
        TempQRImageReq req  = new TempQRImageReq(String.valueOf(time),String.valueOf(sceneId));
        String ticketUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token;
        String object =HttpTools.doPost(ticketUrl,JSON.toJSONString(req.toMap()), HttpRequest.JSON_HEADER,String.class).getBody();
        JSONObject jsonObject = JSON.parseObject(object);
        String ticket = jsonObject.getString("ticket");
        result = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;

        return result;
    }


}
