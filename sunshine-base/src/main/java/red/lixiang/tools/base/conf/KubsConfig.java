package red.lixiang.tools.base.conf;

/**
 * @author lixiang
 * @date 2020/2/21
 **/
public class KubsConfig {

    private String confPath;

    private String confContent;

    public String getConfPath() {
        return confPath;
    }

    public KubsConfig setConfPath(String confPath) {
        this.confPath = confPath;
        return this;
    }

    public String getConfContent() {
        return confContent;
    }

    public KubsConfig setConfContent(String confContent) {
        this.confContent = confContent;
        return this;
    }
}
