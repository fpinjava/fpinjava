package com.fpinjava.functionaparallelism.exercise01;

import static org.junit.Assert.*;


import org.junit.Test;

import com.fpinjava.common.List;


public class ParTest {

  @Test
  public void testSum_() {
    assertEquals(Integer.valueOf(0), Par.sum_(List.list()));
    assertEquals(Integer.valueOf(1), Par.sum_(List.list(1)));
    assertEquals(Integer.valueOf(3), Par.sum_(List.list(1, 2)));
    assertEquals(Integer.valueOf(6), Par.sum_(List.list(1, 2, 3)));
    assertEquals(Integer.valueOf(10), Par.sum_(List.list(1, 2, 3, 4)));
    assertEquals(Integer.valueOf(15), Par.sum_(List.list(1, 2, 3, 4, 5)));
  }

  @Test
  public void testSum() {
    assertEquals(Integer.valueOf(0), Par.get(Par.sum(List.list())));
    assertEquals(Integer.valueOf(1), Par.get(Par.sum(List.list(1))));
    assertEquals(Integer.valueOf(3), Par.get(Par.sum(List.list(1, 2))));
    assertEquals(Integer.valueOf(6), Par.get(Par.sum(List.list(1, 2, 3))));
    assertEquals(Integer.valueOf(10), Par.get(Par.sum(List.list(1, 2, 3, 4))));
    assertEquals(Integer.valueOf(15), Par.get(Par.sum(List.list(1, 2, 3, 4, 5))));
  }

}
