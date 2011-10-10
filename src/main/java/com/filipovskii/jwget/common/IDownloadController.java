package com.filipovskii.jwget.common;

public interface IDownloadController extends Runnable {

  IDownloadResult getStatus();

  /**
   * Cancel download, if it is in
   * {@link com.filipovskii.jwget.downloadresult.DownloadNotStarted}
   * {@link com.filipovskii.jwget.downloadresult.DownloadInProgress}
   * state
   *
   * @return false if was already finished, else true
   */
  boolean cancel();

}
