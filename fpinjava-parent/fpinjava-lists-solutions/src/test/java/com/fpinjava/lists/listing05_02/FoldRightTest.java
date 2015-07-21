package com.fpinjava.lists.listing05_02;

import static com.fpinjava.lists.exercise05_06.List.list;
import static com.fpinjava.lists.listing05_02.FoldRight.sum;
import static com.fpinjava.lists.listing05_02.FoldRight.product;
import static org.junit.Assert.*;

import org.junit.Test;

public class FoldRightTest {

  @Test
  public void testSum() {
    assertEquals(Integer.valueOf(0), sum(list()));
    assertEquals(Integer.valueOf(6), sum(list(1, 2, 3)));
  }

  @Test
  public void testProduct() {
    assertEquals(Double.valueOf(1.0), product(list()));
    assertEquals(Double.valueOf(24.0), product(list(1.0, 2.0, 3.0, 4.0)));
  }

}
