package com.filipovskii.jwget.downloadresult;

import com.filipovskii.jwget.common.IDownloadResult;

public final class DownloadFailed implements IDownloadResult {

  private final Exception exception;
  
  DownloadFailed(Exception exception) {
    this.exception = exception;
  }

  @Override
  public boolean succeed() {
    return false;
  }

  public Exception getException() {
    return exception;
  }
}
