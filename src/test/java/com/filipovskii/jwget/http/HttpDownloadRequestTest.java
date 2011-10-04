package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.*;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

public class HttpDownloadRequestTest {

  @Test
  public void testSendRequestWritesToOutput() throws Exception {
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
}
