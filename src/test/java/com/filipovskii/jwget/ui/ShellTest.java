package com.filipovskii.jwget.ui;

import com.filipovskii.jwget.common.IDownloadData;
import com.filipovskii.jwget.common.IDownloadManager;
import com.filipovskii.jwget.common.IDownloadResult;
import com.filipovskii.jwget.http.HttpDownloadData;
import com.filipovskii.jwget.testdata.DownloadProperties;
import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;

import java.io.Writer;
import java.util.Collections;
import java.util.Map;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
/**
 * @author filipovskii_off
 */
public class ShellTest {

  private IConsole console;
  private IDownloadManager manager;

  @Before
  public void setUp() {
    console = createMock(IConsole.class);
    manager = createMock(IDownloadManager.class);
  }

  @Test
  public void testAddCommandParse() throws Exception {
    String command =
        "add " + DownloadProperties.URL + " " + DownloadProperties.PATH;
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


  @Test
  public void testListCommand() throws Exception {
    String command = "list";
    expect(console.readLine(anyObject(String.class))).andReturn(command);
    expectLastCall();
    expectLastCall().andThrow(new IllegalStateException("out!"));
    expect(manager.listDownloads()).andReturn(
        Collections.<IDownloadData, IDownloadResult>emptyMap());

    expect(console.writer()).andReturn(createMock(Writer.class));
    replay(console, manager);

    Shell sh = new Shell(console, manager);
    try {
      sh.commandLoop();
    } catch(IllegalStateException e) {

    }

    verify(console, manager);
  }
}
