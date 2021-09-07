package io.github.jinlongliao.easytask.boot.job;

import io.github.jinlongliao.commons.mapstruct.core.Proxy;
import io.github.jinlongliao.easytask.boot.job.converter.DataTypeConverter;
import io.github.jinlongliao.easytask.common.util.ServiceLoaderUtil;
import io.github.jinlongliao.easytask.core.annotaion.JobType;
import io.github.jinlongliao.easytask.core.job.AbstractJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liaojinlong
 * @since 2021/9/7 9:59
 */
public class DefaultJobLoadProcess implements ServiceLoaderUtil.LoadProcess<AbstractJob> {
  private static final Logger log = LoggerFactory.getLogger(DefaultJobLoadProcess.class);
  private static Map<Type, String> VALUE_CONVERTER;

  static {
    final Proxy proxy = new Proxy();
    try {
      final Field field = Proxy.class.getDeclaredField("VALUE_CONVERTER");
      field.setAccessible(true);
      VALUE_CONVERTER = (Map<Type, String>) field.get(proxy);
      final Class<DataTypeConverter> dataTypeConverter = DataTypeConverter.class;
      final String className = dataTypeConverter.getName();
      final Method[] methods = dataTypeConverter.getDeclaredMethods();
      for (Method method : methods) {
        final Class<?> type = method.getReturnType();
        final String methodName = method.getName();
        VALUE_CONVERTER.put(type, className + "." + methodName);
      }
    } catch (ReflectiveOperationException e) {
      log.error(e.getLocalizedMessage(), e);
    }
  }

  private Map<String, Class<? extends AbstractJob>> JOB_CLASS_CACHE = new ConcurrentHashMap<>(8);

  @Override
  public List<Class<AbstractJob>> initLoader(Iterator<AbstractJob> iterator) {
    return ServiceLoaderUtil.LoadProcess.super.initLoader(iterator);
  }

  @Override
  public List<Class<AbstractJob>> filter(List<Class<AbstractJob>> collection) {
    final List<Class<AbstractJob>> classes = ServiceLoaderUtil.LoadProcess.super.filter(collection);
    classes.stream().forEach(item -> register(item));
    return classes;
  }

  @Override
  public boolean beforeAdd(Class<AbstractJob> tClass, List<Class<AbstractJob>> classList, boolean sort) {
    classList.stream().forEach(item -> register(item));
    return ServiceLoaderUtil.LoadProcess.super.beforeAdd(tClass, classList, sort);
  }

  /**
   * 优先使用注解；
   *
   * @param jobClass
   */
  private void register(Class<AbstractJob> jobClass) {
    final JobType jobType = jobClass.getAnnotation(JobType.class);
    if (jobType != null) {
      Arrays.asList(jobType.value()).stream().forEach(key -> JOB_CLASS_CACHE.put(key, jobClass));
      return;
    }

    final Constructor<?>[] declaredConstructors = jobClass.getDeclaredConstructors();
    for (Constructor<?> constructor : declaredConstructors) {
      try {
        final AbstractJob job = (AbstractJob) constructor.newInstance();
        job.getJobType().forEach(key -> JOB_CLASS_CACHE.put(key, jobClass));
        return;
      } catch (Exception e) {
      }
    }
    log.warn("注册失败 {}", jobClass.getName());
  }

  /**
   * 依据MsgType 获取Class
   *
   * @param msgType
   * @return /
   */
  public Class<? extends AbstractJob> getJobClassByType(String msgType) {
    return JOB_CLASS_CACHE.get(msgType);
  }
}
