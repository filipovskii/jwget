package com.filipovskii.jwget.common;

import java.util.Map;

/**
 * Abstract factory
 */
public interface IProtocol {

  /**
   * Set download properties
   * @param properties download properties
   */
  void setProperties(Map<String, String> properties);

  /**
   * Create protocol specific connection
   * @return created connection
   *
   * @see com.filipovskii.jwget.http.HttpConnection
   */
  IConnection createConnection();

  /**
   * Create protocol specific request and bind properties to it
   * @return created request
   *
   * @see com.filipovskii.jwget.http.HttpDownloadRequest
   */
  IDownloadRequest createRequest();

  /**
   * Create protocol specific response
   *
   * @return protocol specific response
   *
   * @see com.filipovskii.jwget.http.HttpDownloadResponse
   */
  IDownloadResponse createResponse();
}
