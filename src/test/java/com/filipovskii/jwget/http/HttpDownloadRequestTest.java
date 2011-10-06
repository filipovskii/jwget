package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.IConnection;
import com.filipovskii.jwget.common.IDownloadRequest;
import com.filipovskii.jwget.common.IDownloadResponse;
import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;

import static org.easymock.EasyMock.*;

public class HttpDownloadRequestTest {

  @Test
  public void testConnectionSendSetsOutputSream() throws Exception {
    IConnection con = new HttpConnection("http://www.java.com");
    IDownloadRequest req = new HttpDownloadRequest(
        createMock(OutputStream.class), Collections.<String, String>emptyMap());
    IDownloadResponse resp = createMock(IDownloadResponse.class);
    resp.setInputStream(anyObject(InputStream.class));
    replay(resp);

    con.open();
    con.send(req, resp);
    con.close();

    verify(resp);
  }
}
