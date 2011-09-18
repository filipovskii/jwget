package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.IDownloadRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class HttpDownloadRequest implements IDownloadRequest {
  
  private final Map<String, String> params = new HashMap<String, String>();

  public void addParameter(String name, String value) {
    params.put(name, value);
  }

  public HttpDownloadResponse send(HttpURLConnection con) 
      throws IOException {
    for (Map.Entry<String, String> kv : params.entrySet()) {
      con.setRequestProperty(kv.getKey(), kv.getValue());
    }
    
    HttpDownloadResponse res = 
      new HttpDownloadResponse(con.getInputStream());
    
    int i = 0;
    String header = con.getHeaderFieldKey(0);
    while (header != null) {
      String value = con.getHeaderField(i);
      res.addHeader(header, value);
      header = con.getHeaderFieldKey(++i);
    }
    return res;
  }
  
}
