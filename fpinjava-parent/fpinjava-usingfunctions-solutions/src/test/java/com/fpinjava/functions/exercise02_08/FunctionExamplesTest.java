package com.fpinjava.functions.exercise02_08;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class FunctionExamplesTest {

  @Test
  public void testPartialB() {
    Function<Integer, Function<Double, Double>> f = a -> b -> a * (1 + b / 100);
    Function<Integer, Double> g = FunctionExamples.partialB(16.65, f);

    assertEquals(f.apply(89).apply(16.65), g.apply(89));
    assertEquals(f.apply(0).apply(16.65), g.apply(0));
    assertEquals(f.apply(1623).apply(16.65), g.apply(1623));
  }

}
