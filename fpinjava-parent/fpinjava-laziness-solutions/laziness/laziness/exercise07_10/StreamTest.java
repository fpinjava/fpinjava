package com.fpinjava.laziness.exercise07_10;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;


public class StreamTest {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @Test
  public void testFibs() {
    assertEquals("[0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, NIL]", Stream.fibs().take(20).toString());
  }

}
