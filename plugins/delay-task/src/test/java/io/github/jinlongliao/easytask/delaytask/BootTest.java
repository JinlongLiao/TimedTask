package io.github.jinlongliao.easytask.delaytask;

import com.alibaba.fastjson.JSONObject;
import io.github.jinlongliao.easytask.boot.DefaultBootStart;
import io.github.jinlongliao.easytask.core.constant.JobStatus;
import io.github.jinlongliao.easytask.delaytask.delay.TestDelayTaskHandler;
import io.github.jinlongliao.easytask.delaytask.handler.DelayTaskModel;
import io.github.jinlongliao.easytask.delaytask.job.DelayTask;
import io.github.jinlongliao.easytask.delaytask.job.DelayTaskHandlerFactory;
import io.github.jinlongliao.easytask.delaytask.job.DelayTaskJob;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class BootTest {
  @Test
  public void testJson() {
    final DelayTask delayTask = new DelayTask();
    delayTask.setDelay(10);
    delayTask.setTimeUnit(TimeUnit.SECONDS);
    delayTask.setUrl("https://www.baidu.com");
    final String extra = JSONObject.toJSONString(delayTask);
    final DelayTaskModel delayTaskModel = new DelayTaskModel();
    delayTaskModel.setTaskId("delay");
    delayTaskModel.setExtParam(extra);
    System.out.println("JSON:\n" + extra);
    DelayTaskJob delayTaskJob = new DelayTaskJob();
    delayTaskJob.setTaskId("delay");
    delayTaskJob.setJobStatus(JobStatus.NORMAL);
    delayTaskJob.setGroupKey("delay");
    delayTaskJob.setUnionKey("delay");
    delayTaskJob.setExtraData(JSONObject.toJSONString(delayTaskModel));
    System.out.println("\nJOB:\n" + JSONObject.toJSONString(delayTaskJob));

  }

  @Ignore
  @Test
  public void testServer() throws InterruptedException {
    final DelayTaskHandlerFactory delayTaskHandlerFactory = DelayTaskHandlerFactory.getInstance();
    delayTaskHandlerFactory.addHandler("delay", new TestDelayTaskHandler());
    new DefaultBootStart().startServer("0.0.0.0", 8888, null);
    // Thread.sleep(Integer.MAX_VALUE);
  }
}
