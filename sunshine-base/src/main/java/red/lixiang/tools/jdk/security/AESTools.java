package red.lixiang.tools.jdk.security;

import red.lixiang.tools.base.KV;
import red.lixiang.tools.jdk.StringTools;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

/**
 * @author lixiang
 * @date 2020/1/8
 **/
public class AESTools {

    /** 用来存表和随机串的对应关系 */
    public static final Map<String,String> SALT_MAP = new HashMap<>(50);

    public static String getSalt(String code ){
        return SALT_MAP.get(code);
    }

    public static void setSalt(String code , String salt){
        SALT_MAP.put(code,salt);
    }

    public static void initSalt(List<KV> list){
        for (KV kv : list) {
            SALT_MAP.put(kv.getName(),kv.getValue());
        }
    }


    /**
     * content: 17740899943
     * saltCode: db0.xxxx
     * @return
     */
    public static KV spiltContent(String content, String saltCode){
        String saltStr = AESTools.getSalt(saltCode);
        if(saltStr==null){
            return new KV(content,null);
        }
        String middle  = content.substring(content.length()/3,content.length()/3*2+1);
        String mis = middle.replaceAll("[0-9]","*");
        //不能用替换, 只能用拼接的方式
//        content=content.replace(middle,middle.replaceAll("[0-9]","*"));
        String pre  = content.substring(0,content.length()/3);
        String tail  = content.substring(content.length()/3*2+1);
        content = pre+mis+tail;
        String aesStr = AESTools.AESEncode(saltStr,middle);
        KV kv = new KV(content,aesStr);
        return kv;
    }

    /**
     *
     * @param security
     * @param aesStr
     * @param saltCode
     * @return
     */
    public static String revertContent(String security,String aesStr,String saltCode){
        String saltStr = AESTools.getSalt(saltCode);
        if(StringTools.isBlank(saltStr)){
            return security;
        }
        String content = AESTools.AESDecode(saltStr, aesStr);
        String fake  = security.substring(security.length()/3,security.length()/3*2+1);
        security=security.replace(fake,content);
        return security;
    }


    /*
     * 加密
     * 1.构造密钥生成器
     * 2.根据ecnodeRules规则初始化密钥生成器
     * 3.产生密钥
     * 4.创建和初始化密码器
     * 5.内容加密
     * 6.返回字符串
     */
    public static String AESEncode(String encodeRules,String content){
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encodeRules.getBytes());
            keygen.init(128, random);
            //3.产生原始对称密钥
            SecretKey originalKey=keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte [] raw=originalKey.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(raw, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte [] byteEncode=content.getBytes(StandardCharsets.UTF_8);
            //9.根据密码器的初始化方式--加密：将数据加密
            byte [] byteAes=cipher.doFinal(byteEncode);
            //10.将加密后的数据转换为字符串
            //这里用Base64Encoder中会找不到包
            //解决办法：
            //在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
            String AesEncode=new String(Base64.getEncoder().encode(byteAes));
            //11.将字符串返回
            return AesEncode;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        //如果有错就返加nulll
        return "";
    }
    /*
     * 解密
     * 解密过程：
     * 1.同加密1-4步
     * 2.将加密后的字符串反纺成byte[]数组
     * 3.将加密内容解密
     */
    public static String AESDecode(String encodeRules, String content){
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encodeRules.getBytes());
            keygen.init(128, random);
            //3.产生原始对称密钥
            SecretKey original_key=keygen.generateKey();
            //4.获得原始对称密钥的字节数组
            byte [] raw=original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(raw, "AES");
            //6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance("AES");
            //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.将加密并编码后的内容解码成字节数组
            byte [] byte_content= Base64.getDecoder().decode(content);
            /*
             * 解密
             */
            byte [] byte_decode=cipher.doFinal(byte_content);
            String AES_decode=new String(byte_decode, StandardCharsets.UTF_8);
            return AES_decode;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }

        //如果有错就返加nulll
        return "";
    }



    public static void main(String[] args) {
//        Scanner scanner=new Scanner(System.in);
//        /*
//         * 加密
//         */
//        System.out.println("使用AES对称加密，请输入加密的规则");
//        String encodeRules=scanner.next();
//        System.out.println("请输入要加密的内容:");
//        String content = scanner.next();
//        System.out.println("根据输入的规则"+encodeRules+"加密后的密文是:"+ AESEncode(encodeRules, content));
//
//        /*
//         * 解密
//         */
//        System.out.println("使用AES对称解密，请输入加密的规则：(须与加密相同)");
//        encodeRules=scanner.next();
//        System.out.println("请输入要解密的内容（密文）:");
//        content = scanner.next();
//        System.out.println("根据输入的规则"+encodeRules+"解密后的明文是:"+ AESDncode(encodeRules, content));
//

        String mobile  = "17740899943";
        String pre  = mobile.substring(0,mobile.length()/3);
        String middle  = mobile.substring(mobile.length()/3,mobile.length()/3*2+1);
        String tail  = mobile.substring(mobile.length()/3*2+1);
        System.out.println(pre);

    }
}
