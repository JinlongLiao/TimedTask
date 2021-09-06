package io.github.jinlongliao.easytask.boot.handler;

 import io.github.jinlongliao.easytask.common.http.server.hanler.IHandler;
import io.github.jinlongliao.easytask.common.http.server.hanler.RequestParser;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * @author liaojinlong
 * @since 2021/9/6 14:19
 */
public class HandlerMsgV1Service implements IHandler {
  private static final String RES_SUCCESS = "{\"result\":200}";

  @Override
  public FullHttpResponse handle(HttpRequest request) throws IOException {
    Map<String, String> paramMap = new RequestParser(request).parse();


    final FullHttpResponse response = new DefaultFullHttpResponse(request.protocolVersion(),
      HttpResponseStatus.OK, Unpooled.wrappedBuffer(RES_SUCCESS.getBytes(StandardCharsets.UTF_8)));
    response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
    return response;
  }

  @Override
  public Collection<String> getRequestMapping() {

    return Arrays.asList("/v1", "/v1/");
  }
}
