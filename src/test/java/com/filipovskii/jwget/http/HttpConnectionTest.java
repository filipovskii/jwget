package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.IConnection;
import com.filipovskii.jwget.common.IDownloadRequest;
import com.filipovskii.jwget.common.IDownloadResponse;
import com.filipovskii.jwget.exception.ConnectionFailed;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

import org.easymock.EasyMock;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

public class HttpConnectionTest {
  

  @Test
  public void testBadURLException() {
    try {
      HttpConnection con = new HttpConnection("www.java.bad");
      con.open();
      fail();
    } catch (ConnectionFailed e) {
      assertTrue("Inner exception is incorrect: " + e.getMessage(),
        e.getCause() instanceof MalformedURLException);
    }
  }
}
