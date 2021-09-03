package io.github.jinlongliao.easytask.core.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * @author liaojinlong
  * @since 2021/9/1 16:35
 */
public class EasyTaskRunTimeException extends RuntimeException{
  public EasyTaskRunTimeException() {
    super();
  }

  public EasyTaskRunTimeException(String message) {
    super(message);
  }

  public EasyTaskRunTimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public EasyTaskRunTimeException(Throwable cause) {
    super(cause);
  }

  protected EasyTaskRunTimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }

  @Override
  public String getLocalizedMessage() {
    return super.getLocalizedMessage();
  }

  @Override
  public synchronized Throwable getCause() {
    return super.getCause();
  }

  @Override
  public synchronized Throwable initCause(Throwable cause) {
    return super.initCause(cause);
  }

  @Override
  public String toString() {
    return super.toString();
  }

  @Override
  public void printStackTrace() {
    super.printStackTrace();
  }

  @Override
  public void printStackTrace(PrintStream s) {
    super.printStackTrace(s);
  }

  @Override
  public void printStackTrace(PrintWriter s) {
    super.printStackTrace(s);
  }

  @Override
  public synchronized Throwable fillInStackTrace() {
    return super.fillInStackTrace();
  }

  @Override
  public StackTraceElement[] getStackTrace() {
    return super.getStackTrace();
  }

  @Override
  public void setStackTrace(StackTraceElement[] stackTrace) {
    super.setStackTrace(stackTrace);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
  }
}
