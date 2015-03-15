package com.fpinjava.functions.exercise02_05;

import static org.junit.Assert.*;

import org.junit.Test;

public class FunctionTest {

  public static final Function<Integer, Integer> triple = x -> x * 3;

  public static final Function<Integer, Integer> square = x -> x * x;

  @Test
  public void test() {
    assertEquals(Integer.valueOf(36), Function.<Integer, Integer, Integer>higherCompose().apply(square).apply(triple).apply(2));
  }
}
