package io.github.jinlongliao.easytask.boot.core;

import io.github.jinlongliao.easytask.boot.handler.JobHandleFactory;
import io.github.jinlongliao.easytask.boot.job.JobRegisterFactory;
import io.github.jinlongliao.easytask.common.http.server.hanler.IHandler;
import io.github.jinlongliao.easytask.common.http.server.hanler.RequestParser;
import io.github.jinlongliao.easytask.core.exception.NotFountException;
import io.github.jinlongliao.easytask.core.job.AbstractJob;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author liaojinlong
 * @since 2021/9/6 14:19
 */
public class HandlerMsgV1Service implements IHandler {
  private static final Logger log = LoggerFactory.getLogger(HandlerMsgV1Service.class);
  private static final String JOB_TYPE = "jobType";
  private static final String RES_SUCCESS = "{\"result\":200}";
  private static final String RES_ERROR = "{\"result\":500,\"errorMsg\":\"%s\"}";
  private static JobRegisterFactory jobRegisterFactory = JobRegisterFactory.getInstance();
  private static JobHandleFactory jobHandleFactory = JobHandleFactory.getInstance();


  @Override
  public FullHttpResponse handle(HttpRequest request) throws IOException {
    if (!request.method().equals(HttpMethod.POST)) {
      final DefaultFullHttpResponse response = new DefaultFullHttpResponse(request.protocolVersion(),
        HttpResponseStatus.OK, Unpooled.wrappedBuffer(String.format(RES_ERROR, "仅支持POST").getBytes(StandardCharsets.UTF_8)));
      response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
      return response;
    }
    FullHttpResponse response;
    try {
      Map<String, Object> paramMap = new RequestParser(request).parse();
      AbstractJob job = toBuildMsg(paramMap);
      jobHandleFactory.dispatcherJob(job);
      response = new DefaultFullHttpResponse(request.protocolVersion(),
        HttpResponseStatus.OK, Unpooled.wrappedBuffer(RES_SUCCESS.getBytes(StandardCharsets.UTF_8)));
      response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
    } catch (Exception e) {
      if (log.isErrorEnabled()) {
        log.error(e.getLocalizedMessage(), e);
      }
      response = new DefaultFullHttpResponse(request.protocolVersion(),
        HttpResponseStatus.OK, Unpooled.wrappedBuffer(String.format(RES_ERROR, e.getMessage()).getBytes(StandardCharsets.UTF_8)));
      response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
    }

    return response;
  }

  private AbstractJob toBuildMsg(Map<String, Object> paramMap) throws Exception {
    if (!paramMap.containsKey(JOB_TYPE)) {
      throw new NotFountException("未发现此消息类型");
    }
    Object msg = paramMap.get(JOB_TYPE);
    final String msgType = String.valueOf(msg instanceof Collection ? ((Collection) msg).iterator().next() : msg);

    return jobRegisterFactory.parseJobMsg(msgType, new HashMap<>(paramMap));
  }

  @Override
  public Collection<String> getRequestMapping() {
    return Arrays.asList("/v1", "/v1/");
  }
}
