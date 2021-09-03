package io.github.jinlongliao.easytask.common.msg.impl;

import io.github.jinlongliao.easytask.common.msg.AbstractBaseMsg;

/**
 * Task Msg
 *
 * @author liaojinlong
 * @since 2021/8/30 16:59
 */
public class TaskMsg extends AbstractBaseMsg {
  /**
   * 任务的ID
   */
  private String taskId;

  /**
   * 用于扩展的参数
   */
  private String extraData;


  public String getTaskId() {
    return taskId;
  }

  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }

  public String getExtraData() {
    return extraData;
  }

  public void setExtraData(String extraData) {
    this.extraData = extraData;
  }


}
