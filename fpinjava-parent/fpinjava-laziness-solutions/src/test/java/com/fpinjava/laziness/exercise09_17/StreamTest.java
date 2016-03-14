package com.fpinjava.laziness.exercise09_17;

import org.junit.Test;

import static org.junit.Assert.*;


public class StreamTest {

  @Test
  public void testFibs() throws Exception {
    Stream<Integer> stream = Stream.fibs().take(25);
    assertEquals("[0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, NIL]", stream.toList().toString());
  }
}
