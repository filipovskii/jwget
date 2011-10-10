package com.filipovskii.jwget.mgmt;

import com.filipovskii.jwget.common.*;
import com.filipovskii.jwget.downloadresult.DownloadResults;

import java.io.InputStream;
import java.io.OutputStream;

public final class DownloadController implements IDownloadController {

  private static final int BUFFER_SIZE = 1024;

  private final IProtocol protocol;

  private volatile IDownloadResult status;

  public DownloadController(IProtocol protocol) {
    this.protocol = protocol;
    this.status = DownloadResults.NOT_STARTED;
  }

  @Override
  public void run() {
    this.status = DownloadResults.IN_PROGRESS;

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
        if (Thread.currentThread().isInterrupted()) {
          throw new InterruptedException();
        }
      }

    } catch (InterruptedException ex) {
      this.status = DownloadResults.CANCELED;
    } catch (Exception e) {
      this.status = DownloadResults.fail(e);
    } finally {
      try {
        in.close();
        out.close();
      } catch (Exception ignore) {}
      try {
        connection.close();
      } catch (Exception ignore) {}
    }
    this.status = DownloadResults.SUCCESS;
  }

  @Override
  public IDownloadResult getStatus() {
    return this.status;
  }
}
