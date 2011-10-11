package com.filipovskii.jwget.mgmt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

/**
 * @author filipovskii_off
 */
public final class StatusHolderTest {

  private static final int THREADS_NUMBER = 1000;
  private static final int BYTES_PER_MOVE = 10;
  private static final Log LOG = LogFactory.getLog(StatusHolder.class);

  private final ExecutorService executor =
      Executors.newFixedThreadPool(THREADS_NUMBER);

  @Test
  public void testHolderInitialState() {
    StatusHolder holder = new StatusHolder();

    assertEquals(0, holder.getProgress());
  }

  @Test
  public void testBytesDownloadedTracking() throws Exception {
    StatusHolder holder = new StatusHolder();

    holder.addProgress(BYTES_PER_MOVE);
    assertEquals(BYTES_PER_MOVE, holder.getProgress());
    holder.addProgress(BYTES_PER_MOVE);
    assertEquals(2* BYTES_PER_MOVE, holder.getProgress());
  }
}
