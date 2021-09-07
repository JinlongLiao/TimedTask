package io.github.jinlongliao.easytask.core.job;


import io.github.jinlongliao.easytask.core.annotaion.JobType;
import io.github.jinlongliao.easytask.core.exception.IllegalUseException;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author liaojinlong
 * @since 2021/9/7 17:06
 */
public abstract class AbstractAnnotationJob extends AbstractJob {
  @Override
  public Collection<String> getJobType() {
    final JobType annotation = this.getClass().getAnnotation(JobType.class);
    if (annotation == null) {
      throw new IllegalUseException("not annotation with  " + JobType.class.getName());
    }
    return Arrays.asList(annotation.value());
  }
}
