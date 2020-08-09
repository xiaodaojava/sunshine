package red.lixiang.tools.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import red.lixiang.tools.jdk.StringTools;
import red.lixiang.tools.jdk.io.IOTools;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class CommonController {

    protected Logger logger = LoggerFactory.getLogger(CommonController.class);


    @ModelAttribute
    public void setCommonModel(HttpServletRequest request, HttpServletResponse response) {
        RequestResponseContext.setRequest(request);
        RequestResponseContext.setResponse(response);
    }

    public String getIpAddress(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (!StringTools.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip.split(",")[0];
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-real-ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;


//        // Nginx设置的IP：http_x_forwarded_for
//        String clientIp = request.getHeader("X-Real-IP");
//        clientIp = clientIp !=null && !"unknown".equalsIgnoreCase(clientIp) ? clientIp : "";
//
//        // 去除http_x_forwarded_for中的360网站卫士IP
//        if (clientIp.contains(",")) {
//            String[] splitIps = clientIp.split(",");
//            clientIp = splitIps[0];
//        }
//
//        // 如果是直接通过nginx访问，则获取：remote_addr
//        if (clientIp == null || clientIp.isEmpty()) {
//            clientIp = request.getHeader("X-Remote-IP");
//        }
//
//        if (clientIp == null || clientIp.isEmpty()) {
//            clientIp = request.getHeader("x-forwarded-for");
//        }
//
//        if (clientIp == null || clientIp.isEmpty()) {
//            clientIp = request.getHeader("Proxy-Client-IP");
//        }
//
//        // 直接访问tomcat
//        if (clientIp == null || clientIp.isEmpty()) {
//            clientIp = request.getRemoteAddr();
//        }
//        return clientIp;
    }

    public String requestUrl() {
        return RequestResponseContext.getRequest().getRequestURI();
    }

    public HttpServletRequest getRequest() {
        return RequestResponseContext.getRequest();
    }

    public String postData() {
        HttpServletRequest request = getRequest();
        ServletInputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            String s = IOTools.readString(inputStream);
            inputStream.close();
            return s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HttpServletResponse getResponse() {
        return RequestResponseContext.getResponse();
    }

    public HttpSession getSession() {
        return getRequest().getSession();
    }


    public void writeFile(InputStream inputStream, String name, HttpServletResponse response, String fileName) {
        OutputStream os = null;
        try {
            if (fileName.contains(".")) {
                switch (fileName.split("\\.")[1].toLowerCase()) {
                    case "pdf":
                        this.getResponse().setContentType("application/pdf");
                        break;
                    case "png":
                        this.getResponse().setContentType("image/png");
                        break;
                    case "jpg":
                    case "jpeg":
                        this.getResponse().setContentType("image/jpeg");
                        break;
                    case "gif":
                        this.getResponse().setContentType("image/gif");
                        break;
                    //add more
                    default:
                        break;
                }
            }

            if (null != name && name.length() > 0) {
                this.getResponse().setHeader("Content-Disposition",
                        "attachment;filename=" + new String(name.getBytes(), "iso-8859-1"));
            }

            final int fileSize = 1 << 20;
            // 返回结果缓冲区
            byte[] buffer = new byte[fileSize];
            os = response.getOutputStream();
            int length;
            while ((length = inputStream.read(buffer, 0, fileSize)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

            throw new RuntimeException(e);
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    protected void writeToJson(Integer templates) {
        PrintWriter out = null;
        try {
            getResponse().setContentType("text/json; charset=utf-8");
            out = getResponse().getWriter();
            out.print(new ObjectMapper().writeValueAsString(templates));
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


}
