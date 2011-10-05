package com.filipovskii.jwget.common;

import java.io.InputStream;
import java.io.OutputStream;

public interface IDownloadResponse {

  void setInputStream(InputStream in);
  InputStream getInputStream();
}
