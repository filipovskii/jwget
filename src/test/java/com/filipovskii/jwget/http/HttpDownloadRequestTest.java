package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.*;
import java.net.HttpURLConnection;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

public class HttpDownloadRequestTest {
    
  @Test
  public void testRequestMakesParamsMapping() throws Exception {
    HttpURLConnection httpCon = createNiceMock(HttpURLConnection.class);
    httpCon.setRequestProperty("Cookie", "aggrr!!");
    expect(httpCon.getInputStream()).andReturn(null);
    replay(httpCon);

    HttpDownloadRequest req = new HttpDownloadRequest();
    req.addParameter("Cookie", "aggrr!!");
    req.send(httpCon);
    
    verify(httpCon);
  }

  @Test
  public void testRequestFillsOrigFileName() throws Exception {
    HttpURLConnection httpCon = createMock(HttpURLConnection.class);
    expect(httpCon.getInputStream()).andReturn(null);
    expect(httpCon.getHeaderFieldKey(0)).andReturn("Content-Disposition");
    expect(httpCon.getHeaderField(0)).andReturn(
      "attachment; filename=someFile");
    expect(httpCon.getHeaderFieldKey(1)).andReturn(null);
    replay(httpCon);
    
    HttpDownloadRequest req = new HttpDownloadRequest();
    HttpDownloadResponse res = req.send(httpCon);
    
    assertEquals("someFile", res.getOriginalFileName());
    verify(httpCon);
  }

  @Test
  public void testConnectionCallsRequestSend() throws Exception {
    HttpConnection con = new HttpConnection("address");
    HttpDownloadRequest req =  createMock(HttpDownloadRequest.class);
    // connection is not opened, so we expect sending null
    expect(req.send(null)).andReturn(null);
    replay(req);

    con.send(req);
    
    verify(req);
    
    
  }
}
