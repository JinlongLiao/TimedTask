package io.github.jinlongliao.easytask.common.msg.impl;

import io.github.jinlongliao.easytask.common.msg.AbstractBaseMsg;

/**
 * 注册消息
 *
 * @author liaojinlong
 * @since 2021/8/30 16:59
 */
public class RegisteredMsg extends AbstractBaseMsg {
  private String host;
  private int port;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

}
