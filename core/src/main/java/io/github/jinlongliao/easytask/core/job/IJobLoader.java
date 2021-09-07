package io.github.jinlongliao.easytask.core.job;


import java.util.Set;

/**
 * 加载任务
 *
 * @author liaojinlong
 * @since 2021/9/1 11:52
 */
public interface IJobLoader {
  /**
   * 加载所有的Job
   *
   * @return /
   */
  Set<AbstractJob> loadAllJob();

  /**
   * 新增JOB
   *
   * @param job
   * @return /
   */
  boolean addJob(AbstractJob job);

  /**
   * 移除已有的JOB
   *
   * @param job
   * @return /
   */
  boolean removeJob(AbstractJob job);

  /**
   * 停止正在运行的JOB
   *
   * @param job
   * @return /
   */
  boolean stopJob(AbstractJob job);

  /**
   * 刷新缓存
   */
  void flush();
}
