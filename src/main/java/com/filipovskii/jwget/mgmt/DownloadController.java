package com.filipovskii.jwget.mgmt;

import com.filipovskii.jwget.common.*;
import com.filipovskii.jwget.downloadresult.DownloadCanceled;

import java.io.InputStream;
import java.io.OutputStream;

public final class DownloadController implements IDownloadController {

  private static final int BUFFER_SIZE = 1024;

  private final IProtocol protocol;
  private final ISaver saver;
  private final StatusHolder statusHolder;

  public DownloadController(IProtocol protocol, ISaver saver) {
    this.saver = saver;
    this.protocol = protocol;
    this.statusHolder = new StatusHolder();
    statusHolder.notStarted();
  }

  @Override
  public void run() {
    statusHolder.inProgress();
    IConnection connection = protocol.createConnection();
    IDownloadRequest req = protocol.createRequest();
    IDownloadResponse resp = protocol.createResponse();

    InputStream in = null;
    OutputStream out = null;

    try {
      out = saver.getOutputStream();
      connection.open();
      connection.send(req, resp);

      in = resp.getInputStream();

      byte[] data = new byte[BUFFER_SIZE];
      int read;
      while ((read = in.read(data)) > 0) {
        out.write(data);
        statusHolder.addProgress(read);
        if (isCancelled()) {
          throw new InterruptedException();
        }
      }
      statusHolder.finished();

    } catch (InterruptedException ex) {
      statusHolder.cancelled();
    } catch (Exception e) {
      statusHolder.failed(e);
    } finally {
      try {
        in.close();
        out.close();
      } catch (Exception ignore) {}
      try {
        connection.close();
      } catch (Exception ignore) {}
    }
  }

  @Override
  public IDownloadResult getStatus() {
    return this.statusHolder.getStatus();
  }

  @Override
  public void cancel() {
    this.statusHolder.cancelled();
  }

  /**
   * todo: not thread safe
   * @return
   */
  private boolean isCancelled() {
    return Thread.currentThread().isInterrupted() ||
        (this.statusHolder.getStatus() instanceof DownloadCanceled);
  }
}
