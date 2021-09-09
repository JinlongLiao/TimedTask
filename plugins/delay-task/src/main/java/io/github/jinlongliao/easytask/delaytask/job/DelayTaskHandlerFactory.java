package io.github.jinlongliao.easytask.delaytask.job;

import io.github.jinlongliao.easytask.delaytask.handler.delay.DelayTaskHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 针对不同的延迟任务注册工厂
 *
 * @author liaojinlong
 * @since 2021/9/9 11:53
 */
public class DelayTaskHandlerFactory {

  private static final class Tmp {
    static final DelayTaskHandlerFactory INSTANCE = new DelayTaskHandlerFactory();
  }

  public static DelayTaskHandlerFactory getInstance() {
    return Tmp.INSTANCE;
  }

  private DelayTaskHandlerFactory() {
  }

  private static final Map<String, DelayTaskHandler> DELAY_JOB_HANDLER = new ConcurrentHashMap<>(8);

  public boolean addHandler(String taskId, DelayTaskHandler taskHandler) {
    return null == DELAY_JOB_HANDLER.put(taskId, taskHandler);
  }

  public DelayTaskHandler getDelayTaskHandlerById(String taskId) {
    return DELAY_JOB_HANDLER.get(taskId);
  }
}
