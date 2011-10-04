package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.IConnection;
import com.filipovskii.jwget.common.IDownloadController;
import com.filipovskii.jwget.common.IDownloadRequest;
import com.filipovskii.jwget.common.IDownloadResponse;
import com.filipovskii.jwget.exception.ConnectionFailed;
import com.google.inject.Provider;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.*;

/**
 * @author filipovskii_off
 */
public class DownloadControllerTest {

  final String url = "http://www.java.com";
  final String path = "~/Downloads/java.html";

  private Provider<IDownloadRequest> requestFactory;
  private Provider<IDownloadResponse> responseFactory;
  private IConnection connection;
  private IDownloadController controller;

  @Before
  public void setUp() {
    requestFactory = createNiceMock(Provider.class);
    responseFactory = createNiceMock(Provider.class);
    connection = createMock(IConnection.class);

    controller =
        new DownloadController(
            connection,
            requestFactory,
            responseFactory);
  }

  @Test
  public void testControllerGetsProvidersData() throws Exception {
    expect(requestFactory.get()).andReturn(createMock(IDownloadRequest.class));
    expect(responseFactory.get()).andReturn(createMock(IDownloadResponse.class));
    replay(requestFactory, responseFactory);

    controller.call();

    verify(requestFactory, responseFactory);
  }

  @Test
  public void testConnectionOpensAndCloses() throws Exception {
    connection.open();
    connection.send(
        anyObject(IDownloadRequest.class), anyObject(IDownloadResponse.class));
    connection.close();
    replay(connection);

    controller.call();

    verify(connection);
  }

  @Test
  public void testConnectionClosesIfExceptionIsThrown() throws Exception {
    connection.open();
    expectLastCall().andThrow(new ConnectionFailed("bla bla"));
    connection.close();
    replay(connection);

    controller.call();

    verify(connection);
  }

}
