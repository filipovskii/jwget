package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.IConnection;
import com.filipovskii.jwget.common.IDownloadRequest;
import com.filipovskii.jwget.common.IDownloadResponse;
import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;

import static org.easymock.EasyMock.*;

public class HttpDownloadRequestTest {

  @Test
  public void testConnectionSendSersOutputSream() throws Exception {
    IConnection con = new HttpConnection("http://www.java.com");
    IDownloadRequest req = new HttpDownloadRequest(
        createMock(OutputStream.class));
    IDownloadResponse resp = createMock(IDownloadResponse.class);
    resp.setInputStream(anyObject(InputStream.class));
    replay(resp);

    con.open();
    con.send(req, resp);
    con.close();

    verify(resp);
  }
}
