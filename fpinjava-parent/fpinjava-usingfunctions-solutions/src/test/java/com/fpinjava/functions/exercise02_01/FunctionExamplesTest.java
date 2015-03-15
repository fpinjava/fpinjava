package com.fpinjava.functions.exercise02_01;

import org.junit.Test;

import static com.fpinjava.functions.exercise02_01.FunctionExamples.*;
import static org.junit.Assert.assertEquals;

public class FunctionExamplesTest {

  @Test
  public void testCompose() {
    assertEquals(Integer.valueOf(6), triple.apply(2));
    assertEquals(Integer.valueOf(4), square.apply(2));
    assertEquals(Integer.valueOf(36), square.apply(triple.apply(2)));
    assertEquals(Integer.valueOf(27), compose(triple, square).apply(3));
  }
}
