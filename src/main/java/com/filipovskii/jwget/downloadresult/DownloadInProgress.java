package com.filipovskii.jwget.downloadresult;

import com.filipovskii.jwget.common.IDownloadResult;

public final class DownloadInProgress implements IDownloadResult {

  DownloadInProgress() {

  }

  @Override
  public boolean succeed() {
    return false;
  }

  @Override
  public String toString() {
    return StatusNames.IN_PROGRESS.value();
  }
}
