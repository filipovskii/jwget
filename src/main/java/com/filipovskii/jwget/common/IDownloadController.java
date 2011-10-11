package com.filipovskii.jwget.common;

public interface IDownloadController extends Runnable {

  IDownloadResult getStatus();

  void cancel();

}
