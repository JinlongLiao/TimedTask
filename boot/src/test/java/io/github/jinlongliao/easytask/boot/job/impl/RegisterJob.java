package io.github.jinlongliao.easytask.boot.job.impl;


import io.github.jinlongliao.easytask.core.annotaion.JobType;
import io.github.jinlongliao.easytask.core.job.AbstractAnnotationJob;

/**
 * 测试使用
 * <pre><code class="language-JSON">{
 *     "groupKey":"test",
 *     "taskId":"taskId",
 *     "msgType":"register_job",
 *     "extraData":"extraData",
 *     "unionKey":"test"
 * }</code></pre>
 * </code></p>
 *
 * @author liaojinlong
 * @since 2021/9/7 17:06
 */
@JobType({RegisterJob.MSG_TYPE})
public class RegisterJob extends AbstractAnnotationJob {
  public static final String MSG_TYPE = "register_job";

  @Override
  public String toString() {
    return super.toString();
  }
}
