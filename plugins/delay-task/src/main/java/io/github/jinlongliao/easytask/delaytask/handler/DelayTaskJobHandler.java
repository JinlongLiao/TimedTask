package io.github.jinlongliao.easytask.delaytask.handler;

import com.alibaba.fastjson.JSONObject;
import io.github.jinlongliao.easytask.core.annotaion.JobHandler;
import io.github.jinlongliao.easytask.core.constant.JobStatus;
import io.github.jinlongliao.easytask.core.handler.IAnnotationJobHandler;
import io.github.jinlongliao.easytask.delaytask.handler.delay.DelayTaskHandler;
import io.github.jinlongliao.easytask.delaytask.job.DelayTaskHandlerFactory;
import io.github.jinlongliao.easytask.delaytask.job.DelayTaskJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liaojinlong
 * @since 2021/9/9 11:00
 */
@JobHandler({DelayTaskJob.class})
public class DelayTaskJobHandler implements IAnnotationJobHandler<DelayTaskJob> {

  private static final Logger log = LoggerFactory.getLogger(DelayTaskJobHandler.class);
  private static final DelayTaskHandlerFactory TASK_HANDLER_FACTORY = DelayTaskHandlerFactory.getInstance();

  @Override
  public boolean handlerJob(DelayTaskJob job) {
    boolean result = false;
    try {
      final boolean equals = job.getJobStatus().equals(JobStatus.NORMAL);
      if (equals) {
        final String extraData = job.getExtraData();
        final DelayTaskModel taskModel = JSONObject.parseObject(extraData, DelayTaskModel.class);
        final DelayTaskHandler handler = TASK_HANDLER_FACTORY.getDelayTaskHandlerById(taskModel.getTaskId());
        if (handler == null) {
          log.error("not found DelayTaskHandler ：{} ", taskModel.getTaskId());
          return false;
        }
        handler.handle0(taskModel, job);
        result = true;
      }
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
    }
    return result;
  }
}
