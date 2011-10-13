package com.filipovskii.jwget.ui.command;

import com.filipovskii.jwget.common.IDownloadManager;
import com.filipovskii.jwget.ui.IConsole;
import com.filipovskii.jwget.ui.IConsoleCommand;

/**
 * @author filipovskii_off
 */
final class CancelCommand implements IConsoleCommand {

  private final IDownloadManager manager;

  public CancelCommand(IDownloadManager manager) {
    this.manager = manager;
  }

  @Override
  public void invoke(Iterable<String> args, IConsole console) throws Exception {
    int downloadNumber = Integer.parseInt(args.iterator().next());
    manager.cancelDownload(manager.listDownloadData().get(downloadNumber));
    console.writer().write("ok");
  }
}
