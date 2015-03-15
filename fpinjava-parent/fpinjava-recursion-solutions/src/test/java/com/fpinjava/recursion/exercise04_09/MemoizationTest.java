package com.fpinjava.recursion.exercise04_09;

import static org.junit.Assert.*;

import org.junit.Test;

public class MemoizationTest {

  @Test
  public void testFibo() {
    assertEquals("0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765", Memoization.fibo(20));
  }
}
