package com.fpinjava.advancedtrees.listing11_03;

import org.junit.Test;


import static org.junit.Assert.assertTrue;


public class HeapTest {

  @Test
  public void testIsEmpty() {
    Heap<Integer> queue = Heap.empty();
    assertTrue(queue.isEmpty());
  }
}
