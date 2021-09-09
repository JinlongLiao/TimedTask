package io.github.jinlongliao.easytask.boot.handler.imp;

import io.github.jinlongliao.easytask.boot.job.impl.RegisterJob;
import io.github.jinlongliao.easytask.core.annotaion.JobHandler;
import io.github.jinlongliao.easytask.core.handler.IAnnotationJobHandler;
import io.github.jinlongliao.easytask.core.job.AbstractJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author liaojinlong
 * @since 2021/9/7 17:38
 */
@JobHandler(RegisterJob.class)
public class RegisterJobHandler implements IAnnotationJobHandler<RegisterJob> {
  private static final Logger log = LoggerFactory.getLogger(RegisterJobHandler.class);

  @Override
  public boolean handlerJob(RegisterJob job) {
    log.info("RegisterJob:{}", job);
    return false;
  }
}
