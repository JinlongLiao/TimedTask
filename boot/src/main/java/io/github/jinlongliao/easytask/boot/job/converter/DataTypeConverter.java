package io.github.jinlongliao.easytask.boot.job.converter;

import io.github.jinlongliao.easytask.core.constant.JobStatus;

import java.util.Collection;

/**
 * 数据类型转换
 *
 * @author liaojinlong
 * @since 2021/9/7 15:55
 */
public class DataTypeConverter {
  public static JobStatus toJobStatus(Object obj) {
    if (obj == null) {
      return JobStatus.NORMAL;
    }
    return obj.toString().equals("NORMAL") ? JobStatus.NORMAL : JobStatus.STOP;
  }

  public static Collection collection(Object obj) {
    return null;

  }
}
