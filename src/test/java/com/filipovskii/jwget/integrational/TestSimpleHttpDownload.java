package com.filipovskii.jwget.integrational;

import com.filipovskii.jwget.common.IDownloadResult;
import com.filipovskii.jwget.http.HttpDownloadData;
import com.filipovskii.jwget.http.HttpProtocol;
import com.filipovskii.jwget.mgmt.DownloadController;
import com.filipovskii.jwget.downloadresult.DownloadFailed;
import com.filipovskii.jwget.testdata.DownloadProperties;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class TestSimpleHttpDownload {

  @Before
  public void setUp() throws Exception {
    File f = new File(DownloadProperties.PATH);
    if (f.exists()) {
      f.delete();
    }
  }

  @Test
  public void testSimpleHttpDownload() throws Exception {
    DownloadController controller = new DownloadController(
        new HttpProtocol(
            HttpDownloadData.parseFrom(DownloadProperties.PROPERTIES)));
    IDownloadResult res = controller.call();
    if (!res.succeed()) {
      fail(((DownloadFailed) res).getException().getMessage());
    }
  }
}
