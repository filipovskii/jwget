package com.filipovskii.jwget.mgmt;

import com.filipovskii.jwget.common.*;
import com.filipovskii.jwget.downloadresult.DownloadResults;
import com.filipovskii.jwget.http.HttpDownloadData;
import com.filipovskii.jwget.http.HttpProtocol;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public final class DownloadManager implements IDownloadManager {

  private static final DownloadManager INSTANCE = new DownloadManager();
  private static final int THREADS_NUM = 5;

  public static DownloadManager getInstance() {
    return INSTANCE;
  }

  private ExecutorService executor
      = Executors.newFixedThreadPool(THREADS_NUM);
  private final Map<IDownloadData, Future<IDownloadResult>> downloads
      = new HashMap<IDownloadData, Future<IDownloadResult>>();

  private DownloadManager() {

  }

  @Override
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


  @Override
  public IDownloadResult getStatus(IDownloadData downloadData) {
    Future<IDownloadResult> future = downloads.get(downloadData);
    return getStatus(future);
  }

  @Override
  public Map<IDownloadData, IDownloadResult> listDownloads() {
    Map<IDownloadData, IDownloadResult> resultList =
        new HashMap<IDownloadData, IDownloadResult>();
    for (Map.Entry<IDownloadData, Future<IDownloadResult>> entry :
        downloads.entrySet()) {
      resultList.put(entry.getKey(), getStatus(entry.getValue()));
    }
    return resultList;
  }

  @Override
  public void reset() {
    shutdown();
    executor = Executors.newFixedThreadPool(THREADS_NUM);
    downloads.clear();
  }

  @Override
  public void shutdown() {
    executor.shutdown();
    try {
      executor.awaitTermination(1, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
    }
    executor.shutdownNow();
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
