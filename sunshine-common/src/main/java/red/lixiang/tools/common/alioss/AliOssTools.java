package red.lixiang.tools.common.alioss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.OSSObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @Author lixiang
 * @CreateTime 2019/9/11
 **/
public class AliOssTools {

    private OSSClient ossClient;



    private OSSConfig ossConfig;

    public AliOssTools init(){
        DefaultCredentialProvider provider = new DefaultCredentialProvider(ossConfig.getAccessKey(), ossConfig.getAccessSecret());
        OSSClient client = new OSSClient(ossConfig.getEndpoint(),provider,null);
        this.ossClient = client;
        return this;
    }

    public AliOssTools setOssConfig(OSSConfig ossConfig) {
        this.ossConfig = ossConfig;
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
        this.ossClient.putObject(ossConfig.getBucket(), fileName, inputStream);
    }

    public void saveByteToYun(String fileName, byte[] bytes) {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        this.ossClient.putObject(ossConfig.getBucket(), fileName, inputStream);
    }

    public InputStream getStreamFromYun(String fileName){
        OSSObject object = this.ossClient.getObject(ossConfig.getBucket(), fileName);
        InputStream objectContent = object.getObjectContent();
        return objectContent;
    }


}
