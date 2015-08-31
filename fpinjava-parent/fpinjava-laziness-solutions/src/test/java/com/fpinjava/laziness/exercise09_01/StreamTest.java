package com.fpinjava.laziness.exercise09_01;

import org.junit.Test;

import static org.junit.Assert.*;


public class StreamTest {

  @Test
  public void testHeadOption() throws Exception {
    Stream<Integer> stream = Stream.cons(1, 2, 3, 4, 5);
    assertEquals("Success(1)", stream.headOption().toString());
  }

  @Test
  public void testHeadOptionEmpty() throws Exception {
    Stream<Integer> stream = Stream.cons();
    assertEquals("Empty()", stream.headOption().toString());
  }
}
