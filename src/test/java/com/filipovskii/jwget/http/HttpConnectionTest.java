package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.ConnectionFailed;

import java.net.MalformedURLException;

import org.junit.Test;
import static org.junit.Assert.*;

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
