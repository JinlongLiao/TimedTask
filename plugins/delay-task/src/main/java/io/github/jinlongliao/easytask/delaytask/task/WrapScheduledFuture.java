package io.github.jinlongliao.easytask.delaytask.task;

import java.util.concurrent.*;

class WrapScheduledFuture<V> implements ScheduledFuture<V> {
  private ScheduledFuture<V> scheduledFuture;
  private RunnableTask runnableTask;

  public WrapScheduledFuture(ScheduledFuture<V> scheduledFuture, RunnableTask runnableTask) {
    this.scheduledFuture = scheduledFuture;
    this.runnableTask = runnableTask;
  }

  @Override
  public long getDelay(TimeUnit unit) {
    return scheduledFuture.getDelay(unit);
  }

  @Override
  public int compareTo(Delayed o) {
    return scheduledFuture.compareTo(o);
  }

  @Override
  public boolean cancel(boolean mayInterruptIfRunning) {
    return runnableTask.stop();
  }

  @Override
  public boolean isCancelled() {
    return scheduledFuture.isCancelled();
  }

  @Override
  public boolean isDone() {
    return scheduledFuture.isDone();
  }

  @Override
  public V get() throws InterruptedException, ExecutionException {
    return scheduledFuture.get();
  }

  @Override
  public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
    return scheduledFuture.get(timeout, unit);
  }

  public boolean cancel0(boolean mayInterruptIfRunning) {
    if (mayInterruptIfRunning) {
      this.runnableTask.setRunning(false);
    }
    return scheduledFuture.cancel(mayInterruptIfRunning);
  }
}
