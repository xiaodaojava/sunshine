package red.lixiang.tools.base.api;

public class ApiParam {

    /** String ,Integer 等 */
    private String type;

    /** 参数名称 */
    private String name;

    /** 默认值 */
    private String defaultValue;

    public String getType() {
        return type;
    }

    public ApiParam setType(String type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public ApiParam setName(String name) {
        this.name = name;
        return this;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public ApiParam setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }
}
