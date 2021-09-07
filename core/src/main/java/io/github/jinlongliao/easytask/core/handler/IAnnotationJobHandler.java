package io.github.jinlongliao.easytask.core.handler;

import io.github.jinlongliao.easytask.core.annotaion.JobHandler;
import io.github.jinlongliao.easytask.core.exception.IllegalUseException;
import io.github.jinlongliao.easytask.core.job.AbstractJob;

import java.util.Arrays;
import java.util.Collection;

/**
 * 基于注解的业务处理
 *
 * @author liaojinlong
 * @since 2021/9/1 16:31
 */
public interface IAnnotationJobHandler extends IJobHandler {
  /**
   * 此处理器支持的所有JOB
   *
   * @return /
   */
  @Override
  default Collection<Class<? extends AbstractJob>> supportJob() {

    final JobHandler annotation = this.getClass().getAnnotation(JobHandler.class);
    if (annotation == null) {
      throw new IllegalUseException("not annotation with  " + JobHandler.class.getName());
    }
    return Arrays.asList(annotation.value());
  }
}
