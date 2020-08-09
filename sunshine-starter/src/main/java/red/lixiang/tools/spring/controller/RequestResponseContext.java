package red.lixiang.tools.spring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestResponseContext {
	private static ThreadLocal<HttpServletRequest> REQUEST_LOCAL = new ThreadLocal<HttpServletRequest>();
	 
    private static ThreadLocal<HttpServletResponse> RESPONSE_LOCAL = new ThreadLocal<HttpServletResponse>();
 
    public static void setRequest(HttpServletRequest request) {
        REQUEST_LOCAL.set(request);
    }
 
    public static HttpServletRequest getRequest() {
        return REQUEST_LOCAL.get();
    }
 
    public static void removeRequest() {
        REQUEST_LOCAL.remove();
    }
 
    public static void setResponse(HttpServletResponse response) {
        RESPONSE_LOCAL.set(response);
    }
 
    public static HttpServletResponse getResponse() {
        return RESPONSE_LOCAL.get();
    }
 
    public static void removeResponse() {
        RESPONSE_LOCAL.remove();
    }
}
