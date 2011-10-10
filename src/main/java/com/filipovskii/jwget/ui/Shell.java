package com.filipovskii.jwget.ui;

import com.filipovskii.jwget.common.IDownloadManager;
import com.filipovskii.jwget.ui.command.UiCommands;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.inject.internal.util.ImmutableMap;
import com.google.inject.internal.util.ImmutableSet;
import com.google.inject.internal.util.Preconditions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Iterator;

/**
 * Class is responsible for accepting client requests
 * and forwarding them to {@link com.filipovskii.jwget.mgmt.DownloadManager}
 *
 * @author filipovskii_off
 */
public final class Shell {

  private static Log LOG = LogFactory.getLog(Shell.class);
  private static final Splitter.MapSplitter splitter =
      Splitter
          .on(CharMatcher.WHITESPACE)
          .omitEmptyStrings()
          .trimResults()
          .withKeyValueSeparator("=");

  private final IConsole console;
  private final ImmutableMap<String, IConsoleCommand> commandMap;

  public Shell(IConsole console, IDownloadManager manager) {
    this.console = console;
    UiCommands commands = new UiCommands(manager);
    commandMap = ImmutableMap
        .<String, IConsoleCommand>builder()
        .put("add", commands.ADD_DOWNLOAD)
        .put("list", commands.LIST_DOWNLOADS)
        .put("cancel", commands.CANCEL_DOWNLOAD)
        .build();
  }

  public void commandLoop() throws IOException {
    while (true) {
      if (Thread.currentThread().isInterrupted()) {
        return;
      }

      String strCommand = console.readLine("jwget: ");
      LOG.info("Command: [" + strCommand + "]");

      Iterator<String> args =
          Splitter
              .on(CharMatcher.WHITESPACE)
              .split(strCommand)
              .iterator();

      String commandName = args.next();
      IConsoleCommand command = commandMap.get(commandName);
      Preconditions.checkNotNull(
          command, "Command [%s] not found!", commandName);

      // adding all except key word
      ImmutableSet.Builder<String> setBuilder = ImmutableSet.builder();
      while (args.hasNext()) {
        setBuilder.add(args.next());
      }
      String result = command.invoke(setBuilder.build());
      LOG.info("Command result: " + result + '\n');
      console.writer().write(result);
    }
  }
}