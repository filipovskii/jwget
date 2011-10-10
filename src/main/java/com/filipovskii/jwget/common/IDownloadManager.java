package com.filipovskii.jwget.common;


import com.google.common.collect.ImmutableList;

import java.util.Map;

public interface IDownloadManager {
  void addDownload(Map<String, String> properties);

  void cancelDownload(IDownloadData downloadData);

  IDownloadResult getStatus(IDownloadData downloadData);

  Map<IDownloadData, IDownloadResult> listDownloads();

  ImmutableList<IDownloadData> listDownloadData();

  void reset();

  void shutdown();

}
