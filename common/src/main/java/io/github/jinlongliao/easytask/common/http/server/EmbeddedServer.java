/*
 * Copyright 2014 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.github.jinlongliao.easytask.common.http.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.*;
import io.netty.util.internal.PlatformDependent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * 基于Netty 的内嵌Server
 *
 * @author liaojinlong
 * @since 2021/8/30 17:59
 */
public final class EmbeddedServer implements Closeable {
  private static final Logger log = LoggerFactory.getLogger(EmbeddedServer.class);
  private EventLoopGroup group;
  private EventLoopGroup child;
  private ServerBootstrap bootstrap;
  /**
   * 基础配置
   */
  private String host;
  private int port;
  private SslContext sslCtx;


  public void newServer(int port) throws Exception {
    this.newServer("0.0.0.0", port, null);
  }

  public void newServer(int port, SslContext sslCtx) throws Exception {
    this.newServer("0.0.0.0", port, sslCtx);
  }

  public void newServer(String host, int port, SslContext sslCtx) throws Exception {
    this.host = host;
    this.port = port;
    this.sslCtx = sslCtx;
    // Configure the server.
    final int processors = Runtime.getRuntime().availableProcessors();
    if (PlatformDependent.isOsx() || PlatformDependent.isWindows()) {
      group = new NioEventLoopGroup(1);
      child = new NioEventLoopGroup(processors);
    } else {
      group = new EpollEventLoopGroup(1);
      child = new EpollEventLoopGroup(processors);
    }
    try {
      bootstrap = new ServerBootstrap();
      bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
      bootstrap.group(group, child)
        .channel(NioServerSocketChannel.class)
        .handler(new LoggingHandler(LogLevel.INFO))
        .childHandler(new EmbeddedServerInitializer(sslCtx));
      Channel ch = bootstrap.bind(host, port).sync().channel();
      log.info("Open your HTTP/2-enabled web browser and navigate to " + (sslCtx != null ? "https" : "http") + "://127.0.0.1:" + port + '/');
      ch.closeFuture().sync();
    } finally {
      group.shutdownGracefully();
      child.shutdownGracefully();
    }
  }

  public void shutdown() throws IOException {
    this.close();
  }

  @Override
  public void close() throws IOException {
    if (this.bootstrap != null) {
      group.shutdownGracefully();
      child.shutdownGracefully();
    }
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }

  public SslContext getSslCtx() {
    return sslCtx;
  }

  public ServerBootstrap getBootstrap() {
    return bootstrap;
  }
}
