package com.filipovskii.jwget.ui.command;

import com.filipovskii.jwget.common.IDownloadManager;

/**
 * @author filipovskii_off
 */
public final class UiCommands {

  public final AddCommand ADD_DOWNLOAD;
  public final ListCommand LIST_DOWNLOADS;
  public final CancelCommand CANCEL_DOWNLOAD;

  public UiCommands(IDownloadManager manager) {
    ADD_DOWNLOAD = new AddCommand(manager);
    LIST_DOWNLOADS = new ListCommand(manager);
    CANCEL_DOWNLOAD = new CancelCommand(manager);
  }

}
