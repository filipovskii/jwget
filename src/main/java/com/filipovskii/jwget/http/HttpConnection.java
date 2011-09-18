package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.IConnection;
import com.filipovskii.jwget.common.IDownloadRequest;
import com.filipovskii.jwget.common.IDownloadResponse;
import com.filipovskii.jwget.exception.ConnectionFailed;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

public class HttpConnection implements IConnection {
    
    private final String url;
    private HttpURLConnection con;

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

    public IDownloadResponse send(IDownloadRequest req) 
        throws ConnectionFailed {
      try {
        if (req instanceof HttpDownloadRequest) {
          return ((HttpDownloadRequest) req).send(con);
        } else {
          throw new RuntimeException("Incorrect request class");
        }
      } catch (IOException e) {
        throw new ConnectionFailed("Failed to send request");
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
