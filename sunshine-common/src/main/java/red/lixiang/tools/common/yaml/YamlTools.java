package red.lixiang.tools.common.yaml;

import org.yaml.snakeyaml.Yaml;
import red.lixiang.tools.jdk.StringTools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author lixiang
 * @date 2020/2/23
 **/
public class YamlTools {

    Map<String,Object> properties;


    public static void main(String[] args) {
        YamlTools tools = new YamlTools("/Users/lixiang/.kube/config_cf_online");
//        String  userClientKey = tools.getValueByKey("users[0].user.client-key-data");
//        String  userClientCertificate = tools.getValueByKey("users[0].user.client-certificate-data");
        String  serverCertificateAuthority = tools.getValueByKey("clusters[0].cluster.certificate-authority-data","test");
        System.out.println(serverCertificateAuthority);
    }

    public YamlTools(String filePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Yaml yaml = new Yaml();
        properties  = yaml.loadAs(inputStream,Map.class);
    }

    public void initWithString(String content){
        Yaml yaml = new Yaml();
        properties  = yaml.loadAs(content,Map.class);
    }

    public YamlTools() {
    }




    /**
     * get yaml property
     *
     * @param key
     * @return
     */
    public  <T> T getValueByKey(String key , T defaultValue) {
        String separator = ".";
        String[] separatorKeys = null;
        if (key.contains(separator)) {
            // 取下面配置项的情况, user.path.keys 这种
            separatorKeys = key.split("\\.");
        } else {
            // 直接取一个配置项的情况, user
            Object res =  properties.get(key);
            return  res== null? defaultValue:(T)res;
        }
        // 下面肯定是取多个的情况
        String finalValue = null;
        Object tempObject  = properties;
        for (int i = 0; i < separatorKeys.length; i++) {
            //如果是user[0].path这种情况,则按list处理
            String innerKey  = separatorKeys[i];
            Integer index  = null;
            if(innerKey.contains("[")){
                index  = Integer.valueOf(StringTools.getSubstringBetween(innerKey,"[","]")[0]);
                innerKey = innerKey.substring(0,innerKey.indexOf("["));
            }
            Map<String,Object> mapTempObj = (Map)tempObject;
            Object object = mapTempObj.get(innerKey);
            if(object ==null){
                return defaultValue;
            }
            Object targetObj =object;
            if(index!=null){
                targetObj  = ((ArrayList)object).get(index);
            }
            tempObject = targetObj;
            if(i == separatorKeys.length-1){
                //循环结束
                return (T)targetObj;
            }

        }
        return null;
    }

}
