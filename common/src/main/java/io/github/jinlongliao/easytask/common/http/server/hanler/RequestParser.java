package io.github.jinlongliao.easytask.common.http.server.hanler;


import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liaojinlong
 * @since 2021/8/30 19:43
 */
public class RequestParser {
  private HttpRequest httpRequest;

  /**
   * 构造一个解析器
   *
   * @param httpRequest
   */
  public RequestParser(HttpRequest httpRequest) {
    this.httpRequest = httpRequest;
  }

  /**
   * 解析请求参数
   *
   * @return 包含所有请求参数的键值对, 如果没有参数, 则返回空Map
   * @throws IOException
   */
  public Map<String, String> parse() throws IOException {
    HttpMethod method = httpRequest.method();

    Map<String, String> paramMap;

    if (HttpMethod.POST == method) {
      paramMap = parsePost(httpRequest);
    } else {
      paramMap = new HashMap<>(8);
    }
    paramMap.putAll(parseUrl(httpRequest.uri()));
    return Collections.unmodifiableMap(paramMap);
  }

  /**
   * 解析Get
   *
   * @param uri
   * @return /
   */
  private Map<String, String> parseUrl(String uri) {
    Map<String, String> paramMap = new HashMap<>(8);
    // 是GET请求
    QueryStringDecoder decoder = new QueryStringDecoder(uri);
    decoder.parameters().entrySet().forEach(entry -> {
      // entry.getValue()是一个List, 只取第一个元素
      paramMap.put(entry.getKey(), entry.getValue().get(0));
    });
    return paramMap;
  }

  /**
   * 解析POST
   *
   * @param request
   * @return /
   */
  private Map<String, String> parsePost(HttpRequest request) throws IOException {
    if (!(request instanceof FullHttpRequest)) {
      return Collections.emptyMap();
    }

    Map<String, String> paramMap = new HashMap<>(8);
    FullHttpRequest fullReq = (FullHttpRequest) request;
    // 是POST请求
    HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(fullReq);
    decoder.offer(fullReq);
    List<InterfaceHttpData> paramList = decoder.getBodyHttpDatas();

    for (InterfaceHttpData param : paramList) {
      Attribute data = (Attribute) param;
      paramMap.put(data.getName(), data.getValue());
    }
    return paramMap;
  }
}
