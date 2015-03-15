package com.fpinjava.functionaparallelism;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;


public class ParTest {

  @Test
  public void testSum_() {
    assertEquals(Integer.valueOf(0), Par.sum(List.list()));
    assertEquals(Integer.valueOf(1), Par.sum(List.list(1)));
    assertEquals(Integer.valueOf(3), Par.sum(List.list(1, 2)));
    assertEquals(Integer.valueOf(6), Par.sum(List.list(1, 2, 3)));
    assertEquals(Integer.valueOf(10), Par.sum(List.list(1, 2, 3, 4)));
    assertEquals(Integer.valueOf(15), Par.sum(List.list(1, 2, 3, 4, 5)));
  }

}
