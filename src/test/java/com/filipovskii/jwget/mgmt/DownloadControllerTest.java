package com.filipovskii.jwget.mgmt;

import com.filipovskii.jwget.common.*;
import com.filipovskii.jwget.downloadresult.DownloadFailed;
import com.filipovskii.jwget.downloadresult.DownloadResults;
import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CyclicBarrier;
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
  private ISaver saver;

  private InputStream in;
  private OutputStream out;
  private IDownloadRequest req;
  private IDownloadResponse resp;

  @Before
  public void setUp() throws Exception {
    protocol = createNiceMock(IProtocol.class);
    connection = createMock(IConnection.class);
    saver = createMock(ISaver.class);

    in = createNiceMock(InputStream.class);
    out = createNiceMock(OutputStream.class);

    req = createMock(IDownloadRequest.class);
    resp = createMock(IDownloadResponse.class);
    expect(saver.getOutputStream()).andReturn(out);
    expect(resp.getInputStream()).andReturn(in);

    controller = new DownloadController(protocol, saver);

    expect(protocol.createConnection()).andReturn(connection);
    expect(protocol.createRequest()).andReturn(req);
    expect(protocol.createResponse()).andReturn(resp);
  }

  @Test
  public void testControllerGetsProtocolData() throws Exception {
    // expectations are in setUp

    replay(protocol, req, resp);

    controller.run();

    verify(protocol);
  }

  @Test
  public void testConnectionOpensAndCloses() throws Exception {
    connection.open();
    connection.send(
        anyObject(IDownloadRequest.class), anyObject(IDownloadResponse.class));
    connection.close();
    replay(connection, protocol, req, resp, saver);

    controller.run();

    verify(connection);
  }

  @Test
  public void testConnectionClosesIfExceptionIsThrown() throws Exception {
    connection.open();
    expectLastCall().andThrow(new ConnectionFailed("bla bla"));
    connection.close();
    replay(connection, protocol, req, resp, saver);

    controller.run();

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
    replay(protocol, req, resp, in, out, saver);

    // act
    controller.run();

    verify(in, out);
  }

  @Test
  public void testCancellation() throws Exception {
    final CyclicBarrier barrier = new CyclicBarrier(2);
    expect(in.read((byte[]) anyObject())).andReturn(1);
    out.write((byte[]) anyObject());
    expectLastCall().andAnswer(new IAnswer<Void>() {
      @Override
      public Void answer() throws Throwable {
        barrier.await();
        return null;
      }
    });
    expect(in.read((byte[]) anyObject())).andReturn(1).anyTimes();
    out.write((byte[]) anyObject());
    expectLastCall().anyTimes();

    replay(protocol, req, resp, in, out, saver);
    ExecutorService exec = Executors.newFixedThreadPool(1);
    Future<?> future = exec.submit(controller);

    assertFalse(future.isDone());
    barrier.await();
    controller.cancel();
    future.get();
    assertTrue(future.isDone());
    assertEquals(DownloadResults.CANCELED, controller.getStatus());
  }


  @Test
  public void testStatusNotStarted() throws Exception {
    reset(protocol);
    ExecutorService ex = Executors.newSingleThreadExecutor();

    final CyclicBarrier barrier = new CyclicBarrier(2, new Runnable() {
      @Override
      public void run() {
        assertEquals(DownloadResults.IN_PROGRESS, controller.getStatus());
      }
    });

    expect(protocol.createConnection()).andAnswer(new IAnswer<IConnection>() {
      @Override
      public IConnection answer() throws Throwable {
        barrier.await();
        throw new InterruptedException("stop test");
      }
    });

    replay(protocol);

    assertEquals(DownloadResults.NOT_STARTED, controller.getStatus());
    ex.submit(controller);
    barrier.await();
  }

  @Test
  public void testDownloadFailesOnSaverException() throws Exception {
    IOException ex = new IOException("i.e. file not found");
    reset(saver);
    expect(saver.getOutputStream()).andThrow(ex);

    replay(protocol, req, resp, in, out, saver);
    controller.run();
    assertTrue(controller.getStatus() instanceof DownloadFailed);
    assertSame(ex,
        ((DownloadFailed) controller.getStatus()).getException());
  }

}
