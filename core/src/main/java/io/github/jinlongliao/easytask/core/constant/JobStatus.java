package io.github.jinlongliao.easytask.core.constant;

/**
 * 任务类型
 *
 * @author liaojinlong
 * @since 2021/8/30 16:51
 */
public enum JobStatus {
  /**
   * 正常
   */
  NORMAL(0),

  /**
   * 停止
   */
  STOP(1),

  ;
  private final int type;

  JobStatus(int type) {
    this.type = type;
  }

  public int getType() {
    return type;
  }
}
