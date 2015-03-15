package com.fpinjava.recursion.exercise04_01;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

public class FibonacciTest {

  @Test
  public void testFib() {
    assertEquals(BigInteger.valueOf(0), Fibonacci.fib(0));
    assertEquals(BigInteger.valueOf(1), Fibonacci.fib(1));
    assertEquals(BigInteger.valueOf(1), Fibonacci.fib(2));
    assertEquals(BigInteger.valueOf(5), Fibonacci.fib(5));
    assertEquals(BigInteger.valueOf(55), Fibonacci.fib(10));
    assertEquals(new BigInteger("354224848179261915075"), Fibonacci.fib(100));
  }

}
