package com.filipovskii.jwget.downloadresult;

import com.filipovskii.jwget.common.IDownloadResult;

public final class DownloadResults {

  public static final DownloadSucceed SUCCESS = new DownloadSucceed();
  public static final DownloadCanceled CANCELED = new DownloadCanceled();
  public static final DownloadInProgress IN_PROGRESS = new DownloadInProgress();
  public static final DownloadNotStarted NOT_STARTED = new DownloadNotStarted();

  public static DownloadFailed fail(Exception ex) {
   return new DownloadFailed(ex);
  }
}
