package red.lixiang.tools.common.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import red.lixiang.tools.jdk.StringTools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author lixiang
 * @date 2020/10/17
 **/
public class CommonConfig {

    public static Properties configContent;

    public static void init(NacosServerConfig config){
        ConfigService configService = null;
        try {
            configService = NacosFactory.createConfigService(config.getServerAddr());
            configService.addListener(config.getDataId(), config.getGroup(), new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }
                @Override
                public void receiveConfigInfo(String configInfo) {
                    // 获取到的配置信息, 直接放本地就好了
                    try {
                        configContent.load(new ByteArrayInputStream(configInfo.getBytes(StandardCharsets.UTF_8)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }


    public static String getConfigVal(String key){
        return configContent.getProperty(key);
    }


    public static String getConfigVal(String key,String defaultValue){
        return configContent.getProperty(key, defaultValue);
    }

    public static Integer getConfigIntVal(String key){
        String property = configContent.getProperty(key);
        if(StringTools.isBlank(property)){
            return null;
        }
        return Integer.parseInt(property);
    }

    public static Integer getConfigIntVal(String key,Integer defaultValue){
        String property = configContent.getProperty(key, String.valueOf(defaultValue));
        return Integer.parseInt(property);
    }

}
