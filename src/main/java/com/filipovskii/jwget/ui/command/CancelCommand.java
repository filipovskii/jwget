package com.filipovskii.jwget.ui.command;

import com.filipovskii.jwget.common.IDownloadManager;
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
  public String invoke(Iterable<String> args) {
    int downloadNumber = Integer.parseInt(args.iterator().next());
    manager.cancelDownload(manager.listDownloadData().get(downloadNumber));
    return "ok";
  }
}
