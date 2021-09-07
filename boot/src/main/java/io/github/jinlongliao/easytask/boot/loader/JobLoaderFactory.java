package io.github.jinlongliao.easytask.boot.loader;

import io.github.jinlongliao.easytask.common.util.ServiceLoaderUtil;
import io.github.jinlongliao.easytask.core.job.IJobLoader;

import java.util.*;

/**
 * 加载Job 的 Loader 工厂类
 *
 * @author liaojinlong
 * @since 2021/9/7 23:41
 */
public class JobLoaderFactory {
  private static JobLoaderFactory mInstance;
  private static Set<IJobLoader> JOB_LOADER_SET;


  public static JobLoaderFactory getInstance() {
    if (mInstance == null) {
      synchronized (JobLoaderFactory.class) {
        if (mInstance == null) {
          mInstance = new JobLoaderFactory();
        }
      }
    }
    return mInstance;
  }

  private JobLoaderFactory() {
    ServiceLoaderUtil.loadClass(IJobLoader.class, new ServiceLoaderUtil.LoadProcess<IJobLoader>() {
      @Override
      public List<Class<IJobLoader>> initLoader(Iterator<IJobLoader> iterator) {
        JOB_LOADER_SET = new HashSet<>(8);
        iterator.forEachRemaining(t -> JOB_LOADER_SET.add(t));
        return ServiceLoaderUtil.LoadProcess.super.initLoader(iterator);
      }
    });
  }

  /**
   * 新增JOB
   *
   * @param jobLoader
   * @return /
   */
  public boolean addJobLoader(IJobLoader jobLoader) {
    return JOB_LOADER_SET.add(jobLoader);
  }

  /**
   * 刷新JOB
   *
   * @return /
   */
  public Collection<IJobLoader> getAllJobLoader() {
    return Collections.unmodifiableCollection(JOB_LOADER_SET);
  }
}
