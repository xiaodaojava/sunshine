package red.lixiang.tools.starter.spring.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import red.lixiang.tools.common.alioss.OSSConfig;

/**
 * @Author lixiang
 * @CreateTime 2019/9/11
 **/
@Component
@ConfigurationProperties(prefix = "tools")
public class ToolsProperty {

    /** 保存随机字符串的表名 */
    private String saltTable;

    private OSSConfig oss;

    public OSSConfig getOss() {
        return oss;
    }

    public ToolsProperty setOss(OSSConfig oss) {
        this.oss = oss;
        return this;
    }

    public String getSaltTable() {
        return saltTable;
    }

    public ToolsProperty setSaltTable(String saltTable) {
        this.saltTable = saltTable;
        return this;
    }
}
