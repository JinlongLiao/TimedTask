package io.github.jinlongliao.easytask.common.http.server.hanler;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;


/**
 * 业务处理实现 接口
 *
 * @author liaojinlong
 * @since 2021/8/30 18:36
 */
public interface IHandler {
  Logger log = LoggerFactory.getLogger(IHandler.class);

  /**
   * 业务处理
   *
   * @param request
   * @return /
   * @throws IOException
   */
  default FullHttpResponse handle(HttpRequest request) throws IOException {
    log.debug("Empty Implement url:{}", request.uri());
    return new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.NOT_FOUND, Unpooled.wrappedBuffer(new byte[0]));
  }

  /**
   * 处理的URl
   *
   * @return /
   */
  Collection<String> getRequestMapping();

  /**
   * 支持正则映射
   *
   * @return 是否支持正则映射
   */
  default boolean enablePattern() {
    return false;
  }
}
