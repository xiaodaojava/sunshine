package red.lixiang.tools.common.github;

import com.alibaba.fastjson.JSON;
import red.lixiang.tools.common.github.model.ImageBed;
import red.lixiang.tools.common.github.model.UploadFileReq;
import red.lixiang.tools.common.github.model.UploadFileRes;
import red.lixiang.tools.jdk.FileTools;
import red.lixiang.tools.jdk.StringTools;
import red.lixiang.tools.jdk.URLTools;
import red.lixiang.tools.jdk.http.HttpRequest;
import red.lixiang.tools.jdk.http.HttpResponse;
import red.lixiang.tools.jdk.http.HttpTools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static red.lixiang.tools.common.github.GitConfig.TYPE_GITEE;
import static red.lixiang.tools.common.github.GitConfig.TYPE_GITHUB;

/**
 * @author lixiang
 * @date 2020/3/16
 **/
public class GitTools {



    public static UploadFileRes uploadFile (UploadFileReq req, GitConfig config){
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dir = now.format(formatter);
        String fileName = dir+"/"+ System.currentTimeMillis()+req.getSuffix();
        HttpRequest request = config.httpRequest(fileName);
        String bodyContent = JSON.toJSONString(req.toMap());
        Map<String, String> header = HttpRequest.JSON_HEADER;

        if(TYPE_GITEE == config.getGitType()){
            request.setHttpMethod(HttpRequest.METHOD_POST);
            Map<String, Object> map = req.toMap();
            map.put("access_token",config.getPersonToken());
            map.remove("committer");
            bodyContent = JSON.toJSONString(map);
            header.put("Host","gitee.com");
            header.put("User-Agent","Sunflower");
        }

        if(TYPE_GITHUB == config.getGitType()){
            request.setHttpMethod(HttpRequest.METHOD_PUT);
            header.put("Authorization","token "+config.getPersonToken());
        }
        if(StringTools.isNotBlank(config.getProxyUrl()) && (config.getProxyPort()!=null)){
            request.setProxyHost(config.getProxyUrl()).setProxyPort(config.getProxyPort());
        }

        request.setBodyContent(bodyContent);
        request.setHeaderMap(header);

        HttpResponse<UploadFileRes> uploadFileResResponse = HttpTools.doInvoke(request, UploadFileRes.class);
//        System.out.println(uploadFileResResponse);
        return uploadFileResResponse.getBody();
    }

    public static ImageBed convertUploadFileRes(UploadFileRes res){
        String cdnHost = "https://cdn.jsdelivr.net/gh/";
        if(res ==null){
            return null;
        }
        UploadFileRes.Content content = res.getContent();
        String originUrl  = content.getDownloadUrl();
        //markdown的格式
        String markdownUrl = String.format("![](%s)", content.getDownloadUrl());
        String removeHost = URLTools.removeHost(originUrl).replace("/master","");
        String cdnUrl = cdnHost+removeHost;
        String cdnMarkdownUrl  = String.format("![](%s)",cdnUrl);
        ImageBed bed  = new ImageBed();
        bed.setOriginUrl(originUrl)
                .setMarkdownUrl(markdownUrl)
                .setCdnUrl(cdnUrl)
                .setCdnMarkdownUrl(cdnMarkdownUrl);

        return bed;
    }

}
