package red.lixiang.tools.common.apollo;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import red.lixiang.tools.base.KV;
import red.lixiang.tools.jdk.StringTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * config配置文件工具类,用这个只是默认获取application下面的配置
 * 其他命令空间的需要自行获取config实例
 * 后期可以改成map+单例的实现
 *
 * @author lixiang
 */
public class CommonConfig {

    private static Config config = ConfigService.getAppConfig();


    public static int getConfigIntVal(String key, int defaultValue) {
        return config.getIntProperty(key, defaultValue);
    }

    public static long getConfigLongVal(String key, long defaultValue) {
        return config.getLongProperty(key, defaultValue);
    }

    /**
     * 获取配置文件中的值
     */
    public static String getConfigVal(String key, String defaultValue) {
        return config.getProperty(key, defaultValue);
    }

    public List<KV> getConfigList(String key, String defaultValue){
    	return convert(getConfigVal(key, defaultValue));
    }

    public Map<String,String> getConfigMap(String key, String defaultValue){
		List<KV> configList = getConfigList(key, defaultValue);
		return configList.stream().collect(Collectors.toMap(KV::getValue,KV::getName));
	}

	private List<KV> convert(String content) {
		List<KV> result = new ArrayList<>();
		if (!StringTools.isBlank(content)) {
			String[] valueArray = content.split(";");
			for (String sysDicValue : valueArray) {
				String[] sysDicValueArray = sysDicValue.split(":");
				if (sysDicValueArray.length == 2) {
					KV sys = new KV();
					try {
						sys.setId(Long.parseLong(sysDicValueArray[0]));
					} catch (Exception e) {

					}
					sys.setValue(sysDicValueArray[0]);
					sys.setName(sysDicValueArray[1]);
					result.add(sys);
				}
			}
		}
		return result;
	}

}


