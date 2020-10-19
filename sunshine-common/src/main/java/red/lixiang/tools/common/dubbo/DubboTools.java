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

    public static void main(String[] args) {
        DubboInvokeConfig param = new DubboInvokeConfig();
        ReferenceConfig<GenericService> referenceConfig = ReferenceFactory.buildReferenceConfig(param);
        GenericService genericService = referenceConfig.get();
        Map<String, String> attachments = param.getAttachments();
        if (attachments != null) {
            RpcContext.getContext().setAttachments(attachments);
        }
        Object result = genericService.$invoke(param.getMethodName(), param.getArgTypes(), param.getArgObjects());
        System.out.println(result);
    }
}
