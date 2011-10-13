package com.filipovskii.jwget.mgmt;

import com.filipovskii.jwget.common.*;
import com.filipovskii.jwget.http.HttpDownloadData;
import com.filipovskii.jwget.http.HttpProtocol;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public final class DownloadManager implements IDownloadManager {

  private static final DownloadManager INSTANCE = new DownloadManager();
  private static final int THREADS_NUM = 5;

  public static DownloadManager getInstance() {
    return INSTANCE;
  }

  private ExecutorService executor
      = Executors.newFixedThreadPool(THREADS_NUM);
  private final Map<IDownloadData, IDownloadController> controllerList
      = new HashMap<IDownloadData, IDownloadController>();

  private DownloadManager() {

  }

  @Override
  public void addDownload(Map<String, String> properties) {
    if (properties.containsKey(HttpDownloadData.URL_KEY) &&
        StringUtils.startsWith(properties.get(HttpDownloadData.URL_KEY),
            "http://")) {

      HttpDownloadData downloadData = HttpDownloadData.parseFrom(properties);
      IDownloadController controller = new DownloadController(
          new HttpProtocol(downloadData), new FileSaver(downloadData));
      Future<?> future = executor.submit(controller);
      controllerList.put(downloadData, controller);
    }
  }

  @Override
  public void cancelDownload(IDownloadData downloadData) {
    controllerList.get(downloadData).cancel();
  }


  @Override
  public IDownloadResult getStatus(IDownloadData downloadData) {
    return controllerList.get(downloadData).getStatus();
  }

  @Override
  public Map<IDownloadData, IDownloadResult> listDownloads() {
    Map<IDownloadData, IDownloadResult> resultList =
        new HashMap<IDownloadData, IDownloadResult>();
    for (Map.Entry<IDownloadData,IDownloadController> entry :
        controllerList.entrySet()) {
      resultList.put(entry.getKey(), entry.getValue().getStatus());
    }
    return resultList;
  }

  @Override
  public ImmutableList<IDownloadData> listDownloadData() {
    return ImmutableList.copyOf(controllerList.keySet());
  }

  @Override
  public void reset() {
    shutdown();
    executor = Executors.newFixedThreadPool(THREADS_NUM);
    controllerList.clear();
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


}
