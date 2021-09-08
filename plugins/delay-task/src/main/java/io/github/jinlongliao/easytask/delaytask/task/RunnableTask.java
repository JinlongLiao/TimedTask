package io.github.jinlongliao.easytask.delaytask.task;

import java.util.concurrent.*;

/**
 * @author liaojinlong
 * @since 2021/9/8 19:14
 */
public abstract class RunnableTask<V> implements Callable<V> {
  private ScheduledFuture<V> scheduledFuture;
  private boolean running = true;
  private long initialDelay;
  private TimeUnit unit;

  public RunnableTask(long initialDelay, TimeUnit unit) {
    this.initialDelay = initialDelay;
    this.unit = unit;
  }

  public boolean isRunning() {
    return running;
  }

  public long getInitialDelay() {
    return initialDelay;
  }

  public TimeUnit getUnit() {
    return unit;
  }

  @Override
  public V call() throws Exception {
    if (!running) {
      return null;
    }
    return this.running();
  }

  /**
   * 具体运行
   *
   * @return V
   */
  public abstract V running();

  /**
   * 立即停止
   */
  public void stop() {
    this.stop(true);
  }

  public void stop(boolean mayInterruptIfRunning) {
    this.running = false;
    if (scheduledFuture != null) {
      scheduledFuture.cancel(mayInterruptIfRunning);
    }
  }

  public void setRunning(boolean running) {
    this.running = running;
  }

  public ScheduledFuture<V> getScheduledFuture() {
    return scheduledFuture;
  }

  public ScheduledFuture<V> setScheduledFuture(ScheduledFuture<V> scheduledFuture) {
    return this.scheduledFuture = new WrapScheduledFuture<V>(scheduledFuture, this);
  }
}

