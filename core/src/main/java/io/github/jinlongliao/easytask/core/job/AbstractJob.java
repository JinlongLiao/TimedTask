package io.github.jinlongliao.easytask.core.job;

import io.github.jinlongliao.easytask.core.constant.JobStatus;

import java.util.Collection;

/**
 * 需要执行的任务
 *
 * @author liaojinlong
 * @since 2021/9/1 12:04
 */
public abstract class AbstractJob {
  /**
   * 任务状态
   */
  private JobStatus jobStatus = JobStatus.NORMAL;

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

  protected Collection<String> jobType;

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
   * 返回任务类型
   * <p>
   * return /
   */
  public abstract Collection<String> getJobType();

  public void setJobType(Collection<String> jobType) {
    this.jobType = jobType;
  }

  /**
   * 用于标识任务的唯一标识
   *
   * @return /
   */
  public String uuid() {
    return getGroupKey() + "_" + getUnionKey() + "_" + getTaskId();
  }

  @Override
  public String toString() {
    return "AbstractJob{" +
      "jobStatus=" + jobStatus +
      ", groupKey='" + groupKey + '\'' +
      ", unionKey='" + unionKey + '\'' +
      ", taskId='" + taskId + '\'' +
      ", extraData='" + extraData + '\'' +
      '}';
  }
}
