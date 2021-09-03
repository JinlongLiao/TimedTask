package io.github.jinlongliao.easytask.common.http.server;

import com.alibaba.fastjson.JSON;
import io.github.jinlongliao.easytask.common.http.server.hanler.RequestParser;
import io.github.jinlongliao.easytask.common.http.client.HttpClient;
import io.github.jinlongliao.easytask.common.http.server.hanler.HandlerFactory;
import io.github.jinlongliao.easytask.common.http.server.hanler.IHandler;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liaojinlong
 * @since 2021/8/30 17:56
 */
public class EmbeddedServerTest {
  private static final boolean SSL = false;

  @Test
  public void testEmbeddedServer() throws IOException, InterruptedException {
    final LocalDate now = LocalDate.now();
    final LocalDate localDate = now.plusYears(-18l);

    final EmbeddedServer embeddedServer = new EmbeddedServer();
    new Thread(() -> {
      try {
        embeddedServer.newServer(8888);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }).start();
    final HandlerFactory factory = HandlerFactory.getInstance();
    factory.addHandler(EmbeddedServerDispatcher.class, new IHandler() {
      @Override
      public String getRequestMapping() {
        return "/uewuew";
      }

      @Override
      public FullHttpResponse handle(HttpRequest request) throws IOException {
        // 将GET, POST所有请求参数转换成Map对象
        Map<String, String> paramMap = new RequestParser(request).parse();
        final FullHttpResponse response = new DefaultFullHttpResponse(request.protocolVersion(),
          new HttpResponseStatus(200, ""),
          Unpooled.wrappedBuffer(JSON.toJSONBytes(paramMap)));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
        return response;
      }
    });
    Map<String, Object> param = new HashMap<>(2);
    param.put("key1", true);
    param.put("key3", true);
    param.put("key2", true);
    final HttpClient post = HttpClient.post("http://127.0.0.1:8888/uewuew", param, true);

    final String body = post.body();
    Assert.assertNotNull(body);
    Thread.sleep(20 * 1000);
    embeddedServer.shutdown();
  }
}

