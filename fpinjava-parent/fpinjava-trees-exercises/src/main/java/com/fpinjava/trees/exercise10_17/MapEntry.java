package com.fpinjava.trees.exercise10_17;


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
    throw new IllegalStateException("To be implemented");
  }

  @Override
  public boolean equals(Object o) {
    throw new IllegalStateException("To be implemented");
  }

  @Override
  public int hashCode() {
    throw new IllegalStateException("To be implemented");
  }

  public static <K extends Comparable<K>, V> MapEntry<K, V> mapEntry(K key, V value) {
    throw new IllegalStateException("To be implemented");
  }

  public static <K extends Comparable<K>, V> MapEntry<K, V> mapEntry(K key) {
    throw new IllegalStateException("To be implemented");
  }
}
