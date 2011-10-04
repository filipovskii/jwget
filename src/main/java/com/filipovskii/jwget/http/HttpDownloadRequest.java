package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.IDownloadRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public final class HttpDownloadRequest implements IDownloadRequest {
  
  private final Map<String, String> params = new HashMap<String, String>();

  public void addParameter(String name, String value) {
    params.put(name, value);
  }


}
