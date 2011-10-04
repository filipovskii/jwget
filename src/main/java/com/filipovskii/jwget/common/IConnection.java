package com.filipovskii.jwget.common;

import com.filipovskii.jwget.exception.ConnectionFailed;
import java.io.InputStream;

public interface IConnection {
  void open() throws ConnectionFailed;
  void send(IDownloadRequest request, IDownloadResponse response)
      throws ConnectionFailed;
  void close();
}
