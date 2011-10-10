package com.filipovskii.jwget.ui.command;

import com.filipovskii.jwget.common.IDownloadManager;
import com.filipovskii.jwget.http.HttpDownloadData;
import com.filipovskii.jwget.ui.IConsoleCommand;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author filipovskii_off
 */
public final class AddCommand implements IConsoleCommand {

  private static final String DOWNLOAD_ADDED = "Download added";
  private static final CharMatcher eqSignMatcher = CharMatcher.is('=');
  private final IDownloadManager downloadManager;

  AddCommand(IDownloadManager dm) {
    this.downloadManager = dm;
  }

  @Override
  public String invoke(Iterable<String> args) {
    Map<String, String> properties = new HashMap<String, String>();
    for (String arg : args) {
      if (eqSignMatcher.matchesAnyOf(arg)) {
        Iterator<String> iterator = Splitter
            .on(eqSignMatcher)
            .split(arg).iterator();
        properties.put(iterator.next(), iterator.next());
      } else {
        if (!properties.containsKey(HttpDownloadData.URL_KEY)) {
          properties.put(HttpDownloadData.URL_KEY, arg);
        } else if (!properties.containsKey(HttpDownloadData.PATH_KEY)) {
          properties.put(HttpDownloadData.PATH_KEY, arg);
        } else {
          throw new IllegalStateException(
              "Option [" + arg + "] can't be parsed");
        }
      }
    }
    downloadManager.addDownload(properties);
    return DOWNLOAD_ADDED;
  }
}
