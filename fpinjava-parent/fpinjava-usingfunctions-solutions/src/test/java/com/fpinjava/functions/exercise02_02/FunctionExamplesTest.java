package com.fpinjava.functions.exercise02_02;

import static com.fpinjava.functions.exercise02_02.FunctionExamples.compose;
import static com.fpinjava.functions.exercise02_02.FunctionExamples.square;
import static com.fpinjava.functions.exercise02_02.FunctionExamples.triple;
import static org.junit.Assert.*;

import org.junit.Test;

public class FunctionExamplesTest {

  @Test
  public void testCompose() {
    assertEquals(Integer.valueOf(6), triple.apply(2));
    assertEquals(Integer.valueOf(4), square.apply(2));
    assertEquals(Integer.valueOf(36), square.apply(triple.apply(2)));
    assertEquals(Integer.valueOf(27), compose(triple, square).apply(3));
  }

}
