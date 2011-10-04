package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.IDownloadResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpDownloadResponse implements IDownloadResponse {

  private static final String CONTENT_DISPOSITION = "Content-Disposition";
  
  private final OutputStream out;
  private String originalFileName;
  private final Map<String, String> headers = new HashMap<String, String>();

  public HttpDownloadResponse(OutputStream stream) {
    this.out = stream;
  }

  public String getOriginalFileName() {
    return headers.get(CONTENT_DISPOSITION).split("filename=")[1];
  }


  @Override
  public OutputStream getOutputStream() {
    return out;
  }
}
