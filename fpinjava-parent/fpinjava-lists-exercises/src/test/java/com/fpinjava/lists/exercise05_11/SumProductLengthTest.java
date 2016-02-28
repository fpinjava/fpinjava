package com.fpinjava.lists.exercise05_11;

import static com.fpinjava.lists.exercise05_10.List.list;
import static com.fpinjava.lists.exercise05_11.SumProductLength.*;
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
    assertEquals(Integer.valueOf(0), lengthViaFoldLeft(list()));
    assertEquals(Integer.valueOf(4), lengthViaFoldLeft(list(1.0, 2.0, 3.0, 4.0)));
  }

}
