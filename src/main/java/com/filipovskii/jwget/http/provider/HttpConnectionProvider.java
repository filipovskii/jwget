package com.filipovskii.jwget.http.provider;

import com.filipovskii.jwget.http.HttpConnection;
import com.google.inject.Provider;

public final class HttpConnectionProvider implements Provider<HttpConnection> {

  private String url;

  @Override
  public HttpConnection get() {
    return new HttpConnection(url);
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
