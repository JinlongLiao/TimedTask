package io.github.jinlongliao.easytask.common.annotation;

import java.lang.annotation.*;

/**
 * @author liaojinlong
 * @since 2021/9/6 19:06
 */
@Target({ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Order {

  String value() default "";

  String name() default "";

  /**
   * 排序
   *
   * @return /
   */
  int order() default -1;

  /**
   * 是否生效
   *
   * @return /
   */
  boolean status() default true;
}
