package com.filipovskii.jwget;

import com.filipovskii.jwget.mgmt.DownloadManager;
import com.filipovskii.jwget.ui.Shell;
import com.filipovskii.jwget.ui.SystemConsole;

import java.io.IOException;

/**
 * @author filipovskii_off
 */
public final class Main {

  public static final void main(String... args) throws IOException {
    Shell shell = new Shell(SystemConsole.getInstance(), DownloadManager.getInstance());
    shell.commandLoop();
  }
}
