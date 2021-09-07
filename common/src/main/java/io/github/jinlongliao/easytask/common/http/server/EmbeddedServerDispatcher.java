/*
 * Copyright 2013 The Netty Project
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

import io.github.jinlongliao.easytask.common.http.server.hanler.HandlerFactory;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpHeaderValues.*;

/**
 * 消息转发
 *
 * @author liaojinlong
 * @since 2021/8/30 18:11
 */

public class EmbeddedServerDispatcher extends SimpleChannelInboundHandler<HttpObject> {
  private static final Logger log = LoggerFactory.getLogger(EmbeddedServerDispatcher.class);

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) {
    ctx.flush();
  }

  @Override
  public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
    if (!(msg instanceof HttpRequest)) {
      FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND,
        Unpooled.copiedBuffer("NotFound !", StandardCharsets.UTF_8));
      response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
      ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
      return;
    }
    HttpRequest req = (HttpRequest) msg;

    boolean keepAlive = HttpUtil.isKeepAlive(req);
    FullHttpResponse response;
    try {
      response = HandlerFactory.getInstance().getHandler(this.getClass(), req.uri().split("\\?")[0]).handle(req);
    } catch (IOException e) {
      log.warn("Http Parse Error", e);
      response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR,
        Unpooled.wrappedBuffer(e.getLocalizedMessage().getBytes(StandardCharsets.UTF_8)));
    }
    response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
    if (keepAlive) {
      if (!req.protocolVersion().isKeepAliveDefault()) {
        response.headers().set(CONNECTION, KEEP_ALIVE);
      }
    } else {
      // Tell the client we're going to close the connection.
      response.headers().set(CONNECTION, CLOSE);
    }

    ChannelFuture f = ctx.write(response);

    if (!keepAlive) {
      f.addListener(ChannelFutureListener.CLOSE);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    log.error(cause.getLocalizedMessage(), cause);
    ctx.close();
  }
}
