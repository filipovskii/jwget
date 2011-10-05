package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.IConnection;
import com.filipovskii.jwget.common.IDownloadRequest;
import com.filipovskii.jwget.common.IDownloadResponse;
import com.filipovskii.jwget.common.IProtocol;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public final class HttpProtocol implements IProtocol {

  private final Map<String, String> properties;

  public HttpProtocol(Map<String, String> properties) {
    this.properties = properties;
  }

  @Override
  public IConnection createConnection() {
    return new HttpConnection(properties.get("url"));
  }

  @Override
  public IDownloadRequest createRequest() {
    OutputStream os = null;
    try {
      File file = new File(properties.get("path"));
      file.createNewFile();
      os = new FileOutputStream(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new HttpDownloadRequest(os, properties);
  }

  @Override
  public IDownloadResponse createResponse() {
    return new HttpDownloadResponse();
  }
}
