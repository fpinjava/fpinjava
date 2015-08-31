package com.fpinjava.laziness.exercise09_02;

import org.junit.Test;

import static org.junit.Assert.*;


public class StreamTest {

  @Test
  public void testToList() {
    Stream<Integer> stream = Stream.cons(1, 2, 3, 4, 5);
    assertEquals("[1, 2, 3, 4, 5, NIL]", stream.toList().toString());
  }

  @Test
  public void testToListEmpty() {
    assertEquals("[NIL]", Stream.cons().toList().toString());
  }

}
