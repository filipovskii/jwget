package com.filipovskii.jwget.mgmt;

import com.filipovskii.jwget.common.IDownloadResult;
import com.filipovskii.jwget.downloadresult.DownloadResults;

/**
 * @author filipovskii_off
 */
public final class StatusHolder {

  private int bytesDownloaded = 0;
  private IDownloadResult status;

  public synchronized void addProgress(int bytesDownloaded) {
    this.bytesDownloaded += bytesDownloaded;
  }

  public synchronized int getProgress() {
    return bytesDownloaded;
  }

  public synchronized IDownloadResult getStatus() {
    return status;
  }

  public void notStarted() {
    setStatus(DownloadResults.NOT_STARTED);
  }

  public void inProgress() {
    setStatus(DownloadResults.IN_PROGRESS);
  }

  public void cancelled() {
    setStatus(DownloadResults.CANCELED);
  }

  public void failed(Exception e) {
    setStatus(DownloadResults.fail(e));
  }

  public void finished() {
    setStatus(DownloadResults.SUCCESS);
  }

  private synchronized void setStatus(IDownloadResult status) {
    this.status = status;
  }

}
