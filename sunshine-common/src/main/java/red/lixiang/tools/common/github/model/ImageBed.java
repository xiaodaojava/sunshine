package red.lixiang.tools.common.github.model;

/**
 * @author lixiang
 * @date 2020/3/16
 **/
public class ImageBed {

    /** 原始的url */
    private String originUrl;
    /** 转成Markdown的url */
    private String markdownUrl;
    /** 加了jsdelivr的cdn */
    private String cdnUrl;
    /** 加了cdn的jsdelivr的markdown链接 */
    private String cdnMarkdownUrl;

    public String getOriginUrl() {
        return originUrl;
    }

    public ImageBed setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
        return this;
    }

    public String getMarkdownUrl() {
        return markdownUrl;
    }

    public ImageBed setMarkdownUrl(String markdownUrl) {
        this.markdownUrl = markdownUrl;
        return this;
    }

    public String getCdnUrl() {
        return cdnUrl;
    }

    public ImageBed setCdnUrl(String cdnUrl) {
        this.cdnUrl = cdnUrl;
        return this;
    }

    public String getCdnMarkdownUrl() {
        return cdnMarkdownUrl;
    }

    public ImageBed setCdnMarkdownUrl(String cdnMarkdownUrl) {
        this.cdnMarkdownUrl = cdnMarkdownUrl;
        return this;
    }
}
