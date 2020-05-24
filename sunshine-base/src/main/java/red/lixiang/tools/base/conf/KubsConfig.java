package red.lixiang.tools.base.conf;

import java.io.Serializable;

/**
 * @author lixiang
 * @date 2020/2/21
 **/
public class KubsConfig implements Serializable {

    private static final long serialVersionUID = 3787713783251533055L;
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
