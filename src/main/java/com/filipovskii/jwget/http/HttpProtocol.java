package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.IConnection;
import com.filipovskii.jwget.common.IDownloadRequest;
import com.filipovskii.jwget.common.IDownloadResponse;
import com.filipovskii.jwget.common.IProtocol;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class HttpProtocol implements IProtocol {

  private final HttpDownloadData downloadData;

  public HttpProtocol(HttpDownloadData downloadData) {
    this.downloadData = downloadData;
  }

  @Override
  public IConnection createConnection() {
    return new HttpConnection(downloadData.getUrl());
  }

  @Override
  public IDownloadRequest createRequest() {
    OutputStream os = null;
    try {
      File file = new File(downloadData.getDownloadPath());
      file.createNewFile();
      os = new FileOutputStream(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new HttpDownloadRequest(os, downloadData.getProperties());
  }

  @Override
  public IDownloadResponse createResponse() {
    return new HttpDownloadResponse();
  }
}
