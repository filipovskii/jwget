package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.ConnectionFailed;
import com.filipovskii.jwget.common.IConnection;
import com.filipovskii.jwget.common.IDownloadRequest;
import com.filipovskii.jwget.common.IDownloadResponse;

import javax.inject.Inject;
import java.io.InputStream;
import java.io.IOException;
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
    try {
      response.setInputStream(con.getInputStream());
    } catch (Exception e) {
      throw new ConnectionFailed(e);
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
