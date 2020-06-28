package red.lixiang.tools.spring.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.OSSObject;
import org.springframework.beans.factory.annotation.Autowired;
import red.lixiang.tools.starter.spring.boot.ToolsProperty;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @Author lixiang
 * @CreateTime 2019/9/11
 **/
public class AliOssTools {

    private OSSClient ossClient;



    private ToolsProperty toolsProperty;

    public AliOssTools init(){
        OSSProperty ossProperty = toolsProperty.getOss();
        DefaultCredentialProvider provider = new DefaultCredentialProvider(ossProperty.getAccessKey(),ossProperty.getAccessSecret());
        OSSClient client = new OSSClient(ossProperty.getEndpoint(),provider,null);
        this.ossClient = client;
        return this;
    }

    public AliOssTools setToolsProperty(ToolsProperty toolsProperty) {
        this.toolsProperty = toolsProperty;
        return this;
    }

    public AliOssTools setOssClient(OSSClient ossClient) {
        this.ossClient = ossClient;
        return this;
    }


    /**
     * 保存文件到文件服务器
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public void saveFileToYun(String fileName, InputStream inputStream) {
        OSSProperty ossProperty = toolsProperty.getOss();
        this.ossClient.putObject(ossProperty.getBucket(), fileName, inputStream);
    }

    public void saveByteToYun(String fileName, byte[] bytes) {
        OSSProperty ossProperty = toolsProperty.getOss();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        this.ossClient.putObject(ossProperty.getBucket(), fileName, inputStream);
    }

    public InputStream getStreamFromYun(String fileName){
        OSSProperty ossProperty = toolsProperty.getOss();
        OSSObject object = this.ossClient.getObject(ossProperty.getBucket(), fileName);
        InputStream objectContent = object.getObjectContent();
        return objectContent;
    }

    public ToolsProperty getToolsProperty() {
        return toolsProperty;
    }


}
