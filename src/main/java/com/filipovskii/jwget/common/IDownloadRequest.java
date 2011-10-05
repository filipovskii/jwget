package com.filipovskii.jwget.common;

import java.io.OutputStream;

public interface IDownloadRequest {

  void setOutputStream(OutputStream os);
  OutputStream getOutputStream();
}
