package com.filipovskii.jwget.common;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Creates {@link java.io.OutputStream} to save downloaded data
 *
 * @author filipovskii_off
 */
public interface ISaver {

  /**
   * Creates {@link java.io.OutputStream} to save downloaded data
   * @return stream to write data to
   */
  OutputStream getOutputStream() throws IOException;
}
