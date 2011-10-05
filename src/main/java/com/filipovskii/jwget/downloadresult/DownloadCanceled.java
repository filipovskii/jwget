package com.filipovskii.jwget.downloadresult;

import com.filipovskii.jwget.common.IDownloadResult;

public final class DownloadCanceled implements IDownloadResult {

  @Override
  public boolean succeed() {
    return false;
  }
}
