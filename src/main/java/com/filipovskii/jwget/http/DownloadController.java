package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.*;
import com.filipovskii.jwget.exception.ConnectionFailed;
import com.google.inject.Provider;

import javax.inject.Inject;

public class DownloadController implements IDownloadController {

  private final IConnection connection;
  private final Provider<? extends IDownloadRequest> requestProvider;
  private final Provider<? extends IDownloadResponse> responseProvider;

  @Inject
  public DownloadController(
      IConnection connection,
      Provider<? extends IDownloadRequest> requestProvider,
      Provider<? extends IDownloadResponse> responseProvider) {

    this.connection = connection;
    this.requestProvider = requestProvider;
    this.responseProvider = responseProvider;
  }

  @Override
  public IDownloadResult call() throws Exception {
    try {
      connection.open();
      connection.send(requestProvider.get(), responseProvider.get());
    } catch (ConnectionFailed e) {
      return DownloadResult.fail(e);
    } finally {
      connection.close();
    }
    return DownloadResult.SUCCESS;
  }
}
