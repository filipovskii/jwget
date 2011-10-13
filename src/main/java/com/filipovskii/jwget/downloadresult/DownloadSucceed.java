package com.filipovskii.jwget.downloadresult;

import com.filipovskii.jwget.common.IDownloadResult;

public final class DownloadSucceed implements IDownloadResult {

  DownloadSucceed() {

  }

  @Override
  public boolean succeed() {
    return true;
  }

  @Override
  public String toString() {
    return StatusNames.SUCCEED.value();
  }
}
