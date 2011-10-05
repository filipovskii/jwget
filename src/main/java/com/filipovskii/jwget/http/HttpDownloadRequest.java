package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.IDownloadRequest;

import java.io.OutputStream;
import java.util.Map;

public final class HttpDownloadRequest implements IDownloadRequest {
  
  private Map<String, String> params;
  private final OutputStream os;

  public HttpDownloadRequest(OutputStream os) {
    this.os = os;
  }

  public HttpDownloadRequest(OutputStream os, Map<String, String> properties) {
    this.os = os;
    this.params = properties;
  }

  @Override
  public OutputStream getOutputStream() {
    return this.os;
  }
}
