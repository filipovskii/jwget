package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.IDownloadRequest;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public final class HttpDownloadRequest implements IDownloadRequest {
  
  private final Map<String, String> params = new HashMap<String, String>();

  public HttpDownloadRequest() {
  }

  public HttpDownloadRequest(Map<String, String> properties) {
    this.params.putAll(properties);
  }

  public Map<String, String> getParameters() {
    return params;
  }
}
