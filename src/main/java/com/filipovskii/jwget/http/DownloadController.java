package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.*;
import com.filipovskii.jwget.exception.ConnectionFailed;

import java.io.InputStream;
import java.io.OutputStream;

public class DownloadController implements IDownloadController {

  private static final int BUFFER_SIZE = 1024;

  private final IProtocol protocol;

  public DownloadController(IProtocol protocol) {
    this.protocol = protocol;
  }

  @Override
  public IDownloadResult call() throws Exception {
    IConnection connection = protocol.createConnection();
    IDownloadRequest req = protocol.createRequest();
    IDownloadResponse resp = protocol.createResponse();

    try {
      connection.open();
      connection.send(req, resp);

    } catch (ConnectionFailed e) {
      return DownloadResult.fail(e);
    }

    InputStream in = resp.getInputStream();
    OutputStream out = req.getOutputStream();

    try {
      byte[] data = new byte[BUFFER_SIZE];
      while (in.read(data) > 0) {
        out.write(data);
      }
    } catch (Exception e) {
      return DownloadResult.fail(e);
    } finally {
      try {
        in.close();
        out.close();
        connection.close();
      } catch (Exception e) {

      }
    }
    return DownloadResult.SUCCESS;
  }
}
