package io.github.jinlongliao.easytask.delaytask.delay;

import com.alibaba.fastjson.JSONObject;
import io.github.jinlongliao.easytask.common.http.client.HttpClient;
import io.github.jinlongliao.easytask.delaytask.handler.DelayTaskModel;
import io.github.jinlongliao.easytask.delaytask.handler.delay.DelayTaskHandler;
import io.github.jinlongliao.easytask.delaytask.job.DelayTask;
import io.github.jinlongliao.easytask.delaytask.task.RunnableTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDelayTaskHandler extends DelayTaskHandler {
  private static final Logger log = LoggerFactory.getLogger(TestDelayTaskHandler.class);
  private final ScheduledAssistant assistant = ScheduledAssistant.getInstance();

  @Override
  public Object handle(DelayTaskModel delayTaskModel) {
    log.info("延迟Job ：{}", taskJob);
    final DelayTask delayTask = JSONObject.parseObject(delayTaskModel.getExtParam(), DelayTask.class);
    log.info("add Delay Task {}", System.currentTimeMillis());
    assistant.runDelayTask(new RunnableTask(delayTask.getDelay(), delayTask.getTimeUnit()) {
      @Override
      public Object running() {
        log.info("Running Delay Task {}", System.currentTimeMillis());
        final String body = HttpClient.get(delayTask.getUrl()).body(HttpClient.CHARSET_UTF8);
        log.info("get Body :\n{}", body);
        return body;
      }
    });
    return true;
  }
}
