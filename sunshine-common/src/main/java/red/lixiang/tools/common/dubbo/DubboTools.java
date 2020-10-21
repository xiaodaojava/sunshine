package red.lixiang.tools.common.dubbo;


import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.Map;

/**
 * @author lixiang
 * @date 2020/10/18
 **/
public class DubboTools {


    public static void invokeNoArg(String url,String interfaceName, String methodName){
        DubboInvokeConfig config = new DubboInvokeConfig();
        config.setRegistryAddress(url);
        config.setInterfaceName(interfaceName);
        config.setMethodName(methodName);
        doInvoke(config);
    }

    public static void invokeWithArgs(String url,String interfaceName, String methodName,String[] types,Object[] objs){
        DubboInvokeConfig config = new DubboInvokeConfig();
        config.setRegistryAddress(url);
        config.setInterfaceName(interfaceName);
        config.setMethodName(methodName);
        config.setArgTypes(types);
        config.setArgObjects(objs);
        doInvoke(config);
    }



    public static void doInvoke(DubboInvokeConfig config) {
        // 这个对象后面要缓存起来
        ReferenceConfig<GenericService> referenceConfig = ReferenceFactory.buildReferenceConfig(config);
        GenericService genericService = referenceConfig.get();
        Map<String, String> attachments = config.getAttachments();
        if (attachments != null) {
            RpcContext.getContext().setAttachments(attachments);
        }
        Object result = genericService.$invoke(config.getMethodName(), config.getArgTypes(), config.getArgObjects());
        System.out.println(result);
    }
}
