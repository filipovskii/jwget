package com.filipovskii.jwget.downloadresult;

import com.filipovskii.jwget.common.IDownloadResult;

/**
 * @author filipovskii_off
 */
public final class DownloadNotStarted implements IDownloadResult {

  DownloadNotStarted() {

  }

  @Override
  public boolean succeed() {
    return false;
  }

  @Override
  public String toString() {
    return StatusNames.NOT_STARTED.value();
  }
}
