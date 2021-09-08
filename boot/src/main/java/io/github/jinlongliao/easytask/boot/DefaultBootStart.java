package io.github.jinlongliao.easytask.boot;

import io.github.jinlongliao.easytask.boot.core.HandlerMsgV1Service;
import io.github.jinlongliao.easytask.boot.handler.JobHandleFactory;
import io.github.jinlongliao.easytask.boot.loader.JobLoaderFactory;
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
  private EmbeddedServer embeddedServer;
  private JobLoaderFactory jobLoaderFactory = JobLoaderFactory.getInstance();
  private JobHandleFactory jobHandleFactory = JobHandleFactory.getInstance();

  public DefaultBootStart() {
    super();
    jobLoaderFactory.getAllJobLoader()
      .stream()
      .forEach(
        t -> t.loadAllJob()
          .forEach(
            job -> {
              log.info("启动时本地加载JOB {}", job);
              jobHandleFactory.dispatcherJob(job);

            }
          )
      );
  }

  public void startServer(String host, int port, SslContext sslCtx) {
    final HandlerFactory factory = HandlerFactory.getInstance();
    factory.addHandler(EmbeddedServerDispatcher.class, new HandlerMsgV1Service());
    new Thread(() -> {
      try {
        (embeddedServer = new EmbeddedServer()).newServer(host, port, sslCtx);
      } catch (Exception e) {
        log.error(e.getLocalizedMessage(), e);
      }
    }).start();
  }

  public EmbeddedServer getEmbeddedServer() {
    return embeddedServer;
  }
}
