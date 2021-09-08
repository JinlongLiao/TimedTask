package io.github.jinlongliao.easytask.delaytask.delay;


import io.github.jinlongliao.easytask.delaytask.task.RunnableTask;
import io.github.jinlongliao.easytask.delaytask.task.ScheduleRunningTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 定时任务助手
 *
 * @author liaojinlong
 * @since 2021/9/8 12:15
 */
public class ScheduledAssistant {
  private static AtomicInteger atomicInteger = new AtomicInteger(0);
  private static final Logger log = LoggerFactory.getLogger(ScheduledAssistant.class);
  private static ScheduledAssistant mInstance;

  public static ScheduledAssistant getInstance() {
    if (mInstance == null) {
      synchronized (ScheduledAssistant.class) {
        if (mInstance == null) {
          mInstance = new ScheduledAssistant();
        }
      }
    }
    return mInstance;
  }

  private ScheduledAssistant() {
  }

  private static final ScheduledThreadPoolExecutor SCHEDULED_THREAD_POOL_EXECUTOR = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
    r -> {
      final Thread thread = new Thread(r);
      thread.setName("ScheduledThreadPool:" + atomicInteger.getAndIncrement());
      return thread;
    },
    (r, executor) -> log.warn("Add Runnable error, throw Runnable :{}", r)
  );



  /**
   * 依据指定的时间 延迟执行
   *
   * @param task
   * @return /
   */
  public ScheduledFuture<?> runDelayTask(RunnableTask task) {
    return task.setScheduledFuture(SCHEDULED_THREAD_POOL_EXECUTOR.schedule(task, task.getInitialDelay(), task.getUnit()));
  }

  /**
   * @throws RejectedExecutionException {@inheritDoc}
   * @throws NullPointerException       {@inheritDoc}
   * @throws IllegalArgumentException   {@inheritDoc}
   */
  public ScheduledFuture<?> scheduleAtFixedRate(ScheduleRunningTask task) {
    return task.setScheduledFuture((ScheduledFuture<Void>) SCHEDULED_THREAD_POOL_EXECUTOR.scheduleWithFixedDelay(task, task.getInitialDelay(), task.getDelay(), task.getUnit()));
  }

  /**
   * @throws RejectedExecutionException {@inheritDoc}
   * @throws NullPointerException       {@inheritDoc}
   * @throws IllegalArgumentException   {@inheritDoc}
   */
  public ScheduledFuture<?> scheduleWithFixedDelay(ScheduleRunningTask task) {
    return task.setScheduledFuture((ScheduledFuture<Void>) SCHEDULED_THREAD_POOL_EXECUTOR.scheduleWithFixedDelay(task, task.getInitialDelay(), task.getDelay(), task.getUnit()));
  }

}
