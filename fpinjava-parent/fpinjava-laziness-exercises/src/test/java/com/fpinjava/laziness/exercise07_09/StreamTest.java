package com.fpinjava.laziness.exercise07_09;

import static org.junit.Assert.*;

import org.junit.Test;


public class StreamTest {

  @Test
  public void testFrom() {
    Stream<Integer> stream = Stream.from(5);
    Stream<Integer> stream2 = stream.take(5).map(x -> x * 2);
    Stream<Integer> stream3 = stream.map(x -> x + 2).take(5);
    assertEquals("[10, 12, 14, 16, 18, NIL]", stream2.toString());
    assertEquals("[7, 8, 9, 10, 11, NIL]", stream3.toString());
    assertEquals("[5, 6, 7, 8, 9, NIL]", Stream.from(5).take(5).toString());
  }

}
