package com.fpinjava.handlingerrors.listing07_07_08_09;

import com.fpinjava.handlingerrors.listing07_06.Result;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Map<T, U> {

  private final ConcurrentMap<T, U> map = new ConcurrentHashMap<>();

  public static <T, U> Map<T, U> empty() {
    return new Map<>();
  }

  public static <T, U> Map<T, U> add(Map<T, U> m, T t, U u) {
    m.map.put(t, u);
    return m;
  }

  public Result<U> get(final T t) {
    return this.map.containsKey(t)
        ? Result.success(this.map.get(t))
        : Result.empty();
  }

  public Map<T, U> put(T t, U u) {
    return add(this, t, u);
  }

  public Map<T, U> removeKey(T t) {
    this.map.remove(t);
    return this;
  }
}
