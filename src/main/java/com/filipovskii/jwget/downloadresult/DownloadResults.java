package com.filipovskii.jwget.downloadresult;

import com.filipovskii.jwget.common.IDownloadResult;

public final class DownloadResults {

  public static final IDownloadResult SUCCESS = new DownloadSucceed();
  public static final IDownloadResult CANCELED = new DownloadCanceled();

  public static IDownloadResult fail(Exception ex) {
   return new DownloadFailed(ex);
  }
}
