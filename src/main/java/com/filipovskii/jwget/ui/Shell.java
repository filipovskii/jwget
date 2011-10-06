package com.filipovskii.jwget.ui;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.io.Console;
import java.util.Map;

/**
 * Class is responsible for accepting client requests
 * and forwarding them to {@link com.filipovskii.jwget.mgmt.DownloadManager}
 *
 * @author filipovskii_off
 */
public final class Shell {

  private static final Splitter.MapSplitter splitter =
      Splitter
          .on(CharMatcher.WHITESPACE)
          .omitEmptyStrings()
          .trimResults()
          .withKeyValueSeparator("=");

  private final Console console;

  public Shell(Console console) {
    this.console = console;
  }

  public void commandLoop() {
    while (true) {
      String command = console.readLine("jwget: ");
      Map<String, String> map = splitter.split(command);
      for (Map.Entry<String, String> entry : map.entrySet()) {
        console.writer().write(entry.getKey() + " = " + entry.getValue() + "\n");
      }
    }
  }
}
