package io.github.jinlongliao.easytask.delaytask;

import com.alibaba.fastjson.JSONObject;
import io.github.jinlongliao.easytask.boot.DefaultBootStart;
import io.github.jinlongliao.easytask.common.http.client.HttpClient;
import io.github.jinlongliao.easytask.core.constant.JobStatus;
import io.github.jinlongliao.easytask.delaytask.delay.TestDelayTaskHandler;
import io.github.jinlongliao.easytask.delaytask.handler.DelayTaskModel;
import io.github.jinlongliao.easytask.delaytask.job.DelayTask;
import io.github.jinlongliao.easytask.delaytask.job.DelayTaskHandlerFactory;
import io.github.jinlongliao.easytask.delaytask.job.DelayTaskJob;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
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
    String data = JSONObject.toJSONString(delayTaskJob);
    System.out.println("\nJOB:\n" + data);
    String body = HttpClient.post("http://127.0.0.1:8888/v1")
      .acceptJson()
      .contentType(HttpClient.CONTENT_TYPE_JSON, HttpClient.CHARSET_UTF8)
      .send(data)
      .body(StandardCharsets.UTF_8.name());
    System.out.println("body = " + body);
  }

  @Ignore
  @Test
  public void testServer() throws InterruptedException {
    final DelayTaskHandlerFactory delayTaskHandlerFactory = DelayTaskHandlerFactory.getInstance();
    delayTaskHandlerFactory.addHandler("delay", new TestDelayTaskHandler());
    new DefaultBootStart().startServer("0.0.0.0", 8888, null);
    Thread.sleep(Integer.MAX_VALUE);
  }
}
