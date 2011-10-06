package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.IDownloadData;
import com.google.common.base.Objects;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains basic http download data:
 * <ul>
 *   <li><b>url</b>
 *   <li><b>path</b> to downloaded file
 *   <li>request <b>properties</b>
 * </ul>
 */
public final class HttpDownloadData implements IDownloadData {

  public static final String URL_KEY = "url";
  public static final String PATH_KEY = "path";

  public static HttpDownloadData parseFrom(Map<String, String> properties) {
    Map<String, String> propCopy = new HashMap(properties);
    String url = propCopy.get(URL_KEY);
    String path = propCopy.get(PATH_KEY);
    propCopy.remove(URL_KEY);
    propCopy.remove(PATH_KEY);

    return new HttpDownloadData(
        url, path, Collections.unmodifiableMap(propCopy));
  }

  private final String url;
  private final String path;
  private final Map<String, String> properties;

  public HttpDownloadData(
      String url,
      String path,
      Map<String, String> properties) {
    this.url = url;
    this.path = path;
    this.properties = properties;
  }

  public String getUrl() {
    return url;
  }

  public String getDownloadPath() {
    return path;
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  /**
   * Counts hash code from {@link #url} and {@link #path} <br />
   * {@link #properties} doesn't count
   *
   * @return hash code
   */
  @Override
  public int hashCode() {
    return Objects.hashCode(url, path);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof HttpDownloadData) {
      HttpDownloadData that = (HttpDownloadData) obj;
      return Objects.equal(this.url, that.url) &&
          Objects.equal(this.path, that.path);
    } else {
      return false;
    }
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
        .add("url", url)
        .add("download path", path)
        .toString();
  }
}
