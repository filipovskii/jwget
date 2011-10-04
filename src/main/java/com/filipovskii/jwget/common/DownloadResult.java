package com.filipovskii.jwget.common;

public final class DownloadResult implements IDownloadResult {

  public static final IDownloadResult SUCCESS = new DownloadResult();

  public static IDownloadResult fail(Exception ex) {
   return new DownloadResult(ex);
  }

  private final boolean succeed;
  private final Exception exception;

  private DownloadResult() {
    succeed = true;
    exception = null;
  }

  private DownloadResult(Exception exception) {
    succeed = false;
    this.exception = exception;
  }

  @Override
  public boolean succeed() {
    return succeed;
  }

  @Override
  public Exception getException() {
    return exception;
  }
}
