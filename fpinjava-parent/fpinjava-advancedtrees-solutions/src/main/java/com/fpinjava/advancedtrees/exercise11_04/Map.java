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
    Tuple<K, V> tuple = new Tuple<>(key, value);
    List<Tuple<K, V>> ltkv = getAll(key).map(lt -> lt.foldLeft(List.list(tuple), l -> t -> t._1.equals(key) ? l : l.cons(t))).getOrElse(() -> List.list(tuple));
    return new Map<>(delegate.insert(MapEntry.mapEntry(key.hashCode(), ltkv)));
  }

  public boolean contains(K key) {
    return getAll(key).map(lt -> lt.exists(t -> t._1.equals(key))).getOrElse(false);
  }

  public Map<K, V> remove(K key) {
    List<Tuple<K, V>> ltkv = getAll(key).map(lt -> lt.foldLeft(List.<Tuple<K, V>>list(), l -> t -> t._1.equals(key) ? l : l.cons(t))).getOrElse(List::list);
    return ltkv.isEmpty()
        ? new Map<>(delegate.delete(MapEntry.mapEntry(key.hashCode())))
        : new Map<>(delegate.insert(MapEntry.mapEntry(key.hashCode(), ltkv)));
  }

  public Result<Tuple<K, V>> get(K key) {
    return getAll(key).flatMap(lt -> lt.first(t -> t._1.equals(key)));
  }

  private Result<List<Tuple<K, V>>> getAll(K key) {
    return delegate.get(MapEntry.mapEntry(key.hashCode())).flatMap(x -> x.value.map(lt -> lt.map(t -> t)));
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
