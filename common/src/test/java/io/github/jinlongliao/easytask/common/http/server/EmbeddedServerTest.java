package io.github.jinlongliao.easytask.common.http.server;

import com.alibaba.fastjson.JSON;
import io.github.jinlongliao.easytask.common.http.client.HttpClient;
import io.github.jinlongliao.easytask.common.http.server.hanler.RequestParser;
import io.github.jinlongliao.easytask.common.http.server.hanler.HandlerFactory;
import io.github.jinlongliao.easytask.common.http.server.hanler.IHandler;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
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
    final Thread thread = new Thread(() -> {
      try {
        embeddedServer.newServer(8888);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    thread.start();
    final HandlerFactory factory = HandlerFactory.getInstance();
    factory.addHandler(EmbeddedServerDispatcher.class, new IHandler() {
      @Override
      public Collection<String> getRequestMapping() {
        return Collections.singleton("/uewuew");
      }

      @Override
      public FullHttpResponse handle(HttpRequest request) throws IOException {
        // 将GET, POST所有请求参数转换成Map对象
        Map<String, Object> paramMap = new RequestParser(request).parse();
        final FullHttpResponse response = new DefaultFullHttpResponse(request.protocolVersion(),
          new HttpResponseStatus(200, ""),
          Unpooled.wrappedBuffer(JSON.toJSONBytes(paramMap)));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=UTF-8");
        return response;
      }
    });
    final String body = HttpClient.post(new URL("http://localhost:8888/uewuew"))
      .acceptJson()
      .contentType(HttpClient.CONTENT_TYPE_JSON, HttpClient.CHARSET_UTF8)
      .send("{\"assignee\" : \"jbarrez\"}")
      .body(HttpClient.CHARSET_UTF8);

    System.out.println("response = " + body);
    Thread.sleep(5 * 1000);
    embeddedServer.shutdown();
  }
}

