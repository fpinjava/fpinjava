package com.fpinjava.trees.exercise10_17;


import com.fpinjava.common.Function;
import com.fpinjava.common.Result;

import static com.fpinjava.trees.exercise10_17.MapEntry.mapEntry;


public class Map<K extends Comparable<K>, V> {

  private Map() {
    throw new IllegalStateException("To be implemented");
  }

  private Map(Tree<MapEntry<K, V>> delegate) {
    throw new IllegalStateException("To be implemented");
  }

  public Map<K, V> add(K key, V value) {
    throw new IllegalStateException("To be implemented");
  }

  public boolean contains(K key) {
    throw new IllegalStateException("To be implemented");
  }

  public Map<K, V> remove(K key) {
    throw new IllegalStateException("To be implemented");
  }

  public MapEntry<K, V> max() {
    throw new IllegalStateException("To be implemented");
  }

  public MapEntry<K, V> min() {
    throw new IllegalStateException("To be implemented");
  }

  public Result<MapEntry<K, V>> get(K key) {
    throw new IllegalStateException("To be implemented");
  }

  public boolean isEmpty() {
    throw new IllegalStateException("To be implemented");
  }

  public <B> B fold(B identity, Function<B, Function<MapEntry<K, V>, B>> f, Function<B, Function<B, B>> g) {
    throw new IllegalStateException("To be implemented");
  }

  public static <K extends Comparable<K>, V> Map<K, V> empty() {
    return new Map<>();
  }
}
