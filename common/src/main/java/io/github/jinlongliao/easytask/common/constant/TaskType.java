package io.github.jinlongliao.easytask.common.constant;

/**
 * 任务类型
 *
 * @author liaojinlong
 * @since 2021/8/30 16:51
 */
public enum TaskType {
  /**
   * 定时任务
   */
  TIMED_TASK(0),

  /**
   * 延迟任务
   */
  DELAY_TASK(1),

  /**
   * 定时延迟任务
   */
  TIMED_DELAY_TASK(2),
  ;
  private final int type;

  TaskType(int type) {
    this.type = type;
  }

  public int getType() {
    return type;
  }
}
