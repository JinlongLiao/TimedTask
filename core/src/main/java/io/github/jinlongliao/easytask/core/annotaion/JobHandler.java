package io.github.jinlongliao.easytask.core.annotaion;

import io.github.jinlongliao.easytask.core.job.AbstractJob;

import java.lang.annotation.*;

/**
 * @author liaojinlong
 * @since 2021/9/1 16:26
 */
@Target({ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JobHandler {
  /**
   * 处理的JOB 的类型
   *
   * @return /
   */
  Class<? extends AbstractJob>[] value();
}
