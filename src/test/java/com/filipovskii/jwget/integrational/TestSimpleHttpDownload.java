package com.filipovskii.jwget.integrational;

import com.filipovskii.jwget.common.ConnectionFailed;
import com.filipovskii.jwget.common.IDownloadData;
import com.filipovskii.jwget.common.IDownloadResult;
import com.filipovskii.jwget.downloadresult.DownloadFailed;
import com.filipovskii.jwget.downloadresult.DownloadInProgress;
import com.filipovskii.jwget.http.HttpDownloadData;
import com.filipovskii.jwget.http.HttpProtocol;
import com.filipovskii.jwget.mgmt.DownloadController;
import com.filipovskii.jwget.mgmt.DownloadManager;
import com.filipovskii.jwget.testdata.DownloadProperties;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

  @Test
  public void testDownloadFailed() throws Exception {
    Map<String, String> properties = new HashMap<String, String>();
    properties.put(HttpDownloadData.URL_KEY, "http://badurl.bad");
    properties.put(HttpDownloadData.PATH_KEY, DownloadProperties.PATH);

    DownloadManager manager = new DownloadManager();
    manager.addDownload(properties);
    Map<IDownloadData, IDownloadResult> list = manager.listDownloads();
    IDownloadResult res = list.get(list.keySet().toArray()[0]);

    Future<IDownloadResult> future = manager.getFuture(
        (IDownloadData) list.keySet().toArray()[0]);

    DownloadFailed downloadFailed = (DownloadFailed) future.get();
    assertTrue("Wrong exception",
        downloadFailed.getException() instanceof ConnectionFailed);
    assertTrue("Wrong nested exception",
        downloadFailed.getException().getCause() instanceof UnknownHostException);
  }

  @Test
  public void testDownloadInProgress() throws Exception {
    DownloadManager manager = new DownloadManager();
    manager.addDownload(DownloadProperties.PROPERTIES);
    Map<IDownloadData, IDownloadResult> list = manager.listDownloads();

    IDownloadResult res = list.get(list.keySet().toArray()[0]);

    assertTrue("Download should be in progress",
        res instanceof DownloadInProgress);
  }
}
