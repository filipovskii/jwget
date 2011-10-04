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
  public void testOpenedConnectionCanSendRequests() throws Exception {
    IConnection con = new HttpConnection("http://www.java.com");
    IDownloadRequest req = new HttpDownloadRequest();
    IDownloadResponse resp = createMock(IDownloadResponse.class);
    OutputStream os = createMock(OutputStream.class);

    expect(resp.getOutputStream()).andReturn(os);
    os.write(anyObject(byte[].class));
    expectLastCall().atLeastOnce();
    os.close();
    replay(resp, os);

    con.open();
    con.send(req, resp);
    con.close();

    verify(resp, os);
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
