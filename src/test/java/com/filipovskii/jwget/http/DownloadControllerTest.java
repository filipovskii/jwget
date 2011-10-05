package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.*;
import com.filipovskii.jwget.downloadresult.DownloadResults;
import com.filipovskii.jwget.exception.ConnectionFailed;
import com.filipovskii.jwget.mgmt.DownloadController;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * @author filipovskii_off
 */
public class DownloadControllerTest {

  private IProtocol protocol;
  private IConnection connection;
  private IDownloadController controller;

  private InputStream in;
  private OutputStream out;
  private IDownloadRequest req;
  private IDownloadResponse resp;

  @Before
  public void setUp() {
    protocol = createNiceMock(IProtocol.class);
    connection = createMock(IConnection.class);

    in = createMock(InputStream.class);
    out = createMock(OutputStream.class);

    req = createMock(IDownloadRequest.class);
    resp = createMock(IDownloadResponse.class);
    expect(req.getOutputStream()).andReturn(out);
    expect(resp.getInputStream()).andReturn(in);

    controller = new DownloadController(protocol);

    expect(protocol.createConnection()).andReturn(connection);
    expect(protocol.createRequest()).andReturn(req);
    expect(protocol.createResponse()).andReturn(resp);
  }

  @Test
  public void testControllerGetsProtocolData() throws Exception {
    // expectations are in setUp

    replay(protocol, req, resp);

    controller.call();

    verify(protocol);
  }

  @Test
  public void testConnectionOpensAndCloses() throws Exception {
    connection.open();
    connection.send(
        anyObject(IDownloadRequest.class), anyObject(IDownloadResponse.class));
    connection.close();
    replay(connection, protocol, req, resp);

    controller.call();

    verify(connection);
  }

  @Test
  public void testConnectionClosesIfExceptionIsThrown() throws Exception {
    connection.open();
    expectLastCall().andThrow(new ConnectionFailed("bla bla"));
    connection.close();
    replay(connection, protocol, req, resp);

    controller.call();

    verify(connection);
  }

  @Test
  public void testControllerWritesDataToOutputStream() throws Exception {
    expect(in.read((byte[]) anyObject())).andReturn(1).times(2);
    expect(in.read((byte[]) anyObject())).andReturn(-1);

    out.write((byte[]) anyObject());
    expectLastCall().times(2);
    in.close();
    out.close();
    replay(protocol, req, resp, in, out);

    // act
    controller.call();

    verify(in, out);
  }

  @Test
  public void testInterruption() throws Exception {
    expect(in.read((byte[]) anyObject())).andReturn(1).times(Integer.MAX_VALUE);
    replay(protocol, req, resp, in);
    ExecutorService exec = Executors.newFixedThreadPool(1);
    Future<IDownloadResult> future = exec.submit(controller);
    future.cancel(true);
    assertEquals(DownloadResults.CANCELED, future.get());
  }

}
