package red.lixiang.tools.jdk.http;

import java.net.CookieManager;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lixiang
 */
public class HttpRequest {

  public static final String METHOD_POST = "POST";
  public static final String METHOD_GET = "GET";
  public static final String METHOD_PUT = "PUT";


  private String url;
  private String httpMethod = METHOD_GET;
  private int connectTimeout;
  private int readTimeout;
  private String proxyHost;
  private Integer proxyPort;

  /**
   * 管理cookie的
   */
  private CookieManager cookieManager;

  /**
   * 跟在url后面的
   */
  private Map<String,String> paramMap;

  private String bodyContent;

  private Map<String,String> headerMap;


  public static Map<String,String> JSON_HEADER = new HashMap<>();

  public static Map<String,String> AGENT_HEADER = new HashMap<>();
  public static Map<String,String> FORM_URL_ENCODING_HEADER = new HashMap<>();

  static {
    JSON_HEADER.put("Content-Type", "application/json");
    FORM_URL_ENCODING_HEADER.put("Content-Type", " application/x-www-form-urlencoded");
    AGENT_HEADER.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");
  }

  /**
   * Create the request for the url.
   * @param url the url
   */
  public HttpRequest(String url) {
    this.url = url;
    connectTimeout = 3000;
    readTimeout = 3000;
  }

  public CookieManager getCookieManager() {
    return cookieManager;
  }

  public HttpRequest setCookieManager(CookieManager cookieManager) {
    this.cookieManager = cookieManager;
    return this;
  }

  public HttpRequest(String url, Map<String, String> paramMap) {
    this.url = url;
    this.paramMap = paramMap;
  }

  public String getBodyContent() {
    return bodyContent;
  }

  public HttpRequest setBodyContent(String bodyContent) {
    this.bodyContent = bodyContent;
    return this;
  }

  public Map<String, String> getHeaderMap() {
    if(cookieManager!=null){
//      List<HttpCookie> cookies = cookieManager.getCookieStore().getCookies();

    }
    if(headerMap==null){
      headerMap = new HashMap<>();
    }
    return headerMap;
  }

  public HttpRequest setHeaderMap(Map<String, String> headerMap) {
    this.headerMap = headerMap;
    return this;
  }

  public Map<String, String> getParamMap() {
    if(paramMap == null){
      paramMap = new HashMap<>();
    }
    return paramMap;
  }

  public HttpRequest setParamMap(Map<String, String> paramMap) {
    this.paramMap = paramMap;
    return this;
  }

  public HttpRequest setUrl(String url) {
    this.url = url;
    return this;
  }

  public String getHttpMethod() {
    return httpMethod;
  }

  public HttpRequest setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
    return this;
  }

  public String getProxyHost() {
    return proxyHost;
  }

  public HttpRequest setProxyHost(String proxyHost) {
    this.proxyHost = proxyHost;
    return this;
  }

  public Integer getProxyPort() {
    return proxyPort;
  }

  public HttpRequest setProxyPort(Integer proxyPort) {
    this.proxyPort = proxyPort;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public int getConnectTimeout() {
    return connectTimeout;
  }

  public void setConnectTimeout(int connectTimeout) {
    this.connectTimeout = connectTimeout;
  }

  public int getReadTimeout() {
    return readTimeout;
  }

  public void setReadTimeout(int readTimeout) {
    this.readTimeout = readTimeout;
  }
}
