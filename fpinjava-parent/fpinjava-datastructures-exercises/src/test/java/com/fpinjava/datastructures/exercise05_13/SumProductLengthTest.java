package com.fpinjava.datastructures.exercise05_13;

import static com.fpinjava.datastructures.exercise05_12.List.list;
import static com.fpinjava.datastructures.exercise05_13.SumProductLength.*;
import static org.junit.Assert.*;

import org.junit.Test;


public class SumProductLengthTest {

  @Test
  public void testSumViaFoldLeft() {
    assertEquals(Integer.valueOf(0), sumViaFoldLeft(list()));
    assertEquals(Integer.valueOf(6), sumViaFoldLeft(list(1, 2, 3)));
  }

  @Test
  public void testProductViaFoldLeft() {
    assertEquals(Double.valueOf(1.0), productViaFoldLeft(list()));
    assertEquals(Double.valueOf(24.0), productViaFoldLeft(list(1.0, 2.0, 3.0, 4.0)));
  }

  @Test
  public void testLengthVaFoldLeft() {
    assertEquals(Integer.valueOf(0), lengthVaFoldLeft(list()));
    assertEquals(Integer.valueOf(4), lengthVaFoldLeft(list(1.0, 2.0, 3.0, 4.0)));
  }

}
