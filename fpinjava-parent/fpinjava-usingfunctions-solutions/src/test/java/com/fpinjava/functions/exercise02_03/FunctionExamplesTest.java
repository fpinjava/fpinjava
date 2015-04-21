package com.fpinjava.functions.exercise02_03;

import org.junit.Test;

import static com.fpinjava.functions.exercise02_03.FunctionExamples.*;
import static org.junit.Assert.assertEquals;

public class FunctionExamplesTest {

  @Test
  public void test() {
    assertEquals(Integer.valueOf(8), add.apply(3).apply(5));
    assertEquals(Integer.valueOf(8), addOp.apply(3).apply(5));
    assertEquals(Integer.valueOf(15), multOp.apply(3).apply(5));
  }

}
