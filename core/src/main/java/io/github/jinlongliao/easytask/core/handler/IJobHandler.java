package io.github.jinlongliao.easytask.core.handler;

import io.github.jinlongliao.easytask.core.job.AbstractJob;

import java.util.Collection;

/**
 * 任务处理器
 *
 * @author liaojinlong
 * @since 2021/9/1 16:24
 */
public interface IJobHandler {
  /**
   * 处理Job
   *
   * @param job
   * @return /
   */
  boolean handlerJob(AbstractJob job);

  /**
   * 返回支持处理的JOB
   *
   * @return /
   */
  Collection<Class<? extends AbstractJob>> supportJob();
}
