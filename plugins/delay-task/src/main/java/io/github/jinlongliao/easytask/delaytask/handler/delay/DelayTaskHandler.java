package io.github.jinlongliao.easytask.delaytask.handler.delay;

import io.github.jinlongliao.easytask.delaytask.handler.DelayTaskModel;
import io.github.jinlongliao.easytask.delaytask.job.DelayTaskJob;

/**
 * @author liaojinlong
 * @since 2021/9/9 11:58
 */
public abstract class DelayTaskHandler {
  protected DelayTaskJob taskJob;

  public Object handle0(DelayTaskModel delayTaskModel, DelayTaskJob taskJob) {
    this.taskJob = taskJob;
    return handle(delayTaskModel);
  }

  public abstract Object handle(DelayTaskModel delayTaskModel);

}
