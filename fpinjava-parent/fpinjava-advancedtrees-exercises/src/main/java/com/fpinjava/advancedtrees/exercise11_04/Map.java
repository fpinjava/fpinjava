package com.fpinjava.advancedtrees.exercise11_04;


import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import com.fpinjava.common.Tuple;


public class Map<K, V> {

  protected final Tree<MapEntry<Integer, List<Tuple<K, V>>>> delegate;

  private Map() {
    this.delegate = Tree.empty();
  }

  public Map(Tree<MapEntry<Integer, List<Tuple<K, V>>>> delegate) {
    this.delegate = delegate;
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

  public Result<Tuple<K, V>> get(K key) {
    throw new IllegalStateException("To be implemented");
  }

  public boolean isEmpty() {
    return delegate.isEmpty();
  }

  public static <K, V> Map<K, V> empty() {
    return new Map<>();
  }

  @Override
  public String toString() {
    return String.format("Map[%s]", this.delegate);
  }
}
