package com.fpinjava.trees.redblacktree.one;


import com.fpinjava.common.Result;

@SuppressWarnings("unchecked")
public abstract class Map<K extends Comparable, V> {

  @SuppressWarnings("rawtypes")
  private static Map EMPTY = new Empty();

  public abstract Map<K, V> put(K key, V value);

  public abstract boolean containsKey(K key);

  public abstract Result<V> get(K key);

  private static class Empty<K extends Comparable, V> extends Map<K, V> {

    @Override
    public Map<K, V> put(K key, V value) {
      return new Tree<>(empty(), new MapEntry(key, value), empty());
    }

    @Override
    public boolean containsKey(K key) {
      return false;
    }

    @Override
    public Result<V> get(K key) {
      return Result.failure("empty");
    }
  }

  private static class Tree<K extends Comparable, V> extends Map<K, V> {
    private final Map<K, V> left;
    private final Map<K, V> right;
    private final MapEntry<K, V> entry;


    private Tree(Map<K, V> left, MapEntry<K, V> entry, Map<K, V> right) {
      this.left = left;
      this.right = right;
      this.entry = entry;
    }

    @Override
    public Map<K, V> put(K key, V value) {
      return key.compareTo(this.entry.key) < 0
          ? new Tree<>(left.put(key, value), entry, right)
          : key.compareTo(this.entry.key) > 0
              ? new Tree<>(left, entry, right.put(key, value))
              : this;
    }

    @Override
    public boolean containsKey(K key) {
      return key.compareTo(this.entry.key) < 0
          ? left.containsKey(key)
          : key.compareTo(this.entry.key) > 0
              ? right.containsKey(key)
              : true;
    }

    @Override
    public Result<V> get(K key) {
      return key.compareTo(this.entry.key) < 0
          ? left.get(key)
          : key.compareTo(this.entry.key) > 0
              ? right.get(key)
              : Result.success(this.entry.value);
    }
  }

  private static class MapEntry<K, V> {
    public final K key;
    public final V value;

    public MapEntry(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }

  @SuppressWarnings("unchecked")
  public static <K extends Comparable, V> Map<K, V> empty() {
    return EMPTY;
  }

  @SuppressWarnings("unchecked")
  public static <K extends Comparable, V> Map<K, V> apply(K key, V value) {
    return new Tree<>(EMPTY, new MapEntry<>(key, value), EMPTY);
  }
}
