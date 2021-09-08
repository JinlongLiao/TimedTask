package io.github.jinlongliao.easytask.delaytask.job;

import io.github.jinlongliao.easytask.core.annotaion.JobType;
import io.github.jinlongliao.easytask.core.job.AbstractAnnotationJob;

/**
 * @author liaojinlong
 * @since 2021/9/6 14:09
 */
@JobType({DelayTaskJob.DELAY_JOB})
public class DelayTaskJob extends AbstractAnnotationJob {
  public static final String DELAY_JOB = ("DELAY_JOB");

}
