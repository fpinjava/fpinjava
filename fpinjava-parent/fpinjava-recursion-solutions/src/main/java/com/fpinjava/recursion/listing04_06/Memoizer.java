package com.fpinjava.recursion.listing04_06;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fpinjava.common.Function;

public class Memoizer<T, U> {

  private final Map<T, U> cache = new ConcurrentHashMap<>();

  private Memoizer() {}

  public static <T, U> Function<T, U> memoize(Function<T, U> function) {
    return new Memoizer<T, U>().doMemoize(function);
  }

  private Function<T, U> doMemoize(Function<T, U> function) {
    return input -> cache.computeIfAbsent(input, function::apply);
  }
}