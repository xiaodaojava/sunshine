package red.lixiang.tools.common.jianyingpro.models;

public class Text {

    public static final String TYPE_SUBTITLE = "subtitle";

    private String id;

    private String type;

    private String content;

    public String getId() {
        return id;
    }

    public Text setId(String id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public Text setType(String type) {
        this.type = type;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Text setContent(String content) {
        this.content = content;
        return this;
    }
}
