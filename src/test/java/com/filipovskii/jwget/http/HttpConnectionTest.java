package com.filipovskii.jwget.http;

import com.filipovskii.jwget.exception.ConnectionFailed;

import java.io.InputStream;
import java.net.MalformedURLException;

import org.junit.Test;
import static org.junit.Assert.*;

public class HttpConnectionTest {
  
  @Test
  public void testConnectionSucceed() throws Exception {
    HttpConnection con = new HttpConnection("http://www.java.com");
    con.open();
    InputStream in = con.getInputStream();
    byte[] data = new byte[1024];
    in.read(data);

    assertNotNull("InputStream is null", in);
    assertTrue("No data in stream", data.length > 0);
    in.close();
  }
  
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
