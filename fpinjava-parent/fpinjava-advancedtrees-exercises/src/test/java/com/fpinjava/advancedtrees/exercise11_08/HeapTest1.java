package com.fpinjava.advancedtrees.exercise11_08;

import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import com.fpinjava.common.Tuple;
import com.fpinjava.state.SimpleRNG;
import org.junit.Test;

import static org.junit.Assert.*;


public class HeapTest1 {

  /*
   * Adjust according to your environment. The faster the computer,
   * the lower this value should be.
   */
  int timeFactor = 500;

  static int testLimit = 20_000;

  static final List<Integer> randomTestList = SimpleRNG.doubles(testLimit, new SimpleRNG.Simple(3))._1.map(x -> (int) (x * 30));
  static final List<Integer> orderedTestList = List.range(0, testLimit);

  static final Heap<Integer> orderedHeap = orderedTestList.foldLeft(Heap.empty(), h -> h::add);
  static final Heap<Integer> randomHeap = randomTestList.foldLeft(Heap.empty(), h -> h::add);

  @Test
  public void testInsertOrderedAscending7() {
    int limit = 7;
    List<Integer> list = List.range(1, limit + 1);
    Heap<Integer> heap = list.foldLeft(Heap.<Integer>empty(), h -> h::add);
    assertEquals(limit, heap.length());
  }

  @Test
  public void testInsertOrderedDescending7() {
    int limit = 7;
    List<Integer> list = List.iterate(limit, x -> x - 1, limit);
    Heap<Integer> heap = list.foldLeft(Heap.<Integer>empty(), h -> h::add);
    assertEquals(limit, heap.length());
    assertTrue(isBalanced(heap));
    assertTrue(isValueOrdered(heap));
  }

  @Test
  public void testInsertRandom7() {
    int limit = 7;
    List<Integer> list = List.list(2, 5, 7, 3, 6, 1, 4);
    Heap<Integer> heap = list.foldLeft(Heap.<Integer>empty(), h -> h::add);
    assertEquals(limit, heap.length());
    assertTrue(isBalanced(heap));
    assertTrue(isValueOrdered(heap));
  }

  @Test
  public void testInsertOrderedAscending() {
    int limit = 20_000;
    List<Integer> list = List.range(1, limit + 1);
    Heap<Integer> heap = list.foldLeft(Heap.<Integer>empty(), h -> h::add);
    assertEquals(limit, heap.length());
    assertTrue(isBalanced(heap));
    assertTrue(isValueOrdered(heap));
    Heap<Integer> heap2 = list.foldLeft(heap, t ->  e -> t.tail().getOrElse(t));
    assertTrue(heap2.isEmpty());
  }

  @Test
  public void testInsertOrderedDescending() {
    int limit = 20_000;
    List<Integer> list = List.iterate(limit, x -> x - 1, limit);
    Heap<Integer> heap = list.foldLeft(Heap.<Integer>empty(), h -> h::add);
    assertEquals(limit, heap.length());
    assertTrue(isBalanced(heap));
    assertTrue(isValueOrdered(heap));
    Heap<Integer> heap2 = list.foldLeft(heap, t ->  e -> t.tail().getOrElse(t));
    assertTrue(heap2.isEmpty());
  }

  @Test
  public void testInsertRandom() {
    int limit = 20_000;
    List<Integer> list = SimpleRNG.doubles(limit, new SimpleRNG.Simple(3))._1.map(x -> (int) (x * limit * 3));
    Heap<Integer> heap = list.foldLeft(Heap.<Integer>empty(), h -> h::add);
    assertTrue(isBalanced(heap));
    assertTrue(isValueOrdered(heap));
    Heap<Integer> heap2 = list.foldLeft(heap, t ->  e -> t.tail().getOrElse(t));
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
    Heap<Integer> h1 = list1.foldLeft(orderedHeap, t ->  e -> t.tail().getOrElse(t));
    assertEquals(list2.length(), h1.length());
    assertTrue(isBalanced(h1));
    assertTrue(isValueOrdered(h1));
    Heap<Integer> h2 = list1.foldLeft(h1, m -> m::add);
    assertEquals(orderedTestList.length(), h2.length());
    assertTrue(isBalanced(h2));
    assertTrue(isValueOrdered(h2));
  }

  @Test
  public void testRemoveHalfRandom() {
    assertTrue(randomTestList.foldLeft(randomHeap, t ->  e -> t.tail().getOrElse(t)).isEmpty());
  }

  private static <A extends Comparable<A>> boolean isBalanced(Heap<A> heap) {
    return rightSpine(heap) <= log2nlz(heap.length() + 1);
  }

  private static <A extends Comparable<A>> int rightSpine(Heap<A> heap) {
    return 1 + rightSpine_(heap.right());
  }
  private static <A extends Comparable<A>> int rightSpine_(Result<Heap<A>> heap) {
    return heap.map(t -> t.isEmpty() ? -1 : 1 + rightSpine_(t.right())).getOrElse(-1);
  }

  private static <A extends Comparable<A>> boolean isValueOrdered(Heap<A> heap) {
    return isValueOrdered_(heap) && heap.tail().map(HeapTest1::isValueOrdered_).getOrElse(true);
  }

  public static int log2nlz(int n) {
    return n == 0
        ? 0
        : 31 - Integer.numberOfLeadingZeros(n);
  }

  private static <A extends Comparable<A>> boolean isValueOrdered_(Heap<A> heap) {
    return heap.head().flatMap(t1 -> heap.tail().flatMap(tail -> tail.head().map(t2 -> t1.compareTo(t2) <= 0))).getOrElse(true);
  }
}
