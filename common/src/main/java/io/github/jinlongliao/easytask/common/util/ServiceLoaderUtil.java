package io.github.jinlongliao.easytask.common.util;

import io.github.jinlongliao.easytask.common.annotation.Order;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * SPI Util
 *
 * @author liaojinlong
 * @since 2021/9/6 19:04
 */
public class ServiceLoaderUtil {
  private static Map<Class<?>, List<Class<?>>> CLASS_CACHE = new ConcurrentHashMap<>(8);
  private static Map<Class<?>, LoadProcess> LOAD_PROCESS_CACHE = new ConcurrentHashMap<>(8);
  private static final LoadProcess DEFAULT_LOAD_PROCESS = new LoadProcess<Object>() {
    @Override
    public List<Class<Object>> initLoader(Iterator<Object> iterator) {
      return LoadProcess.super.initLoader(iterator);
    }

    @Override
    public List<Class<Object>> filter(List<Class<Object>> collection) {
      return LoadProcess.super.filter(collection);
    }

    @Override
    public boolean beforeAdd(Class<Object> tClass, List<Class<Object>> classes, boolean sort) {
      return LoadProcess.super.beforeAdd(tClass, classes, sort);
    }
  };

  /**
   * 排序过滤
   *
   * @param tmp
   * @param <T>
   * @return /
   */
  private static <T> List<Class<T>> toSort(List<Class<T>> tmp) {
    AtomicInteger remove = new AtomicInteger();
    tmp.sort((a, b) -> {
      final Order aA = a.getAnnotation(Order.class);
      final Order bA = b.getAnnotation(Order.class);
      int aO = 1;
      int b0 = 1;
      if (aA != null) {
        if (aA.status()) {
          aO = aA.order();
        } else {
          remove.getAndIncrement();
          aO = Integer.MIN_VALUE;
        }
      }
      if (bA != null) {
        if (bA.status()) {
          b0 = bA.order();
        } else {
          remove.getAndIncrement();
          b0 = Integer.MIN_VALUE;
        }
      }
      return b0 - aO;
    });
    final List<Class<T>> classList;
    if (remove.get() > 0) {
      classList = tmp.subList(remove.get(), tmp.size());
    } else {
      classList = tmp;
    }
    return classList;
  }

  /**
   * 加载 SPI，基于默认的处理策略
   *
   * @param tClass
   * @param <T>
   * @return /
   */
  public static <T> List<Class<T>> loadClass(Class<T> tClass) {
    return loadClass(tClass, DEFAULT_LOAD_PROCESS);
  }

  /**
   * * 加载 SPI，基于指定的处理策略
   *
   * @param tClass
   * @param loadProcess
   * @param <T>
   * @return /
   */
  public static <T> List<Class<T>> loadClass(Class<T> tClass, LoadProcess loadProcess) {
    if (!LOAD_PROCESS_CACHE.containsKey(loadProcess)) {
      LOAD_PROCESS_CACHE.put(tClass, loadProcess);
    }
    if (CLASS_CACHE.containsKey(tClass)) {
      List<Class<?>> classes = CLASS_CACHE.get(tClass);
      if (classes != null) {
        return classes.stream().map(t -> (Class<T>) t).collect(Collectors.toList());
      }

    }
    List<Class<T>> classList = loadProcess.filter(loadProcess.initLoader(ServiceLoader.load(tClass).iterator()));
    CLASS_CACHE.put(tClass, classList.stream().collect(Collectors.toList()));
    return classList;
  }

  /**
   * 新增Class
   *
   * @param tClass
   * @param classList
   * @param <T>
   */
  public static <T> void addClass(Class<T> tClass, List<Class<T>> classList) {
    addNewJob(tClass, classList, false, LOAD_PROCESS_CACHE.getOrDefault(tClass, DEFAULT_LOAD_PROCESS));
  }

  public static <T> void addNewJob(Class<T> tClass, List<Class<T>> classList, LoadProcess loadProcess) {
    addNewJob(tClass, classList, false, loadProcess);
  }

  public static <T> void addNewJob(Class<T> tClass, List<Class<T>> classList, boolean sort, LoadProcess loadProcess) {
    List<Class<?>> classes = CLASS_CACHE.get(tClass);
    if (classes != null) {
      for (Class<?> aClass : classes) {
        boolean add = loadProcess.beforeAdd(tClass, classList, sort);
        if (add) {
          classList.add((Class<T>) aClass);
        }
      }
    }
    if (sort) {
      classList = toSort(classList);
    }
    CLASS_CACHE.put(tClass, classList.stream().collect(Collectors.toList()));
  }

  /**
   * 加载并处理
   *
   * @author liaojinlong
   * @since 2021/9/6 22:38
   */
  public interface LoadProcess<T> {
    /**
     * 初步加载试
     *
     * @param iterator
     * @return /
     */
    default List<Class<T>> initLoader(Iterator<T> iterator) {
      final List<Class<T>> tmp = new ArrayList<>(8);
      iterator.forEachRemaining(t -> {
        tmp.add((Class<T>) t.getClass());
      });
      List<Class<T>> classList = toSort(tmp);
      return classList;
    }

    /**
     * 过滤排序后
     *
     * @param collection
     * @return /
     */

    default List<Class<T>> filter(List<Class<T>> collection) {
      return toSort(collection);
    }

    /**
     * 添加前
     *
     * @param tClass
     * @param classList
     * @param sort
     * @return /
     */
    default boolean beforeAdd(Class<T> tClass, List<Class<T>> classList, boolean sort) {
      return true;
    }
  }

}
