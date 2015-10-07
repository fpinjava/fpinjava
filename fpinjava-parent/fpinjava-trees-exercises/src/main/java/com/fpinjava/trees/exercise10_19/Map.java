package com.fpinjava.trees.exercise10_19;


import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import com.fpinjava.common.Tuple;


public class Map<K, V> {

  private Map() {
    throw new IllegalStateException("To be implemented");
  }

  public Map(Tree<MapEntry<Integer, List<Tuple<K, V>>>> delegate) {
    throw new IllegalStateException("To be implemented");
  }

  public Map<K, V> add(K key, V value) {
    throw new IllegalStateException("To be implemented");
  }

  public boolean contains(K key) {
    return getAll(key).map(lt -> lt.exists(t -> t._1.equals(key))).getOrElse(false);
  }

  public Map<K, V> remove(K key) {
    throw new IllegalStateException("To be implemented");
  }

  public Result<Tuple<K, V>> get(K key) {
    throw new IllegalStateException("To be implemented");
  }

  private Result<List<Tuple<K, V>>> getAll(K key) {
    throw new IllegalStateException("To be implemented");
  }

  public boolean isEmpty() {
    throw new IllegalStateException("To be implemented");
  }

  public static <K, V> Map<K, V> empty() {
    return new Map<>();
  }
}
