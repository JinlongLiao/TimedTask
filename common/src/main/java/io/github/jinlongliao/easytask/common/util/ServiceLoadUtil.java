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
public class ServiceLoadUtil {
  private static Map<Class<?>, List<Class<?>>> CLASS_CACHE = new ConcurrentHashMap<>(8);

  public static <T> List<Class<T>> loadClass(Class<T> tClass) {
    if (CLASS_CACHE.containsKey(tClass)) {
      List<Class<?>> classes = CLASS_CACHE.get(tClass);
      if (classes != null) {
        return classes.stream().map(t -> (Class<T>) t).collect(Collectors.toList());
      }

    }
    final List<Class<T>> tmp = new ArrayList<>(8);

    final Iterator<T> iterator = ServiceLoader.load(tClass).iterator();
    iterator.forEachRemaining(t -> {
      tmp.add((Class<T>) t.getClass());
    });
    List<Class<T>> classList = toSort(tmp);
    CLASS_CACHE.put(tClass, classList.stream().collect(Collectors.toList()));
    return classList;
  }

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

  public static <T> void addNewJob(Class<T> tClass, List<Class<T>> classList, boolean sort) {
    List<Class<?>> classes = CLASS_CACHE.get(tClass);
    if (classes != null) {
      for (Class<?> aClass : classes) {
        classList.add((Class<T>) aClass);
      }
    }
    if (sort) {
      classList = toSort(classList);
    }
    CLASS_CACHE.put(tClass, classList.stream().collect(Collectors.toList()));
  }

  public static <T> void addNewJob(Class<T> tClass, List<Class<T>> classList) {
    addNewJob(tClass, classList, false);
  }
}
