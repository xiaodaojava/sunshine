

package red.lixiang.tools.common.dubbo;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.util.StringUtils;

/**
 * 服务引用工厂类
 *
 * @author idea
 * @date 2020/1/2
 * @version V1.0
 */
public class ReferenceFactory {


    private static String APP_NAME = "dubbo-invoker-proxy";

    private static Integer DEFAULT_TIME_OUT = 3000;


    private static boolean DEFAULT_ASYNC = false;

    private static int DEFAULT_RETRIES = 3;

    /**
     * @return
     */
    public static ReferenceConfig<GenericService> buildReferenceConfig(DubboInvokeConfig param) {
        if (StringUtils.isEmpty(param.getRetries())) {
            param.setRetries(DEFAULT_RETRIES);
        }
        if (StringUtils.isEmpty(param.isAsync())) {
            param.setAsync(DEFAULT_ASYNC);
        }
        if (StringUtils.isEmpty(param.getLoadbalance())) {
            param.setLoadbalance("random");
        }
        if (StringUtils.isEmpty(param.getTimeout())) {
            param.setTimeout(DEFAULT_TIME_OUT);
        }

        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(new ApplicationConfig(APP_NAME));

        reference.setInterface(param.getInterfaceName());
        reference.setVersion(param.getVersion());
        reference.setGeneric("true");
        if (!StringUtils.isEmpty(param.getGroup())) {
            reference.setGroup(param.getGroup());
        }
        //默认重试次数为3次
        if (param.getRetries() < 0) {
            reference.setRetries(param.getRetries());
        }
//        reference.setCluster("failfast");
        reference.setTimeout(param.getTimeout());
        reference.setAsync(param.isAsync());
        reference.setActives(param.getActives());
        reference.setLoadbalance(param.getLoadbalance());
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress(param.getRegistryAddress());
        reference.setRegistry(registry);
        if (!StringUtils.isEmpty(param.getUrl())) {
            reference.setUrl(param.getUrl());
        }
        return reference;
    }
}
