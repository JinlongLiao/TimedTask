package io.github.jinlongliao.easytask.core.job;

import io.github.jinlongliao.easytask.core.constant.JobStatus;

/**
 * 需要执行的任务
 *
 * @author liaojinlong
 * @since 2021/9/1 12:04
 */
public class Job {
  /**
   * 任务状态
   */
  private JobStatus jobStatus;
  /**
   * 客户端组的唯一ID
   */
  private String groupKey;

  /**
   * 客户端的唯一标识
   */
  private String unionKey;
  /**
   * 任务的ID
   */
  private String taskId;

  /**
   * 用于扩展的参数
   */
  private String extraData;


  public JobStatus getJobStatus() {
    return jobStatus;
  }

  public void setJobStatus(JobStatus jobStatus) {
    this.jobStatus = jobStatus;
  }

  public String getGroupKey() {
    return groupKey;
  }

  public void setGroupKey(String groupKey) {
    this.groupKey = groupKey;
  }

  public String getUnionKey() {
    return unionKey;
  }

  public void setUnionKey(String unionKey) {
    this.unionKey = unionKey;
  }

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

  /**
   * 用于标识任务的唯一标识
   *
   * @return /
   */
  public String uuid() {
    return getGroupKey() + "_" + getUnionKey() + "_" + getTaskId();
  }
}
