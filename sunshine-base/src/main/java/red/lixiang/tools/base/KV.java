package red.lixiang.tools.base;

/**
 * @author lixiang
 * @date 2019/12/28
 **/
public class KV {
    private Long id;
    private String name;
    private String value;

    public KV() {
    }

    public KV(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public KV setId(Long id) {
        this.id = id;
        return this;
    }

    public String getValue() {
        return value;
    }

    public KV setValue(String value) {
        this.value = value;
        return this;
    }

    public String getName() {
        return name;
    }

    public KV setName(String name) {
        this.name = name;
        return this;
    }
}
