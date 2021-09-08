package io.github.jinlongliao.easytask.delaytask.exception;

import io.github.jinlongliao.easytask.common.exception.EasyTaskRunTimeException;

/**
 * 多线程运行错误
 *
 * @author liaojinlong
 * @since 2021/9/8 19:40
 */
public class TaskRunningException extends EasyTaskRunTimeException {
  public TaskRunningException() {
    super();
  }

  public TaskRunningException(String message) {
    super(message);
  }

  public TaskRunningException(String message, Throwable cause) {
    super(message, cause);
  }

  public TaskRunningException(Throwable cause) {
    super(cause);
  }

  protected TaskRunningException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
