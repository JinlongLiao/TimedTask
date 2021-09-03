package io.github.jinlongliao.easytask.common.msg;

/**
 * @author liaojinlong
 * @since 2021/8/30 16:55
 */
public abstract class AbstractBaseMsg {
  /**
   * 客户端组的唯一ID
   */
  private String groupKey;

  /**
   * 客户端的唯一标识
   */
  private String unionKey;
  /**
   * 消息类型
   */
  protected int msgType;

  public int getMsgType() {
    return msgType;
  }

  public void setMsgType(int msgType) {
    this.msgType = msgType;
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
}
