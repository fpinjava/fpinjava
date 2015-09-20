package com.fpinjava.laziness.exercise09_16;

import org.junit.Test;

import static org.junit.Assert.*;


public class StreamTest {

  @Test
  public void testFrom() {
    Stream<Integer> stream = Stream.from(2);
    assertEquals(Integer.valueOf(14), stream.take(4).toList().foldRight(0, x -> y -> x + y));
  }

  @Test
  public void testRepeat() {
    Stream<Integer> stream = Stream.repeat(2);
    assertEquals(Integer.valueOf(8), stream.take(4).toList().foldRight(0, x -> y -> x + y));
  }
}
