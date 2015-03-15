package com.fpinjava.functions.exercise02_07;

import static org.junit.Assert.*;

import org.junit.Test;

public class FunctionExamplesTest {

  @Test
  public void testPartialA() {
    Function<Integer, Function<Double, Double>> f = a -> b -> a * (1 + b / 100);
    Function<Double, Double> g = FunctionExamples.partialA(89, f);    

    assertEquals(f.apply(89).apply(7.0), g.apply(7.0));
    assertEquals(f.apply(89).apply(0.0), g.apply(0.0));
    assertEquals(f.apply(89).apply(16.65), g.apply(16.65));
  }

}
