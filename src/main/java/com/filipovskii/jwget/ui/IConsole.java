package com.filipovskii.jwget.ui;

import java.io.Writer;

/**
 * For testing Shell class, it is needed to provide {@link java.io.Console}
 * which is impossible to mock . . . for now.
 * <br />
 * So shell will depend on this interface
 */
public interface IConsole {

  String readLine(String invitation);
  Writer writer();

}
