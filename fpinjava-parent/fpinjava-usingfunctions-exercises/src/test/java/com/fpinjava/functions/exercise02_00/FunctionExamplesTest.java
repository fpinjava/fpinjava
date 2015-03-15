package com.fpinjava.functions.exercise02_00;

import static org.junit.Assert.*;
import static com.fpinjava.functions.exercise02_00.FunctionExamples.*;

import org.junit.Test;

public class FunctionExamplesTest {

  @Test
  public void testCompose() {
    assertEquals(6, triple.apply(2));
    assertEquals(4, square.apply(2));
    assertEquals(36, square.apply(triple.apply(2)));
    assertEquals(27, compose(triple, square).apply(3));
  }
}
