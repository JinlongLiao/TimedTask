package io.github.jinlongliao.easytask.boot;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author liaojinlong
 * @since 2021/9/7 19:54
 */
public class BootTest {
  @Ignore
  @Test
  public void testServer() throws InterruptedException {
    new DefaultBootStart().startServer("0.0.0.0", 8888, null);
    // Thread.sleep(Integer.MAX_VALUE);
  }
}
