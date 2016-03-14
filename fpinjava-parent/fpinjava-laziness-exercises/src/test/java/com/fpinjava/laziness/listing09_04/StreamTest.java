package com.fpinjava.laziness.listing09_04;

import org.junit.Test;

import static org.junit.Assert.*;


public class StreamTest {

  @Test
  public void testStream() {
    Stream<Integer> stream = Stream.from(1);
    assertEquals(Integer.valueOf(1), stream.head());
    assertEquals(Integer.valueOf(2), stream.tail().head());
    assertEquals(Integer.valueOf(3), stream.tail().tail().head());
  }
}
