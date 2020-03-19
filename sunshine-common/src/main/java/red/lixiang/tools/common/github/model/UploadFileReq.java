package red.lixiang.tools.common.github.model;

import red.lixiang.tools.base.CommonModel;
import red.lixiang.tools.base.annotation.EnhanceTool;

/**
 * @author lixiang
 * @date 2020/3/16
 **/
public class UploadFileReq implements CommonModel {

    /**
     * 文件后缀名
     */
    @EnhanceTool(skipToMap = true)
    private String suffix;

    /**
     * commit message
     */
    private String message = "公众号:java技术大本营";

    /**
     * 文件内容
     */
    private String content;

    /**
     * 提交人信息
     */
    private Committer committer = Committer.DEFAULT;

    public Committer getCommitter() {
        return committer;
    }

    public UploadFileReq setCommitter(Committer committer) {
        this.committer = committer;
        return this;
    }

    static class Committer{
        String name;
        String email;

        public String getName() {
            return name;
        }
        public String getEmail() {
            return email;
        }

        public Committer(String name, String email) {
            this.name = name;
            this.email = email;
        }
        static Committer DEFAULT = new Committer("公众号:java技术大本营","lixiang9409@vip.qq.com");
    }

    public String getSuffix() {
        return suffix;
    }

    public UploadFileReq setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public UploadFileReq setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getContent() {
        return content;
    }

    public UploadFileReq setContent(String content) {
        this.content = content;
        return this;
    }
}
