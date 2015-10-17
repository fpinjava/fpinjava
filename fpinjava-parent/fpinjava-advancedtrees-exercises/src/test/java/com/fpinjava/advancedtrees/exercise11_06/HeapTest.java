package com.fpinjava.advancedtrees.exercise11_06;

import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;


public class HeapTest {

  @Test
  public void testAdd() throws Exception {
    List<Integer> list = List.list(1, 2, 3, 4, 5, 6, 7);
    Heap<Integer> queue = list.foldLeft(Heap.<Integer>empty(), h -> h::add);
    queue.head().map(a -> a == 1).forEachOrThrow(Assert::assertTrue);
    assertTrue(isBalanced(queue));
    assertTrue(isValueOrdered(queue));
  }

  @Test
  public void testAdd2() throws Exception {
    List<Integer> list = List.list(7, 3, 1, 6, 4, 6, 2);
    Heap<Integer> queue = list.foldLeft(Heap.<Integer>empty(), h -> h::add);
    queue.head().map(a -> a == 1).forEachOrThrow(Assert::assertTrue);
    assertTrue(isBalanced(queue));
    assertTrue(isValueOrdered(queue));
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
    return isValueOrdered_(heap) && heap.tail().map(HeapTest::isValueOrdered_).getOrElse(true);
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
