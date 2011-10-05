package com.filipovskii.jwget.http;

import com.filipovskii.jwget.testdata.DownloadProperties;
import org.junit.Test;

import static org.junit.Assert.*;

public class HttpDownloadDataTest {


  @Test
  public void testParseFromProperties() throws Exception {
    HttpDownloadData data =
        HttpDownloadData.parseFrom(DownloadProperties.PROPERTIES);

    assertEquals(DownloadProperties.URL, data.getUrl());
    assertEquals(DownloadProperties.PATH, data.getDownloadPath());
  }
}
