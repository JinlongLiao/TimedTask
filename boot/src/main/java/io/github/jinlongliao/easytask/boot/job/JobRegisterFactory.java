package io.github.jinlongliao.easytask.boot.job;

import io.github.jinlongliao.commons.mapstruct.BeanCopierUtils;
import io.github.jinlongliao.commons.mapstruct.core.IData2Object;
import io.github.jinlongliao.easytask.common.util.ServiceLoaderUtil;
import io.github.jinlongliao.easytask.core.exception.NotFountException;
import io.github.jinlongliao.easytask.core.job.AbstractJob;

import java.util.Map;

/**
 * @author liaojinlong
 * @since 2021/9/7 9:58
 */
public class JobRegisterFactory {

  private static JobRegisterFactory mInstance;
  private static DefaultJobLoadProcess jobLoadProcess;

  public static JobRegisterFactory getInstance() {
    if (mInstance == null) {
      synchronized (JobRegisterFactory.class) {
        if (mInstance == null) {
          mInstance = new JobRegisterFactory();
        }
      }
    }
    return mInstance;
  }

  private JobRegisterFactory() {
    JobRegisterFactory.jobLoadProcess = new DefaultJobLoadProcess();
    ServiceLoaderUtil.loadClass(AbstractJob.class, jobLoadProcess);
  }

  /**
   * @param msgType
   * @return /
   * @throws NotFountException
   */
  public IData2Object getMsgData2Object(String msgType) throws NotFountException {
    final Class<? extends AbstractJob> jobClassByType = jobLoadProcess.getJobClassByType(msgType);
    if (jobClassByType == null) {
      throw new NotFountException("未发现 msgType：" + msgType);
    }
    return BeanCopierUtils.getFullData2Object(jobClassByType);
  }

  /**
   * 数据映射转换
   *
   * @param msgType
   * @param data
   * @return /
   * @throws Exception
   */
  public AbstractJob parseJobMsg(String msgType, Map<String, Object> data) throws Exception {
    return this.getMsgData2Object(msgType).toMapConverter(data);
  }

}
