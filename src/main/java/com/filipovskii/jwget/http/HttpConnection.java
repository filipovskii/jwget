package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.IConnection;
import com.filipovskii.jwget.common.IDownloadRequest;
import com.filipovskii.jwget.common.IDownloadResponse;
import com.filipovskii.jwget.exception.ConnectionFailed;

import javax.inject.Inject;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

public final class HttpConnection implements IConnection {

  private static final int BUFFER_SIZE = 1024;

  private final String url;
  private HttpURLConnection con;

  @Inject
  public HttpConnection(String connectionString) {
    this.url = connectionString;
  }

  public void open() throws ConnectionFailed {
    try {
      URL u = new URL(url);
      con = (HttpURLConnection) u.openConnection();
    } catch (MalformedURLException ex) {
      throw new ConnectionFailed("URL is incorrect", ex);
    } catch (IOException ex) {
      throw new ConnectionFailed("IO problem", ex);
    }

  }

  @Override
  public void send(IDownloadRequest request, IDownloadResponse response)
      throws ConnectionFailed {
    OutputStream out = response.getOutputStream();
    InputStream in = null;
    try {
      in = new BufferedInputStream(con.getInputStream());
      byte[] data = new byte[BUFFER_SIZE];
      while (in.read(data) > 0) {
        out.write(data);
      }
    } catch (Exception e) {
      throw new ConnectionFailed(e);
    } finally {
      try {
        in.close();
        out.close();
      } catch (IOException e) {
      }
    }
  }


  public void close() {
    con.disconnect();
  }

  public InputStream getInputStream() throws ConnectionFailed {
    try {
      return con.getInputStream();
    } catch (IOException e) {
      throw new ConnectionFailed(e);
    }
  }
}
