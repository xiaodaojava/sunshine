package red.lixiang.tools.jdk.http;

import java.util.Map;

/**
 * @author lixiang
 */
public class HttpRequest {
  private String url;
  private String httpMethod;
  private int connectTimeout;
  private int readTimeout;
  private String proxyHost;
  private Integer proxyPort;

  private Map<String,String> paramMap;

  /**
   * Create the request for the url.
   * @param url the url
   */
  public HttpRequest(String url) {
    this.url = url;
    connectTimeout = 3000;
    readTimeout = 3000;
  }

  public HttpRequest(String url, Map<String, String> paramMap) {
    this.url = url;
    this.paramMap = paramMap;
  }

  public Map<String, String> getParamMap() {
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
