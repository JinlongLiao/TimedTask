package io.github.jinlongliao.easytask.delaytask.job;

import java.util.concurrent.TimeUnit;

public class DelayTask {
  private int delay;
  private TimeUnit timeUnit;
  private String url;

  public int getDelay() {
    return delay;
  }

  public void setDelay(int delay) {
    this.delay = delay;
  }

  public TimeUnit getTimeUnit() {
    return timeUnit;
  }

  public void setTimeUnit(TimeUnit timeUnit) {
    this.timeUnit = timeUnit;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}
