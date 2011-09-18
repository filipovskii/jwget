package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.IDownloadResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpDownloadResponse implements IDownloadResponse {

  private static final String CONTENT_DISPOSITION = "Content-Disposition";
  
  private InputStream in;
  private String originalFileName;
  private Map<String, String> headers = new HashMap<String, String>();

  public HttpDownloadResponse(InputStream in) {
    this.in = in;
  }


  public void addHeader(String key, String value) {
    headers.put(key, value);
  }

  public String getOriginalFileName() {
    return headers.get(CONTENT_DISPOSITION).split("filename=")[1];
  }
 

}
