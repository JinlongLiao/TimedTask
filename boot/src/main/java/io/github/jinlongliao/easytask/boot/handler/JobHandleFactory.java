package io.github.jinlongliao.easytask.boot.handler;

import io.github.jinlongliao.easytask.common.util.ServiceLoaderUtil;
import io.github.jinlongliao.easytask.core.JobDispatcherFactory;
import io.github.jinlongliao.easytask.core.handler.IJobHandler;
import io.github.jinlongliao.easytask.core.job.AbstractJob;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author liaojinlong
 * @since 2021/9/7 17:34
 */
public class JobHandleFactory {
  private static final JobDispatcherFactory JOB_DISPATCHER_FACTORY = JobDispatcherFactory.getInstance();
  private static final List<IJobHandler> JOB_HANDLERS = new ArrayList<>(16);

  public static final ServiceLoaderUtil.LoadProcess<IJobHandler> JOB_LOAD_PROCESS = new ServiceLoaderUtil.LoadProcess<IJobHandler>() {
    @Override
    public List<Class<IJobHandler>> initLoader(Iterator<IJobHandler> iterator) {
      iterator.forEachRemaining(t -> {
          JOB_HANDLERS.add(t);
          JOB_DISPATCHER_FACTORY.addNewJobHandler(t);
        }
      );
      return ServiceLoaderUtil.LoadProcess.super.initLoader(iterator);
    }

    @Override
    public List<Class<IJobHandler>> filter(List<Class<IJobHandler>> collection) {
      return collection;
    }

    @Override
    public boolean beforeAdd(Class<IJobHandler> iJobHandlerClass, List<Class<IJobHandler>> classes, boolean sort) {
      return true;
    }
  };

  private static JobHandleFactory mInstance;

  public static JobHandleFactory getInstance() {
    if (mInstance == null) {
      synchronized (JobHandleFactory.class) {
        if (mInstance == null) {
          mInstance = new JobHandleFactory();
        }
      }
    }
    return mInstance;
  }

  private JobHandleFactory() {
    ServiceLoaderUtil.loadClass(IJobHandler.class, JOB_LOAD_PROCESS);
  }

  public void addNewJobHandler(IJobHandler jobHandler) {
    JOB_DISPATCHER_FACTORY.addNewJobHandler(jobHandler);
  }

  public void dispatcherJob(AbstractJob job) {
    JOB_DISPATCHER_FACTORY.dispatcherJob(job);
  }
}
