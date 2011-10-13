package com.filipovskii.jwget.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Provides {@link FileOutputStream} to
 * {@link com.filipovskii.jwget.common.IDownloadData#getDownloadPath()}
 *
 * @author filipovskii_off
 */
public final class FileSaver implements ISaver {

  private OutputStream os;
  private final IDownloadData downloadData;

  public FileSaver(IDownloadData data) {
    this.downloadData = data;
  }

  @Override
  public OutputStream getOutputStream() throws IOException {
    if (os == null) {
      File file = new File(downloadData.getDownloadPath());
      file.createNewFile();
      os = new FileOutputStream(file);
    }
    return os;
  }
}
