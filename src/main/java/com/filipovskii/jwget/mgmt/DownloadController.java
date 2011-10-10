package com.filipovskii.jwget.mgmt;

import com.filipovskii.jwget.common.*;
import com.filipovskii.jwget.downloadresult.DownloadCanceled;
import com.filipovskii.jwget.downloadresult.DownloadResults;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReference;

public final class DownloadController implements IDownloadController {

  private static final int BUFFER_SIZE = 1024;

  private final IProtocol protocol;

  private final AtomicReference<IDownloadResult> status;

  public DownloadController(IProtocol protocol) {
    this.protocol = protocol;
    this.status =
        new AtomicReference<IDownloadResult>(DownloadResults.NOT_STARTED);
  }

  @Override
  public void run() {
    if (!status.compareAndSet(
        DownloadResults.NOT_STARTED,
        DownloadResults.IN_PROGRESS)) {
      return;
    }

    IConnection connection = protocol.createConnection();
    IDownloadRequest req = protocol.createRequest();
    IDownloadResponse resp = protocol.createResponse();

    InputStream in = null;
    OutputStream out = null;

    try {
      connection.open();
      connection.send(req, resp);

      in = resp.getInputStream();
      out = req.getOutputStream();

      byte[] data = new byte[BUFFER_SIZE];
      while (in.read(data) > 0) {
        out.write(data);
        if (isCancelled()) {
          throw new InterruptedException();
        }
      }

    } catch (InterruptedException ex) {
      this.status.compareAndSet(
          DownloadResults.IN_PROGRESS,
          DownloadResults.CANCELED);
    } catch (Exception e) {
      this.status.compareAndSet(
          DownloadResults.IN_PROGRESS,
          DownloadResults.fail(e));
    } finally {
      try {
        in.close();
        out.close();
      } catch (Exception ignore) {}
      try {
        connection.close();
      } catch (Exception ignore) {}
    }
    this.status.compareAndSet(
        DownloadResults.IN_PROGRESS,
        DownloadResults.SUCCESS);
  }

  @Override
  public IDownloadResult getStatus() {
    return this.status.get();
  }

  @Override
  public boolean cancel() {
    return status.compareAndSet(
        DownloadResults.IN_PROGRESS,
        DownloadResults.CANCELED) ||
        status.compareAndSet(
            DownloadResults.NOT_STARTED,
            DownloadResults.CANCELED);
  }

  private boolean isCancelled() {
    return Thread.currentThread().isInterrupted() ||
        (this.status.get() instanceof DownloadCanceled);
  }
}
