package red.lixiang.tools.common.github;

import red.lixiang.tools.jdk.StringTools;
import red.lixiang.tools.jdk.http.HttpRequest;

/**
 * @author lixiang
 * @date 2020/3/16
 **/
public class GitConfig {

    public static final String GITHUB_HOST = "https://api.github.com";
    
    public static final String GITEE_HOST = "https://gitee.com/api/v5";

    public static final int TYPE_GITHUB = 0;

    public static final int TYPE_GITEE = 1;

    public String githubUploadFileUrl(){
        return GITHUB_HOST +"/repos/"+owner+"/"+repo+"/contents/";
    }

    public String giteeUploadFileUrl(){
        return GITEE_HOST+"/repos/"+owner+"/"+repo+"/contents/";
    }

    public String lastCommitUrl(String sha){
        String url = (gitType == TYPE_GITHUB ? GITHUB_HOST : GITEE_HOST) +
                "/repos/" +
                owner + "/" +
                repo + "/" +
                "commits";
        if(StringTools.isNotBlank(sha)){
            url = url+"/"+sha;
        }
        return url;
    }

    public HttpRequest lastCommitRequest(String sha){
        HttpRequest httpRequest = new HttpRequest(lastCommitUrl(sha));
        httpRequest.setHeaderMap(HttpRequest.AGENT_HEADER);
        return httpRequest;
    }

    public HttpRequest uploadFileRequest(String fileName){
        String url  = this.gitType==TYPE_GITHUB?githubUploadFileUrl():giteeUploadFileUrl();
        return new HttpRequest(url+fileName);
    }

    /**
     * 当前配置的标识
     */
    private Long id;

    private String tag;

    private Integer gitType;

    /**
     * 仓库名称
     */
    private String repo;

    /**
     * 仓库拥有者
     */
    private String owner;

    /**
     * 个人的token
     */
    private String personToken;

    /**
     * 连github的代理url
     */
    private String proxyUrl;

    /**
     * 连github的代理端口
     */
    private Integer proxyPort;

    /**
     * 是否是当前选中的
     */
    private Boolean selected;

    public Boolean getSelected() {
        return selected;
    }

    public GitConfig setSelected(Boolean selected) {
        this.selected = selected;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public GitConfig setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public Integer getGitType() {
        return gitType;
    }

    public GitConfig setGitType(Integer gitType) {
        this.gitType = gitType;
        return this;
    }

    public Long getId() {
        return id;
    }

    public GitConfig setId(Long id) {
        this.id = id;
        return this;
    }

    public String getProxyUrl() {
        return proxyUrl;
    }

    public GitConfig setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
        return this;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public GitConfig setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
        return this;
    }

    public String getRepo() {
        return repo;
    }

    public GitConfig setRepo(String repo) {
        this.repo = repo;
        return this;
    }

    public String getOwner() {
        return owner;
    }

    public GitConfig setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public String getPersonToken() {
        return personToken;
    }

    public GitConfig setPersonToken(String personToken) {
        this.personToken = personToken;
        return this;
    }
}
