package io.github.jinlongliao.easytask.core.annotaion;

import io.github.jinlongliao.easytask.core.job.AbstractJob;

import java.lang.annotation.*;

/**
 * @author liaojinlong
 * @see {@link AbstractJob#getJobType()} 用于替代，优先级最高
 * @since 2021/9/7 15:13
 */
@Target({ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JobType {
  String[] value();
}
