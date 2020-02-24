package red.lixiang.tools.base.conf;

import java.util.List;

/**
 * 这个是总的配置类.
 * @author lixiang
 * @date 2020/2/21
 **/
public class ToolConfig {

    List<KubsConfig> kubsConfigList;

    List<MySqlConfig> mySqlConfigList;


    public List<KubsConfig> getKubsConfigList() {
        return kubsConfigList;
    }

    public ToolConfig setKubsConfigList(List<KubsConfig> kubsConfigList) {
        this.kubsConfigList = kubsConfigList;
        return this;
    }

    public List<MySqlConfig> getMySqlConfigList() {
        return mySqlConfigList;
    }

    public ToolConfig setMySqlConfigList(List<MySqlConfig> mySqlConfigList) {
        this.mySqlConfigList = mySqlConfigList;
        return this;
    }
}
