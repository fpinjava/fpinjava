package com.fpinjava.advancedtrees.exercise11_07;

import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;
import org.junit.Assert;
import org.junit.Test;


public class HeapTest {

  @Test
  public void testAdd() throws Exception {
    List<Integer> list = List.list(1, 2, 3, 4, 5, 6, 7);
    Heap<Integer> queue = list.foldLeft(Heap.<Integer>empty(), h -> h::add);
    list.zipWithPositionResult().forEachOrThrow(testList -> List.sequence(testList.map(t -> queue.get(t._2).map(v -> v.equals(t._1)))).map(lst -> lst.forAll(x -> x)).forEachOrThrow(Assert::assertTrue));
  }

  @Test
  public void testAdd2() throws Exception {
    List<Integer> list = List.list(7, 3, 1, 6, 4, 6, 2);
    Heap<Integer> queue = list.foldLeft(Heap.<Integer>empty(), h -> h::add);
    List.list(1, 2, 3, 4, 6, 6, 7).zipWithPositionResult().forEachOrThrow(testList -> List.sequence(testList.map(t -> queue.get(t._2).map(v -> v.equals(t._1)))).map(lst -> lst.forAll(x -> x)).forEachOrThrow(Assert::assertTrue));
  }
}
