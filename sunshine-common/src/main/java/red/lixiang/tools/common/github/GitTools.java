package red.lixiang.tools.common.github;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import red.lixiang.tools.common.github.model.ImageBed;
import red.lixiang.tools.common.github.model.CommitRes;
import red.lixiang.tools.common.github.model.UploadFileReq;
import red.lixiang.tools.common.github.model.UploadFileRes;
import red.lixiang.tools.jdk.FileTools;
import red.lixiang.tools.jdk.JSONTools;
import red.lixiang.tools.jdk.StringTools;
import red.lixiang.tools.jdk.URLTools;
import red.lixiang.tools.jdk.http.HttpRequest;
import red.lixiang.tools.jdk.http.HttpResponse;
import red.lixiang.tools.jdk.http.HttpTools;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

import static red.lixiang.tools.common.github.GitConfig.TYPE_GITEE;
import static red.lixiang.tools.common.github.GitConfig.TYPE_GITHUB;

/**
 * @author lixiang
 * @date 2020/3/16
 **/
public class GitTools {



    public static List<String> commitFileUrlList(GitConfig config,Integer page) {
        if(page ==null){
            page = 1;
        }
        List<String> urlList = new ArrayList<>();
        List<CommitRes> commitResList = lastCommits(config, page, null);
        for (CommitRes commitRes : commitResList) {
            List<CommitRes> commitDetail = lastCommits(config, null, commitRes.getSha());
            CommitRes.File file = commitDetail.get(0).getFiles().get(0);
            urlList.add(file.rawUrl());
        }
        return urlList;
    }

    /**
     * 查询git的提交记录
     *
     * @param config
     * @param page   如果是查列表,要传这个值,sha可以传null
     * @param sha    如果查单个的某个详情,则要传这个值, page可以传null
     * @return
     */
    public static List<CommitRes> lastCommits(GitConfig config, Integer page, String sha) {
        HttpRequest request = config.lastCommitRequest(sha);
        if (page != null) {
            request.getParamMap().put("page", String.valueOf(page));
        }
        if (TYPE_GITEE == config.getGitType()) {
            request.setHttpMethod(HttpRequest.METHOD_GET);
            request.getParamMap().put("access_token", config.getPersonToken());
        }
        if (TYPE_GITHUB == config.getGitType()) {
            request.setHttpMethod(HttpRequest.METHOD_GET);
            request.getHeaderMap().put("Authorization", "token " + config.getPersonToken());
        }
        HttpResponse<String> response = HttpTools.doInvoke(request, String.class);
        String body = response.getBody();
        if (StringTools.isNotBlank(sha)) {
            //只查一个的时候, 返回值不是List
            CommitRes commitRes = JSONTools.toObject(body, CommitRes.class);
            return Collections.singletonList(commitRes);
        }
        List<CommitRes> commitRes = JSONArray.parseArray(body, CommitRes.class);
        return commitRes;
    }

    /**
     * 上传文件
     *
     * @param file
     * @param config
     * @return
     */
    public static UploadFileRes uploadFile(File file, GitConfig config) {
        String suffix = FileTools.getSuffixName(file.getName());
        UploadFileReq req = new UploadFileReq();
        req.setSuffix(suffix);
        req.setContent(FileTools.fileBase64Content(file));
        return uploadFile(req, config);
    }


    /**
     * 上传文件
     *
     * @param req
     * @param config
     * @return
     */
    public static UploadFileRes uploadFile(UploadFileReq req, GitConfig config) {
        LocalDate now = LocalDate.now();
        String fileName = now.getYear() + "/" + now.getMonthValue() + "/" + now.getDayOfMonth() + "/" + System.currentTimeMillis() + req.getSuffix();
        HttpRequest request = config.uploadFileRequest(fileName);
        String bodyContent = JSON.toJSONString(req.toMap());
        Map<String, String> header = HttpRequest.JSON_HEADER;

        if (TYPE_GITEE == config.getGitType()) {
            request.setHttpMethod(HttpRequest.METHOD_POST);
            Map<String, Object> map = req.toMap();
            map.put("access_token", config.getPersonToken());
            map.remove("committer");
            bodyContent = JSON.toJSONString(map);
            header.put("Host", "gitee.com");
            header.put("User-Agent", "Sunflower");
        }

        if (TYPE_GITHUB == config.getGitType()) {
            request.setHttpMethod(HttpRequest.METHOD_PUT);
            header.put("Authorization", "token " + config.getPersonToken());
        }
        if (StringTools.isNotBlank(config.getProxyUrl()) && (config.getProxyPort() != null)) {
            request.setProxyHost(config.getProxyUrl()).setProxyPort(config.getProxyPort());
        }

        request.setBodyContent(bodyContent);
        request.setHeaderMap(header);

        HttpResponse<UploadFileRes> uploadFileResResponse = HttpTools.doInvoke(request, UploadFileRes.class);
//        System.out.println(uploadFileResResponse);
        return uploadFileResResponse.getBody();
    }

    public static ImageBed convertUploadFileRes(String originUrl) {
        String cdnHost = "https://cdn.jsdelivr.net/gh/";

        //markdown的格式
        String markdownUrl = String.format("![](%s)", originUrl);
        String removeHost = URLTools.removeHost(originUrl).replace("/master", "");
        String cdnUrl = cdnHost + removeHost;
        String cdnMarkdownUrl = String.format("![](%s)", cdnUrl);
        ImageBed bed = new ImageBed();
        bed.setOriginUrl(originUrl)
                .setMarkdownUrl(markdownUrl)
                .setCdnUrl(cdnUrl)
                .setCdnMarkdownUrl(cdnMarkdownUrl);

        return bed;
    }

}
