package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.IConnection;
import com.filipovskii.jwget.common.IDownloadRequest;
import com.filipovskii.jwget.common.IDownloadResponse;
import com.filipovskii.jwget.common.IProtocol;

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
    return new HttpDownloadRequest(downloadData.getProperties());
  }

  @Override
  public IDownloadResponse createResponse() {
    return new HttpDownloadResponse();
  }
}
