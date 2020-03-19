package red.lixiang.tools.common.github.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author lixiang
 * @date 2020/3/16
 **/
public class UploadFileRes {

    private Content content;

    public Content getContent() {
        return content;
    }

    public UploadFileRes setContent(Content content) {
        this.content = content;
        return this;
    }

    public static class Content{
        String name;
        String path;
        String sha;
        String url;
        @JSONField(name = "html_url")
        String htmlUrl;
        @JSONField(name = "git_url")
        String gitUrl;
        @JSONField(name = "download_url")
        String downloadUrl;

        public String getName() {
            return name;
        }

        public Content setName(String name) {
            this.name = name;
            return this;
        }

        public String getPath() {
            return path;
        }

        public Content setPath(String path) {
            this.path = path;
            return this;
        }

        public String getSha() {
            return sha;
        }

        public Content setSha(String sha) {
            this.sha = sha;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public Content setUrl(String url) {
            this.url = url;
            return this;
        }

        public String getHtmlUrl() {
            return htmlUrl;
        }

        public Content setHtmlUrl(String htmlUrl) {
            this.htmlUrl = htmlUrl;
            return this;
        }

        public String getGitUrl() {
            return gitUrl;
        }

        public Content setGitUrl(String gitUrl) {
            this.gitUrl = gitUrl;
            return this;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public Content setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
            return this;
        }
    }

    public static void main(String[] args) {



    }


}
