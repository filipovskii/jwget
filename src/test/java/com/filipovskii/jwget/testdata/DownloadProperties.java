package com.filipovskii.jwget.testdata;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class DownloadProperties {

  public static final String URL = "http://www.java.com";
  public static final String PATH = "/home/filipovskii_off/Downloads/java.htm";

  public static final Map<String, String> PROPERTIES;

  static {
    Map<String, String> props = new HashMap<String, String>();
    props.put("url", URL);
    props.put("path", PATH);
    PROPERTIES = Collections.unmodifiableMap(props);
  }


}
