package io.github.jinlongliao.easytask.common.exception;

/**
 * 不支持的方法
 *
 * @author liaojinlong
 * @since 2021/8/30 19:44
 */
public class NotSupportException extends RuntimeException {
  public NotSupportException() {
    super();
  }

  public NotSupportException(String message) {
    super(message);
  }

  public NotSupportException(String message, Throwable cause) {
    super(message, cause);
  }

  public NotSupportException(Throwable cause) {
    super(cause);
  }

  protected NotSupportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
