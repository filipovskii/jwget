package com.filipovskii.jwget;

import com.filipovskii.jwget.ui.Shell;

/**
 * @author filipovskii_off
 */
public final class Main {

  public static final void main(String... args) {
    Shell shell = new Shell(System.console());
    shell.commandLoop();
  }
}
