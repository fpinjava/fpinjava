package com.fpinjava.advancedtrees.exercise11_04;


import com.fpinjava.common.Result;

public class MapEntry<K, V> implements Comparable<MapEntry<K, V>> {
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
  public int compareTo(MapEntry<K, V> that) {
    int thisHashCode = this.hashCode();
    int thatHashCode = that.hashCode();
    return thisHashCode < thatHashCode
        ? -1
        : thisHashCode > thatHashCode
            ? 1
            : 0;
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof MapEntry && this.key.equals(((MapEntry) o).key);
  }

  @Override
  public int hashCode() {
    return key.hashCode();
  }

  public static <K, V> MapEntry<K, V> mapEntry(K key, V value) {
    return new MapEntry<>(key, Result.success(value));
  }

  public static <K, V> MapEntry<K, V> mapEntry(K key) {
    return new MapEntry<>(key, Result.empty());
  }
}
