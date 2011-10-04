package com.filipovskii.jwget.http;

import com.filipovskii.jwget.common.IConnection;
import com.filipovskii.jwget.common.IDownloadController;
import com.filipovskii.jwget.common.IDownloadRequest;
import com.filipovskii.jwget.common.IDownloadResponse;
import com.google.inject.Provider;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.*;

import java.util.concurrent.CountDownLatch;

/**
 * @author filipovskii_off
 */
public class DownloadControllerTest {

  final String url = "http://www.java.com";
  final String path = "~/Downloads/java.html";

  private Provider<IConnection> connectionProvider;
  private Provider<IDownloadRequest> requestProvider;
  private Provider<IDownloadResponse> responseProvider;
  private IDownloadController controller;

  @Before
  public void setUp() {
    connectionProvider = createNiceMock(Provider.class);
    requestProvider = createNiceMock(Provider.class);
    responseProvider = createNiceMock(Provider.class);

    controller =
        new DownloadController(
            connectionProvider,
            requestProvider,
            responseProvider);
  }

  @Test
  public void testControllerGetsProvidersData() throws Exception {
    expect(connectionProvider.get()).andReturn(createMock(IConnection.class));
    expect(requestProvider.get()).andReturn(createMock(IDownloadRequest.class));
    expect(responseProvider.get()).andReturn(createMock(IDownloadResponse.class));
    replay(connectionProvider, requestProvider, responseProvider);

    controller.call();

    verify(connectionProvider, requestProvider, responseProvider);
  }
}
