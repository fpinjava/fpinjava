package com.fpinjava.functions.exercise02_06;

import static org.junit.Assert.*;

import org.junit.Test;

public class FunctionTest {

  public static final Function<Integer, Integer> triple = x -> x * 3;

  public static final Function<Integer, Integer> square = x -> x * x;
  Function<Double, Integer> f = a -> (int) (a * 3);
  Function<Long, Double> g = a -> a + 2.0;

  @Test
  public void test() {
    assertEquals(Integer.valueOf(12), Function.<Integer, Integer, Integer>higherAndThen().apply(square).apply(triple).apply(2));

    assertEquals(Integer.valueOf(9), f.apply((g.apply(1L))));
    assertEquals(Integer.valueOf(9),
            Function.<Long, Double, Integer>higherCompose().apply(f).apply(g).apply(1L));
  }

}
