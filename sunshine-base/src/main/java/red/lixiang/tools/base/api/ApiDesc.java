package red.lixiang.tools.base.api;

import java.util.List;

/**
 * 这是一个描述API信息的类
 */
public class ApiDesc  {


    private static final String TYPE_HTTP = "HTTP";

    private static final String TYPE_DUBBO = "DUBBO";

    /** 接口的 url */
    private String url;

    /** http,dubbo等 */
    private String apiType;

    /** api标题 */
    private String title;

    /** 说明 */
    private String remark;

    /** 入参 */
    private List<ApiParam> params;

    /** 输出的模板 */
    private String template;


    public String getUrl() {
        return url;
    }

    public ApiDesc setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getApiType() {
        return apiType;
    }

    public ApiDesc setApiType(String apiType) {
        this.apiType = apiType;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ApiDesc setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public ApiDesc setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public List<ApiParam> getParams() {
        return params;
    }

    public ApiDesc setParams(List<ApiParam> params) {
        this.params = params;
        return this;
    }
}

