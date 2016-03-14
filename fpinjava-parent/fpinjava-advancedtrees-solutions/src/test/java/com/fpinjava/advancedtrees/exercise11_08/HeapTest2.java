package com.fpinjava.advancedtrees.exercise11_08;

import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import com.fpinjava.common.Tuple;
import com.fpinjava.state.SimpleRNG;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class HeapTest2 {

  static int testLimit = 20_000;

  static Comparator<Number> comparator = (n1, n2) -> n1.value < n2.value ? -1 : n1.value > n2.value ? 1 : 0;

  static final List<Integer> randomTestList = SimpleRNG.doubles(testLimit, new SimpleRNG.Simple(3))._1.map(x -> (int) (x * 30));
  static final List<Integer> orderedTestList = List.range(0, testLimit);

  static final Heap<Number> orderedHeap = orderedTestList.foldLeft(Heap.empty(comparator), h -> i -> h.add(number(i)));
  static final Heap<Number> randomHeap = randomTestList.foldLeft(Heap.empty(comparator), h -> i -> h.add(number(i)));

  @Test
  public void testInsertOrderedAscending7() {
    int limit = 7;
    List<Integer> list = List.range(1, limit + 1);
    Heap<Number> heap = list.foldLeft(Heap.empty(comparator), h -> i -> h.add(number(i)));
    assertEquals(limit, heap.length());
  }

  @Test
  public void testInsertOrderedDescending7() {
    int limit = 7;
    List<Integer> list = List.iterate(limit, x -> x - 1, limit);
    Heap<Number> heap = list.foldLeft(Heap.empty(comparator), h -> i -> h.add(number(i)));
    assertEquals(limit, heap.length());
    assertTrue(isBalanced(heap));
    assertTrue(isValueOrdered(heap, comparator));
  }

  @Test
  public void testInsertRandom7() {
    List<Integer> list = List.list(2, 5, 7, 3, 6, 1, 4);
    Heap<Number> heap = list.foldLeft(Heap.empty(comparator), h -> i -> h.add(number(i)));
    assertTrue(heap.height() <= 2 * log2nlz(heap.length() + 1));
  }

  @Test
  public void testInsertOrderedAscending() {
    int limit = 20_000;
    List<Integer> list = List.range(1, limit + 1);
    Heap<Number> heap = list.foldLeft(Heap.empty(comparator), h -> i -> h.add(number(i)));
    assertEquals(limit, heap.length());
    assertTrue(isBalanced(heap));
    assertTrue(isValueOrdered(heap, comparator));
    Heap<Number> heap2 = list.foldLeft(heap, t ->  e -> t.tail().getOrElse(t));
    assertTrue(heap2.isEmpty());
  }

  @Test
  public void testInsertOrderedDescending() {
    int limit = 20_000;
    List<Integer> list = List.iterate(limit, x -> x - 1, limit);
    Heap<Number> heap = list.foldLeft(Heap.empty(comparator), h -> i -> h.add(number(i)));
    assertEquals(limit, heap.length());
    assertTrue(isBalanced(heap));
    assertTrue(isValueOrdered(heap, comparator));
    Heap<Number> heap2 = list.foldLeft(heap, t ->  e -> t.tail().getOrElse(t));
    assertTrue(heap2.isEmpty());
  }

  @Test
  public void testInsertRandom() {
    int limit = 20_000;
    List<Integer> list = SimpleRNG.doubles(limit, new SimpleRNG.Simple(3))._1.map(x -> (int) (x * limit * 3));
    Heap<Number> heap = list.foldLeft(Heap.empty(comparator), h -> i -> h.add(number(i)));
    assertTrue(isBalanced(heap));
    assertTrue(isValueOrdered(heap, comparator));
    Heap<Number> heap2 = list.foldLeft(heap, t ->  e -> t.tail().getOrElse(t));
    assertTrue(heap2.isEmpty());
  }

  @Test
  public void testRemoveAllOrdered() {
    assertTrue(orderedTestList.foldLeft(orderedHeap, t ->  e -> t.tail().getOrElse(t)).isEmpty());
  }

  @Test
  public void testRemoveAllRandom() {
    assertTrue(randomTestList.foldLeft(randomHeap, t ->  e -> t.tail().getOrElse(t)).isEmpty());
  }

  @Test
  public void testRemoveHalfOrdered() {
    Tuple<List<Integer>, List<Integer>> lists = orderedTestList.splitAt(orderedTestList.length() / 2);
    List<Integer> list1 = lists._1;
    List<Integer> list2 = lists._2;
    Heap<Number> h1 = list1.foldLeft(orderedHeap, t ->  e -> t.tail().getOrElse(t));
    assertEquals(list2.length(), h1.length());
    assertTrue(isBalanced(h1));
    assertTrue(isValueOrdered(h1, comparator));
    Heap<Number> h2 = list1.foldLeft(h1, h -> i -> h.add(number(i)));
    assertEquals(orderedTestList.length(), h2.length());
    assertTrue(isBalanced(h2));
    assertTrue(isValueOrdered(h2, comparator));
  }

  @Test
  public void testRemoveHalfRandom() {
    assertTrue(randomTestList.foldLeft(randomHeap, t ->  e -> t.tail().getOrElse(t)).isEmpty());
  }

  private static <A> boolean isBalanced(Heap<A> heap) {
    return rightSpine(heap) <= log2nlz(heap.length() + 1);
  }

  private static <A> int rightSpine(Heap<A> heap) {
    return 1 + rightSpine_(heap.right());
  }
  private static <A> int rightSpine_(Result<Heap<A>> heap) {
    return heap.map(t -> t.isEmpty() ? -1 : 1 + rightSpine_(t.right())).getOrElse(-1);
  }

  private static <A> boolean isValueOrdered(Heap<A> heap, Comparator<A> comparator) {
    return isValueOrdered_(heap, comparator) && heap.tail().map(h -> isValueOrdered_(h, comparator)).getOrElse(true);
  }

  public static int log2nlz(int n) {
    return n == 0
        ? 0
        : 31 - Integer.numberOfLeadingZeros(n);
  }

  private static <A> boolean isValueOrdered_(Heap<A> heap, Comparator<A> comparator) {
    return heap.head().flatMap(t1 -> heap.tail().flatMap(tail -> tail.head().map(t2 -> comparator.compare(t1, t2) <= 0))).getOrElse(true);
  }

  private static Number number(int num) {
    return new Number(num);
  }

  private static class Number {
    public final int value;

    private Number(int value) {
      this.value = value;
    }

    public String toString() {
      return Integer.toString(value);
    }
  }

}
