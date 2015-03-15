package com.fpinjava.functions.exercise02_11;

import static org.junit.Assert.*;

import org.junit.Test;

public class FunctionExamplesTest {

  @Test
  public void testReverseArgs() {
    Function<Integer, Function<Double, Double>> f = a -> b -> a * (1 + b / 100);
    Function<Double, Function<Integer, Double>> g = FunctionExamples.reverseArgs(f);
    assertEquals(f.apply(89).apply(7.0), g.apply(7.0).apply(89));
    assertEquals(f.apply(27).apply(0.0), g.apply(0.0).apply(27));
    assertEquals(f.apply(1623).apply(16.65), g.apply(16.65).apply(1623));
  }
}
