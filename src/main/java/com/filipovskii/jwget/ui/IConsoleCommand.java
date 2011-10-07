package com.filipovskii.jwget.ui;

public interface IConsoleCommand {
  /**
   * Invoke a command.
   *
   * @param args command arguments
   * @return output
   */
  String invoke(Iterable<String> args);
}
