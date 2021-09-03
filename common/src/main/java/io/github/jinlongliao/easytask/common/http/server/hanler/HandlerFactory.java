package io.github.jinlongliao.easytask.common.http.server.hanler;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 业务处理Handler
 *
 * @author liaojinlong
 * @since 2021/8/30 18:31
 */
public class HandlerFactory {
  /**
   * 忽略的URL
   */
  public static Set<String> skipUrl = new HashSet<>(16);
  /**
   * 普通的URl 映射
   */
  private static final Map<Class<?>, Map<String, IHandler>> normalHandlerCache = new HashMap<>(8);
  /**
   * 支持正则的映射
   */
  private static final Map<Class<?>, Map<Pattern, IHandler>> patternHandlerCache = new HashMap<>(8);
  private static final IHandler DEFAULT_HANDLER = () -> "";

  static {
    skipUrl.add("/favicon.ico");
  }

  /**
   * 新增HANDLE
   *
   * @param key
   * @param handler
   */
  public void addHandler(Class<?> key, IHandler handler) {
    if (key == null || handler == null) {
      return;
    }

    final boolean enablePattern = handler.enablePattern();
    if (enablePattern) {
      final Map<Pattern, IHandler> handlerMap = patternHandlerCache.getOrDefault(key, new HashMap<>(2));
      handlerMap.put(Pattern.compile(handler.getRequestMapping()), handler);
      patternHandlerCache.put(key, handlerMap);
    } else {
      final Map<String, IHandler> handlerMap = normalHandlerCache.getOrDefault(key, new HashMap<>(2));
      handlerMap.put(handler.getRequestMapping(), handler);
      normalHandlerCache.put(key, handlerMap);
    }
  }

  /**
   * 获取各个隔离环境中的 业务处理类
   *
   * @param key
   * @param reqUrl
   * @return /
   */
  public IHandler getHandler(Class<?> key, String reqUrl) {
    if (skipUrl.contains(reqUrl)) {
      return DEFAULT_HANDLER;
    }
    IHandler handler = normalHandlerCache.getOrDefault(key, Collections.emptyMap()).get(reqUrl);
    if (handler == null) {
      final Map<Pattern, IHandler> patternIHandlerMap = patternHandlerCache.get(key);
      if (patternIHandlerMap == null) {
        return DEFAULT_HANDLER;
      }
      for (Map.Entry<Pattern, IHandler> entry : patternIHandlerMap.entrySet()) {
        if (entry.getKey().matcher(reqUrl).find()) {
          return entry.getValue();
        }
      }
      handler = DEFAULT_HANDLER;
    }
    return handler;
  }

  private static HandlerFactory mInstance;

  public static HandlerFactory getInstance() {
    if (mInstance == null) {
      synchronized (HandlerFactory.class) {
        if (mInstance == null) {
          mInstance = new HandlerFactory();
        }
      }
    }
    return mInstance;
  }

  private HandlerFactory() {
  }

}
