package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.*;
import com.filipovskii.jwget.exception.ConnectionFailed;
import com.google.inject.Provider;

public class DownloadController implements IDownloadController {

  private final Provider<? extends IConnection> connectionProvider;
  private final Provider<? extends IDownloadRequest> requestProvider;
  private final Provider<? extends IDownloadResponse> responseProvider;

  public DownloadController(
      Provider<? extends IConnection> connectionProvider,
      Provider<? extends IDownloadRequest> requestProvider,
      Provider<? extends IDownloadResponse> responseProvider) {

    this.connectionProvider = connectionProvider;
    this.requestProvider = requestProvider;
    this.responseProvider = responseProvider;
  }

  @Override
  public IDownloadResult call() throws Exception {
    IConnection con = connectionProvider.get();

    try {
      con.open();
      con.send(requestProvider.get(), responseProvider.get());
    } catch (ConnectionFailed e) {
      return DownloadResult.fail(e);
    } finally {
      con.close();
    }
    return DownloadResult.SUCCESS;
  }
}
