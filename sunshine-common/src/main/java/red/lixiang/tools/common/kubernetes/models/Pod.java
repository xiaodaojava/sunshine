package red.lixiang.tools.common.kubernetes.models;

/**
 * @author lixiang
 * @date 2020/2/23
 **/
public class Pod {

    private String name;
    private String namespace;

    public String getName() {
        return name;
    }

    public Pod setName(String name) {
        this.name = name;
        return this;
    }

    public String getNamespace() {
        return namespace;
    }

    public Pod setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }
}
