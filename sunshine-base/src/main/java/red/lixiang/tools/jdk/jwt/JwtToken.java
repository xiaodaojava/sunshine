package red.lixiang.tools.jdk.jwt;

import red.lixiang.tools.base.CommonModel;
import red.lixiang.tools.jdk.JSONTools;
import red.lixiang.tools.jdk.security.RSATools;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lixiang
 * @date 2020/6/25
 **/
public class JwtToken {

    private Header header;
    /**
     * 常用的key值
     * https://www.iana.org/assignments/jwt/jwt.xhtml
     * */
    private Map<String,String> payload;

    /** HMACSHA256(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret) */
    private String sign;


    static class Header implements CommonModel {
        /** 签名算法: HS256、HS384、HS512、RS256、RS384、RS512、ES256、ES384、ES512、PS256、PS384
         *
         JWS	算法名称	描述
         HS256	HMAC256	HMAC with SHA-256
         HS384	HMAC384	HMAC with SHA-384
         HS512	HMAC512	HMAC with SHA-512
         RS256	RSA256	RSASSA-PKCS1-v1_5 with SHA-256
         RS384	RSA384	RSASSA-PKCS1-v1_5 with SHA-384
         RS512	RSA512	RSASSA-PKCS1-v1_5 with SHA-512
         ES256	ECDSA256	ECDSA with curve P-256 and SHA-256
         ES384	ECDSA384	ECDSA with curve P-384 and SHA-384
         ES512	ECDSA512	ECDSA with curve P-521 and SHA-512
         PS256	RSAPSSSA256	RSAPSS with SHA-256
         PS384	RSAPSSSA384	RSAPSS with SHA-384
         *
         * */
        String alg;

        String type = "jwt";

        public String getAlg() {
            return alg;
        }

        public Header setAlg(String alg) {
            this.alg = alg;
            return this;
        }

        public String getType() {
            return type;
        }

    }


    public void initDefaultPayload(Long passportId , String name,Long exp){
        Map<String,String> map = new HashMap<>();
        map.put("uid",String.valueOf(passportId));
        map.put("name",name);
        map.put("exp",String.valueOf(exp));
    }


    public String toJwt(){
        // 第一部分转成json
        String headerStr = Base64.getEncoder().encodeToString(header.toJson().getBytes(StandardCharsets.UTF_8));
        String payloadStr = Base64.getEncoder().encodeToString(JSONTools.toJson(payload).getBytes(StandardCharsets.UTF_8));
        String signStr = "RSATools.signByPrivateKey()";
        return headerStr+"."+payloadStr+"."+signStr;
    }


}
