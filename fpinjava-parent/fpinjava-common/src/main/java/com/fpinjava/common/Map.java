package com.fpinjava.common;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

public class Map<T, U> {

  private static String NULL_KEY = "Map key can't be null";
  private static String NULL_VALUE = "Map value can't be null";

  private final ConcurrentMap<T, U> map = new ConcurrentHashMap<>();

  public static <T, U> Map<T, U> empty() {
    return new Map<>();
  }

  public static <T, U> Map<T, U> add(Map<T, U> m, T t, U u) {
    Objects.requireNonNull(t, NULL_KEY);
    Objects.requireNonNull(u, NULL_VALUE);
    m.map.put(t, u);
    return m;
  }

  public Result<U> get(final T t) {
    return t == null
        ? Result.failure(NULL_KEY, new IllegalArgumentException(NULL_KEY))
        : Result.of(() -> this.map.get(t), String.format("Key %s not found in map", t));
  }

  public boolean containsKey(final T t) {
    return this.map.containsKey(t);
  }

  public Map<T, U> put(Tuple<T, U> e) {
    return put(e._1, e._2);
  }

  public Map<T, U> put(T t, U u) {
    return add(this, t, u);
  }

  public Map<T, U> replace(Tuple<T, U> e) {
    return put(e);
  }

  public Map<T, U> removeKey(T t) {
    this.map.remove(t);
    return this;
  }

  public List<T> keys() {
    return List.fromCollection(this.map.keySet());
  }

  public List<U> values() {
    return List.fromCollection(this.map.values());
  }

  public int size() {
    return this.map.size();
  }

  public void foreach(Consumer<Tuple<T, U>> c) {
    this.map.entrySet().forEach(e -> c.accept(new Tuple<>(e.getKey(), e.getValue())));
  }

}
