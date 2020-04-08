package red.lixiang.tools.starter.spring.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import red.lixiang.tools.spring.oss.OSSProperty;

/**
 * @Author lixiang
 * @CreateTime 2019/9/11
 **/
@Component
@ConfigurationProperties(prefix = "tools")
public class ToolsProperty {

    /** 保存随机字符串的表名 */
    private String saltTable;

    private OSSProperty oss;

    public OSSProperty getOss() {
        return oss;
    }

    public ToolsProperty setOss(OSSProperty oss) {
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
