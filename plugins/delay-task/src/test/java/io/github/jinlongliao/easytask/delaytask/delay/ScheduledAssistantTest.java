package io.github.jinlongliao.easytask.delaytask.delay;

import io.github.jinlongliao.easytask.delaytask.task.RunnableTask;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author liaojinlong
 * @since 2021/9/8 20:22
 */
public class ScheduledAssistantTest {
  private ScheduledAssistant scheduledAssistant;
  private final RunnableTask<Void> task = new RunnableTaskTest("task", 2, TimeUnit.SECONDS);
  private final RunnableTask<Void> task2 = new RunnableTaskTest("task2", 2, TimeUnit.SECONDS);

  @Before
  public void init() {
    scheduledAssistant = ScheduledAssistant.getInstance();
  }

  /**
   * 立刻执行
   */
  @Test
  public void runImmediately() throws InterruptedException {
    System.out.println("start timeMillis = " + System.currentTimeMillis());
    scheduledAssistant.runDelayTask(task);
    final ScheduledFuture<?> future = scheduledAssistant.runDelayTask(task2);
    Thread.sleep(50);
    task2.stop();
    future.cancel(true);
    Thread.sleep(5000);
  }
}

class RunnableTaskTest extends RunnableTask<Void> {
  private final String taskName;

  public RunnableTaskTest(String taskName, long initialDelay, TimeUnit unit) {
    super(initialDelay, unit);
    this.taskName = taskName;
  }

  @Override
  public boolean stop() {
    super.stop();
    System.out.println(taskName + " stop timeMillis = " + System.currentTimeMillis());
    return false;
  }

  @Override
  public Void running() {
    System.out.println(taskName + " running timeMillis = " + System.currentTimeMillis());
    return null;
  }
}



