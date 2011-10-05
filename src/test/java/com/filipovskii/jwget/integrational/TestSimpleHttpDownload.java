package com.filipovskii.jwget.integrational;

import com.filipovskii.jwget.common.IDownloadResult;
import com.filipovskii.jwget.http.DownloadController;
import com.filipovskii.jwget.http.HttpProtocol;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TestSimpleHttpDownload {

  private static final String url = "http://www.java.com";
  private static final String path = "/home/filipovskii_off/Downloads/java.htm";

  private static final Map<String, String> properties;

  static {
    Map<String, String> props = new HashMap<String, String>();
    props.put("url", url);
    props.put("path", path);
    properties = Collections.unmodifiableMap(props);
  }

  @Before
  public void setUp() throws Exception {
    File f = new File(path);
    if (f.exists()) {
      f.delete();
    }
  }

  @Test
  public void testSimpleHttpDownload() throws Exception {
    DownloadController controller = new DownloadController(
        new HttpProtocol(properties));
    IDownloadResult res = controller.call();
    if (!res.succeed()) {
      res.getException().printStackTrace();
      fail(res.getException().getMessage());
    }
  }
}
