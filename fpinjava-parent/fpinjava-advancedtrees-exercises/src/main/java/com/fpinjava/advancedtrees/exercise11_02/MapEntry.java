package com.fpinjava.advancedtrees.exercise11_02;


import com.fpinjava.common.Result;

public class MapEntry<K extends Comparable<K>, V> implements Comparable<MapEntry<K, V>> {
  public final K key;
  public final Result<V> value;

  private MapEntry(K key, Result<V> value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public String toString() {
    return String.format("MapEntry(%s, %s)", key, value);
  }

  @Override
  public int compareTo(MapEntry<K, V> me) {
    return this.key.compareTo(me.key);
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof MapEntry && this.key.equals(((MapEntry) o).key);
  }

  @Override
  public int hashCode() {
    return key.hashCode();
  }

  public static <K extends Comparable<K>, V> MapEntry<K, V> mapEntry(K key, V value) {
    return new MapEntry<>(key, Result.success(value));
  }

  public static <K extends Comparable<K>, V> MapEntry<K, V> mapEntry(K key) {
    return new MapEntry<>(key, Result.empty());
  }
}
