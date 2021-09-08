package io.github.jinlongliao.easytask.delaytask.task;

import io.github.jinlongliao.easytask.delaytask.exception.TaskRunningException;

import java.util.concurrent.TimeUnit;


/**
 * @author liaojinlong
 * @since 2021/9/8 19:37
 */
public abstract class ScheduleRunningTask extends RunnableTask<Void> implements Runnable {
  private long delay;

  public ScheduleRunningTask(long initialDelay, TimeUnit unit, long delay) {
    super(initialDelay, unit);
    this.delay = delay;
  }

  public long getDelay() {
    return delay;
  }

  @Override
  public void run() {
    try {
      this.call();
    } catch (Exception e) {
      throw new TaskRunningException(e);
    }
  }
}
