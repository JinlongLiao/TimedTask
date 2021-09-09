package io.github.jinlongliao.easytask.delaytask.handler;

/**
 * 延迟任务模型
 *
 * @author liaojinlong
 * @since 2021/9/9 11:04
 */
public class DelayTaskModel {
  /**
   * 执行的任务ID
   */
  private String taskId;
  /**
   * 任务的扩展参数
   */
  private String extParam;

  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public String getExtParam() {
    return extParam;
  }

  public void setExtParam(String extParam) {
    this.extParam = extParam;
  }

  @Override
  public String toString() {
    return "DelayTaskModel{" +
      "taskId='" + taskId + '\'' +
      ", extParam='" + extParam + '\'' +
      '}';
  }
}
