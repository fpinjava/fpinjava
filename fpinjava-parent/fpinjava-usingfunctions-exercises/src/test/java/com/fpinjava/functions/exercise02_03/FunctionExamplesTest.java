package com.fpinjava.functions.exercise02_03;

import static com.fpinjava.functions.exercise02_03.FunctionExamples.add;
import static com.fpinjava.functions.exercise02_03.FunctionExamples.add2;
import static com.fpinjava.functions.exercise02_03.FunctionExamples.mult;
import static org.junit.Assert.*;

import org.junit.Test;

public class FunctionExamplesTest {

  @Test
  public void test() {
    assertEquals(Integer.valueOf(8), add.apply(3).apply(5));
    assertEquals(Integer.valueOf(8), add2.apply(3).apply(5));
    assertEquals(Integer.valueOf(15), mult.apply(3).apply(5));
  }

}
