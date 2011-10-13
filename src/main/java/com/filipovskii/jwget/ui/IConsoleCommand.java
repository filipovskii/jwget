package com.filipovskii.jwget.ui;

public interface IConsoleCommand {
  /**
   * Invoke a command.
   *
   * @param args command arguments
   * @param console result will be written here
   */
  void invoke(Iterable<String> args, IConsole console) throws Exception;
}
