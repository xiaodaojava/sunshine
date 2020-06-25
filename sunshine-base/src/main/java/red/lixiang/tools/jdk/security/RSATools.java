package red.lixiang.tools.jdk.security;



import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加解密及签名校验
 * RSA签名算法：MD2withRSA、MD5withRSA、SHA1withRSA，为jdk实现
 * 1.秘钥长度为64的倍数，在512~65536之间，默认长度1024，签名长度和秘钥长度相同
 * 签名算法：SHA256withRSA，为BC实现，秘钥默认长度为2048
 *
 *
 */
public class RSATools {


    //非对称加密算法
    public static final String KEY_ALGORITHM = "RSA";
    //签名算法
    public static final String SIGN_ALGORITHM = "SHA256withRSA";
    /**
     * 秘钥长度，必须是64的倍数，在512~65536之间
     */
    private static final int KEY_SIZE = 4098;
    //公钥
    private static final String PUBLIC_KEY = "PUBLIC_KEY";
    //私钥
    private static final String PRIVATE_KEY = "PRIVATE_KEY";

    private static final String PUBLIC_PATH = "/Users/public.key";

    private static final String PRIVATE_PATH = "/Users/private.key";

    public static void main(String[] args) throws Exception{
        /**
         * 加解密验证
         */
        //初始化秘钥对
        Map<String, Object> keyMap = RSATools.initKey();
        //私钥
        byte[] privateKey = RSATools.getPrivateKey(keyMap);
        //公钥
        byte[] publicKey = RSATools.getPublicKey(keyMap);
        System.out.println("私钥：" + Base64.getEncoder().encodeToString(privateKey));
        System.out.println("公钥：" + Base64.getEncoder().encodeToString(publicKey));

//        String source = "Burning by Maria Arredondo";
//        byte[] encryptData = RSATools.encryptByPrivateKey(source.getBytes(), privateKey);
//        System.out.println("私钥加密后数据：" + new String(encryptData));
//        byte[] decryptData = RSATools.decryptByPublicKey(encryptData, publicKey);
//        System.out.println("公钥解密后数据：" + new String(decryptData));
//
//        /**
//         * 签名验证
//         */
//        String privateKeyStr = readKeyFromFile(PRIVATE_PATH);
//        String publicKeyStr = readKeyFromFile(PUBLIC_PATH);
//
//        String signSource = "Love Mail";
//        String sign = signByPrivateKey(signSource, privateKeyStr);
//        System.out.println("私钥签名值：" + sign);
//        System.out.println("公钥签名校验结果：" + verifySignByPublic(signSource, publicKeyStr, sign));
    }

    /**
     * 初始化秘钥对
     * @return 秘钥map
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception{
        //实例化秘钥生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //初始化秘钥生成器
        keyPairGenerator.initialize(KEY_SIZE);
        //生成秘钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //公钥
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        //私钥
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

        //保存公钥
        RSATools.saveKeyForFile(PUBLIC_PATH, Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded()));
        //保存私钥
        RSATools.saveKeyForFile(PRIVATE_PATH, Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded()));

        //将秘钥存储在map中
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put(PUBLIC_KEY, rsaPublicKey);
        keyMap.put(PRIVATE_KEY, rsaPrivateKey);
        return keyMap;
    }

    /**
     * 私钥加密
     * @param data 待加密数据
     * @param key 秘钥
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws Exception{
        //取得私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(key);
        //实例化秘钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //私钥加密数据
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }


    /**
     * 公钥解密
     * @param data 待解密数据
     * @param key 秘钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception{
        //取得公钥(材料转换)
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
        //实例化秘钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成公钥
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

        //解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥签名
     * @param content 待签名数据
     * @return 签名值
     * @throws Exception
     */
    public static String signByPrivateKey(String content,String rsaPrivateKey) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(rsaPrivateKey));
        String signStr =null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            //获取秘钥
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            //实例化签名
            Signature signature = Signature.getInstance(SIGN_ALGORITHM);
            //初始化签名
            signature.initSign(privateKey);
            signature.update(content.getBytes());
            byte[] sign = signature.sign();
            signStr = Base64.getEncoder().encodeToString(sign);
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException | SignatureException e) {
            e.printStackTrace();
        }
        return signStr;

    }

    /**
     * 公钥验签
     * @param content 报文数据
     * @param rsaPublicKey 签名秘钥
     * @param sign 签名值
     * @return 验签是否通过
     * @throws Exception
     */
    public static boolean verifySignByPublic(String content, String rsaPublicKey, String sign) throws Exception{
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(rsaPublicKey));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //获取公钥
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        //实例化签名
        Signature signature = Signature.getInstance(SIGN_ALGORITHM);
        //初始化验签
        signature.initVerify(publicKey);
        signature.update(content.getBytes());
        return signature.verify(Base64.getDecoder().decode(sign));

    }

    /**
     * 获取公钥
     * @param keyMap
     * @return
     */
    public static byte[] getPublicKey(Map<String, Object> keyMap){
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return key.getEncoded();
    }

    /**
     * 获取私钥
     * @param keyMap
     * @return
     */
    public static byte[] getPrivateKey(Map<String, Object> keyMap){
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return key.getEncoded();
    }

    /**
     * 生成文件保存秘钥
     * @param filePath
     * @param keyStr
     */
    public static void saveKeyForFile(String filePath, String keyStr){
        try{
            File file = new File(filePath);
            if(!file.exists()){
                file.mkdir();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(keyStr);
            bw.flush();
            bw.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 从文件中读取秘钥
     * @param filePath
     * @return
     */
    public static String readKeyFromFile(String filePath){
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while((readLine=br.readLine()) != null){
                sb.append(readLine);
            }
            br.close();
            return sb.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }


}