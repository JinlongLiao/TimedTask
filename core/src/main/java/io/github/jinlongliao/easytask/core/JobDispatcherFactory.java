package io.github.jinlongliao.easytask.core;

import io.github.jinlongliao.easytask.core.exception.NotFountException;
import io.github.jinlongliao.easytask.core.handler.IJobHandler;
import io.github.jinlongliao.easytask.core.job.AbstractJob;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 任务转发器
 *
 * @author liaojinlong
 * @since 2021/9/1 17:03
 */
public class JobDispatcherFactory {

  private Set<IJobHandler> jobHandlers;
  private Map<Class<? extends AbstractJob>, IJobHandler> fastMatchCache;
  private IJobHandler defaultHandle;

  private JobDispatcherFactory() {
    fastMatchCache = new ConcurrentHashMap<>(8);
    jobHandlers = new HashSet<>(8);
  }

  public void refreshJobHandler(Set<IJobHandler> jobHandlers) {
    Map<Class<? extends AbstractJob>, IJobHandler> temp = new HashMap<>(jobHandlers.size() * 2);
    jobHandlers.forEach(item -> item.supportJob().forEach(node -> temp.put(node, item)));
    this.fastMatchCache = temp;
    this.jobHandlers = jobHandlers;
  }

  public void setDefaultHandle(IJobHandler defaultHandle) {
    this.defaultHandle = defaultHandle;
  }

  public boolean addNewJobHandler(IJobHandler jobHandler) {
    final boolean add = jobHandlers.add(jobHandler);
    if (add) {
      jobHandler.supportJob().forEach(item -> fastMatchCache.put(item, jobHandler));
    }
    return add;
  }

  /**
   * 处理任务
   *
   * @param job
   * @return /
   */
  public boolean dispatcherJob(AbstractJob job) {
    final Class<? extends AbstractJob> jobClass = job.getClass();
    final IJobHandler jobHandler = fastMatchCache.getOrDefault(jobClass, defaultHandle);
    if (Objects.isNull(jobHandler)) {
      throw new NotFountException("Job Handler Not Found Match Type" + jobClass.getName());
    }
    return jobHandler.handlerJob(job);
  }

  private static JobDispatcherFactory mInstance;

  public static JobDispatcherFactory getInstance() {
    if (mInstance == null) {
      synchronized (JobDispatcherFactory.class) {
        if (mInstance == null) {
          mInstance = new JobDispatcherFactory();
        }
      }
    }
    return mInstance;
  }


}
