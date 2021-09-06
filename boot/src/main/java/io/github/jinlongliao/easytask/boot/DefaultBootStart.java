package io.github.jinlongliao.easytask.boot;

import io.github.jinlongliao.easytask.boot.handler.HandlerMsgV1Service;
import io.github.jinlongliao.easytask.common.http.server.EmbeddedServer;
import io.github.jinlongliao.easytask.common.http.server.EmbeddedServerDispatcher;
import io.github.jinlongliao.easytask.common.http.server.hanler.*;

import io.netty.handler.ssl.SslContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author liaojinlong
 * @since 2021/9/6 14:16
 */
public class DefaultBootStart {
  private static final Logger log = LoggerFactory.getLogger(DefaultBootStart.class);

  public static void main(String[] args) {
    try {
      startServer("0.0.0.0", 8888, null);
    } catch (Exception e) {
      log.error(e.getLocalizedMessage(), e);
    }
  }

  private static void startServer(String host, int port, SslContext sslCtx) {
    final HandlerFactory factory = HandlerFactory.getInstance();
    factory.addHandler(EmbeddedServerDispatcher.class, new HandlerMsgV1Service());
    new Thread(() -> {
      try {
        new EmbeddedServer().newServer(host, port, sslCtx);
      } catch (Exception e) {
        log.error(e.getLocalizedMessage(), e);
      }
    }).start();

  }
}
