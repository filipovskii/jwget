package com.filipovskii.jwget.mgmt;

import com.filipovskii.jwget.common.IDownloadController;
import com.filipovskii.jwget.common.IDownloadData;
import com.filipovskii.jwget.common.IDownloadResult;
import com.filipovskii.jwget.common.IProtocol;
import com.filipovskii.jwget.http.HttpDownloadData;
import com.filipovskii.jwget.http.HttpProtocol;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class DownloadManager {

  private final ExecutorService executor
      = Executors.newFixedThreadPool(5);
  private final Map<IDownloadData, Future<IDownloadResult>> downloads
      = new HashMap<IDownloadData, Future<IDownloadResult>>();

  public void addDownload(Map<String, String> properties) {
    if (properties.containsKey(HttpDownloadData.URL_KEY) &&
        StringUtils.startsWith(
            "http://", properties.get(HttpDownloadData.URL_KEY))) {

      HttpDownloadData downloadData = HttpDownloadData.parseFrom(properties);
      IProtocol protocol = new HttpProtocol(downloadData);
      IDownloadController controller = new DownloadController(protocol);
      Future<IDownloadResult> future = executor.submit(controller);
      downloads.put(downloadData, future);
    }
  }

}
