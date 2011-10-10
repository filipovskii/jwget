package com.filipovskii.jwget.ui;

import java.io.Console;
import java.io.Writer;

/**
 * {@link IConsole} implementation, that forwards
 * all method calls to {@link System#console()}
 *
 * @author filipovskii_off
 */
public final class SystemConsole implements IConsole {

  private static final Console console = System.console();
  private static final SystemConsole INSTANCE = new SystemConsole();

  public static SystemConsole getInstance() {
    return INSTANCE;
  }

  private SystemConsole() {

  }

  @Override
  public String readLine(String invitation) {
    return console.readLine(invitation);
  }

  @Override
  public Writer writer() {
    return console.writer();
  }
}
