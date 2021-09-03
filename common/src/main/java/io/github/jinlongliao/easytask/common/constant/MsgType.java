package io.github.jinlongliao.easytask.common.constant;

/**
 * 任务中心与客户端的消息交换格式
 *
 * @author liaojinlong
 * @since 2021/8/30 16:48
 */
public enum MsgType {
  /**
   * 新增任务
   */
  ADD_TASK(0),

  /**
   * 开始任务
   */
  START_TASK(1),
  /**
   * 停止任务
   */
  STOP_TASK(2),

  /**
   * 移除任务
   */
  REMOVE_TASK(3),

  ;
  private final int type;

  MsgType(int type) {
    this.type = type;
  }

  public int getType() {
    return type;
  }
}
