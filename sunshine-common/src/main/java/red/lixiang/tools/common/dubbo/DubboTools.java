package red.lixiang.tools.common.dubbo;


import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.Map;

import static red.lixiang.tools.common.dubbo.DubboToolCache.DUBBO_SERVICE_CACHE;

/**
 * @author lixiang
 * @date 2020/10/18
 **/
public class DubboTools {


    public static Object invokeNoArg(String url, String interfaceName, String methodName) {
        DubboInvokeConfig config = new DubboInvokeConfig();
        config.setRegistryAddress(url);
        config.setInterfaceName(interfaceName);
        config.setMethodName(methodName);
        return doInvoke(config);
    }

    public static Object invokeWithArgs(String url, String interfaceName, String methodName, String[] types, Object[] objs) {
        DubboInvokeConfig config = new DubboInvokeConfig();
        config.setRegistryAddress(url);
        config.setInterfaceName(interfaceName);
        config.setMethodName(methodName);
        config.setArgTypes(types);
        config.setArgObjects(objs);
        return doInvoke(config);
    }


    public static Object doInvoke(DubboInvokeConfig config) {
        // 通过hashMap把对象缓存起来
        String key = config.getInterfaceName() + "#" + config.getMethodName();
        GenericService genericService = DUBBO_SERVICE_CACHE.computeIfAbsent(key, (k) -> {
            ReferenceConfig<GenericService> referenceConfig = ReferenceFactory.buildReferenceConfig(config);
            return referenceConfig.get();
        });

        Map<String, String> attachments = config.getAttachments();
        if (attachments != null) {
            RpcContext.getContext().setAttachments(attachments);
        }
        Object result = genericService.$invoke(config.getMethodName(), config.getArgTypes(), config.getArgObjects());
        return result;
    }
}
