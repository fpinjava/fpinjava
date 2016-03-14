package com.fpinjava.laziness.exercise09_02;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class StreamBenchmark {

  @Test
  public void testToListRecursive() {
    int size = 500_000;
    Stream<Integer> stream = Stream.empty();
    for (int i = size; i > 0; i--) {
      int j = i;
      stream = Stream.cons(() -> j, stream);
    }
    for(int i = 0; i < 5; i++) {
      assertTrue(stream.toList().toString().startsWith("[1, 2, 3, 4, 5, 6, 7, 8, 9"));
    }
    long time = System.currentTimeMillis();
    for(int i = 0; i < 5; i++) {
      assertTrue(stream.toList().toString().startsWith("[1, 2, 3, 4, 5, 6, 7, 8, 9"));
    }
    System.out.println(System.currentTimeMillis() - time);
  }

  @Test
  public void testToListIterative() {
    int size = 500_000;
    Stream<Integer> stream = Stream.empty();
    for (int i = size; i > 0; i--) {
      int j = i;
      stream = Stream.cons(() -> j, stream);
    }
    for(int i = 0; i < 5; i++) {
      assertTrue(stream.toListIterative().toString().startsWith("[1, 2, 3, 4, 5, 6, 7, 8, 9"));
    }
    long time = System.currentTimeMillis();
    for(int i = 0; i < 5; i++) {
      assertTrue(stream.toListIterative().toString().startsWith("[1, 2, 3, 4, 5, 6, 7, 8, 9"));
    }
    System.out.println(System.currentTimeMillis() - time);
  }
}
