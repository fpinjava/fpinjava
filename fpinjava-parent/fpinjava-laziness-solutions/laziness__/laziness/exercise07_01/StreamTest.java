package com.fpinjava.laziness.exercise07_01;

import static org.junit.Assert.*;

import org.junit.Test;

public class StreamTest {

  @Test
  public void testToList() {
    Stream<Integer> stream = Stream.cons(1, 2, 3, 4, 5);
    assertEquals("[1, 2, 3, 4, 5, NIL]", stream.toList().toString());
    assertEquals("[NIL]", Stream.cons().toList().toString());
  }

}
