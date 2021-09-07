package io.github.jinlongliao.easytask.boot.loader;

import io.github.jinlongliao.easytask.boot.job.impl.RegisterJob;
import io.github.jinlongliao.easytask.boot.loader.imp.SimpleLocalJobLoader;
import io.github.jinlongliao.easytask.core.job.AbstractJob;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * @author liaojinlong
 * @since 2021/9/7 23:17
 */
public class LocalJobLoaderTest {
  @Test
  public void localLoaderTest() {
    SimpleLocalJobLoader localJobLoader = new SimpleLocalJobLoader();
    Set<AbstractJob> jobs = localJobLoader.loadAllJob();
    Assert.assertNotNull(jobs);
    localJobLoader.addJob(new RegisterJob());
    Assert.assertEquals(jobs.size(), localJobLoader.loadAllJob().size());

  }

  @Test
  public void localLoaderCallBackTest() {
    SimpleLocalJobLoader localJobLoader = new SimpleLocalJobLoader();
    Set<AbstractJob> jobs = localJobLoader.loadAllJob();
    Assert.assertNotNull(jobs);
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      localJobLoader.addJob(new RegisterJob());
      localJobLoader.flush();
    }));

  }
}
