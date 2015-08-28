package com.fpinjava.optionaldata.listing06_02;

import com.fpinjava.optionaldata.exercise06_06.Option;

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

  public Option<U> get(final T t) {
    return this.map.containsKey(t)
        ? Option.some(this.map.get(t))
        : Option.none();
  }

  public Map<T, U> put(T t, U u) {
    return add(this, t, u);
  }

  public Map<T, U> removeKey(T t) {
    this.map.remove(t);
    return this;
  }
}
