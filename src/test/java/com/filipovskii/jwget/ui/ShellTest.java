package com.filipovskii.jwget.ui;

import com.filipovskii.jwget.common.IDownloadManager;
import com.filipovskii.jwget.http.HttpDownloadData;
import com.filipovskii.jwget.testdata.DownloadProperties;
import org.easymock.Capture;
import org.junit.Test;

import java.io.Writer;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
/**
 * @author filipovskii_off
 */
public class ShellTest {

  private ExecutorService executor = Executors.newSingleThreadExecutor();
  private CountDownLatch latch = new CountDownLatch(1);

  @Test
  public void testAddCommandParse() throws Exception {
    String command =
        "add " + DownloadProperties.URL + " " + DownloadProperties.PATH;

    IConsole console = createMock(IConsole.class);
    IDownloadManager manager = createMock(IDownloadManager.class);
    Capture<Map<String, String>> passedProps =
        new Capture<Map<String, String>>();

    expect(console.readLine(anyObject(String.class)))
        .andReturn(command);
    expect(console.readLine(anyObject(String.class)))
        .andThrow(new IllegalStateException("Get out from loop"));
    manager.addDownload(capture(passedProps));
    expect(console.writer()).andReturn(createMock(Writer.class));

    replay(console, manager);

    final Shell shell = new Shell(console, manager);
    try {
      shell.commandLoop();
    } catch (IllegalStateException loopFinished) {

    }

    assertEquals(
        DownloadProperties.URL,
        passedProps.getValue().get(HttpDownloadData.URL_KEY));
    assertEquals(
        DownloadProperties.PATH,
        passedProps.getValue().get(HttpDownloadData.PATH_KEY));

    verify(console, manager);
  }
}
