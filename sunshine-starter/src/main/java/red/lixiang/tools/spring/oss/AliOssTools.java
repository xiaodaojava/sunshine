package red.lixiang.tools.spring.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @Author lixiang
 * @CreateTime 2019/9/11
 **/
public class AliOssTools {

    private OSSClient ossClient;


    @Autowired
    private OSSProperty ossProperty;


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
        this.ossClient.putObject(ossProperty.getBucket(), fileName, inputStream);
    }

    public void saveByteToYun(String fileName, byte[] bytes) {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        this.ossClient.putObject(ossProperty.getBucket(), fileName, inputStream);
    }

    public InputStream getStreamFromYun(String fileName){
        OSSObject object = this.ossClient.getObject(ossProperty.getBucket(), fileName);
        InputStream objectContent = object.getObjectContent();
        return objectContent;
    }


}
