package com.filipovskii.jwget.ui;

import com.filipovskii.jwget.common.IDownloadData;
import com.filipovskii.jwget.common.IDownloadManager;
import com.filipovskii.jwget.common.IDownloadResult;
import com.filipovskii.jwget.http.HttpDownloadData;
import com.filipovskii.jwget.testdata.DownloadProperties;
import com.google.common.collect.ImmutableList;
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
    final Capture<Map<String, String>> passedProps =
        new Capture<Map<String, String>>();
    manager.addDownload(capture(passedProps));

    testCommand(command);

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
    expect(manager.listDownloads()).andReturn(
        Collections.<IDownloadData, IDownloadResult>emptyMap());

    testCommand(command);
  }

  @Test
  public void testCancelCommand() throws Exception {
    final IDownloadData data =
        HttpDownloadData.parseFrom(DownloadProperties.PROPERTIES);
    String command = "cancel 0";
    expect(manager.listDownloadData()).andReturn(ImmutableList.of(data));
    manager.cancelDownload(data);

    testCommand(command);
  }

  /**
   * This is a basic method stub for testing shell commands. <br />
   *
   * Executes {@link com.filipovskii.jwget.ui.Shell#commandLoop()} for
   * one time, and makes {@link #console} return <b>command</b> on first
   * {@link IConsole#readLine(String)} call.
   *
   * @param command command to execute
   * @throws Exception
   */
  private void testCommand(String command) throws Exception {

    expect(console.readLine(anyObject(String.class))).andReturn(command);
    expectLastCall();
    expectLastCall().andThrow(new IllegalStateException("out!"));
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
