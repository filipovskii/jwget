package com.filipovskii.jwget.mgmt;

import com.filipovskii.jwget.common.IDownloadController;
import com.filipovskii.jwget.common.IDownloadData;
import com.filipovskii.jwget.common.IDownloadResult;
import com.filipovskii.jwget.common.IProtocol;
import com.filipovskii.jwget.downloadresult.DownloadResults;
import com.filipovskii.jwget.http.HttpDownloadData;
import com.filipovskii.jwget.http.HttpProtocol;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CancellationException;
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
        StringUtils.startsWith(properties.get(HttpDownloadData.URL_KEY),
            "http://")) {

      HttpDownloadData downloadData = HttpDownloadData.parseFrom(properties);
      IProtocol protocol = new HttpProtocol(downloadData);
      IDownloadController controller = new DownloadController(protocol);
      Future<IDownloadResult> future = executor.submit(controller);
      downloads.put(downloadData, future);
    }
  }


  public IDownloadResult getStatus(IDownloadData downloadData) {
    Future<IDownloadResult> future = downloads.get(downloadData);
    return getStatus(future);
  }

  public Map<IDownloadData, IDownloadResult> listDownloads() {
    Map<IDownloadData, IDownloadResult> resultList =
        new HashMap<IDownloadData, IDownloadResult>();
    for (Map.Entry<IDownloadData, Future<IDownloadResult>> entry :
        downloads.entrySet()) {
      resultList.put(entry.getKey(), getStatus(entry.getValue()));
    }
    return resultList;
  }

  // TODO: needed for tests and should be removed/replaced
  public Future<IDownloadResult> getFuture(IDownloadData data) {
    return downloads.get(data);
  }

  private IDownloadResult getStatus(Future<IDownloadResult> future) {
    if (future.isDone()) {
      try {
        return future.get();
      } catch (CancellationException e) {
        return DownloadResults.CANCELED;
      } catch (Exception ex) {
        return DownloadResults.fail(ex);
      }
    } else {
      return DownloadResults.IN_PROGRESS;
    }
  }

}
