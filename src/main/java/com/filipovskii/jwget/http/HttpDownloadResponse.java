package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.IDownloadResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpDownloadResponse implements IDownloadResponse {

  private static final String CONTENT_DISPOSITION = "Content-Disposition";
  
  private InputStream in;
  private final Map<String, String> headers = new HashMap<String, String>();

  public String getOriginalFileName() {
    return headers.get(CONTENT_DISPOSITION).split("filename=")[1];
  }

  @Override
  public void setInputStream(InputStream in) {
    this.in = in;
  }

  @Override
  public InputStream getInputStream() {
    return in;
  }
}
