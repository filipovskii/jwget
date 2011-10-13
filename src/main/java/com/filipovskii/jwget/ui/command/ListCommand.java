package com.filipovskii.jwget.ui.command;

import com.filipovskii.jwget.common.IDownloadData;
import com.filipovskii.jwget.common.IDownloadManager;
import com.filipovskii.jwget.common.IDownloadResult;
import com.filipovskii.jwget.ui.IConsole;
import com.filipovskii.jwget.ui.IConsoleCommand;
import com.google.common.base.Joiner;

import java.util.Map;

/**
 * @author filipovskii_off
 */
public final class ListCommand implements IConsoleCommand {

  private final String NO_DOWNLOADS = "No downloads";
  private final IDownloadManager manager;

  ListCommand(IDownloadManager dm) {
    this.manager = dm;
  }

  @Override
  public void invoke(Iterable<String> args, IConsole console) throws Exception {
    StringBuilder builder = new StringBuilder();
    Map<IDownloadData, IDownloadResult> map = manager.listDownloads();
    if (map.isEmpty()) {
      builder.append(NO_DOWNLOADS);
    }
    int i = 0;
    for (Map.Entry<IDownloadData, IDownloadResult> e : map.entrySet()) {
      builder.append(i++);
      builder.append(" -> ");
      builder.append(Joiner.on(" -> ").join(e.getKey(), e.getValue()));
      builder.append(";");
    }
    console.writer().write(builder.toString());
  }
}
