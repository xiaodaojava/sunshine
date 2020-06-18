package red.lixiang.tools.common.github.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author lixiang
 * @date 2020/5/3
 **/
public class CommitRes {
    private String url;
    private String sha;

    // 因为这是一个record类型, 用fastjson解析会出错,所以先禁用掉fastJson的解析
    @JSONField(serialize = false,deserialize = false)
    List<File> files;

    public String getUrl() {
        return url;
    }

    public CommitRes setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getSha() {
        return sha;
    }

    public CommitRes setSha(String sha) {
        this.sha = sha;
        return this;
    }

    public List<File> getFiles() {
        return files;
    }

    public CommitRes setFiles(List<File> files) {
        this.files = files;
        return this;
    }

    public static class File{
        @SerializedName("raw_url")
        private String rawUrl;
        private String status;

        public boolean removed(){
            return "removed".equals(status);
        }

        public String getStatus() {
            return status;
        }

        public File setStatus(String status) {
            this.status = status;
            return this;
        }

        public String getRawUrl() {
            return rawUrl;
        }

        public File setRawUrl(String rawUrl) {
            this.rawUrl = rawUrl;
            return this;
        }
    };

}
