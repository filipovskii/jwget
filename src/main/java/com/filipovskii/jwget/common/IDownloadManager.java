package com.filipovskii.jwget.common;

import java.util.Map;

public interface IDownloadManager {
  void addDownload(Map<String, String> properties);

  IDownloadResult getStatus(IDownloadData downloadData);

  Map<IDownloadData, IDownloadResult> listDownloads();

  void reset();

  void shutdown();
}
