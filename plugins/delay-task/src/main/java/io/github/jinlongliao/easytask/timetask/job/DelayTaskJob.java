package io.github.jinlongliao.easytask.timetask.job;

import io.github.jinlongliao.easytask.core.job.Job;

/**
 * @author liaojinlong
 * @since 2021/9/6 14:09
 */
public class DelayTaskJob extends Job {
  private static final String DELAY_JOB = "DELAY_JOB";

  @Override
  public String getJobType() {
    return DELAY_JOB;
  }
}
