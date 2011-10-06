package com.filipovskii.jwget.common;

public interface IConnection {
  void open() throws ConnectionFailed;
  void send(IDownloadRequest request, IDownloadResponse response)
      throws ConnectionFailed;
  void close();
}
