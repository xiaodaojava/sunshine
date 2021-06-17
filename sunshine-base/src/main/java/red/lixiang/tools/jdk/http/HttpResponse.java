package red.lixiang.tools.jdk.http;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class HttpResponse<T> {
  private String url;
  private final int statusCode;
  private final T body;

  public HttpResponse(int statusCode, T body) {
    this.statusCode = statusCode;
    this.body = body;
  }

  public HttpResponse(String url, int statusCode, T body) {
    this.url = url;
    this.statusCode = statusCode;
    this.body = body;
  }

  public String getUrl() {
    return url;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public T getBody() {
    return body;
  }
}
