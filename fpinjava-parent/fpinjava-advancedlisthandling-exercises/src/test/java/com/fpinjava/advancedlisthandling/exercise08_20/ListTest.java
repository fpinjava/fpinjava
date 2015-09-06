package com.fpinjava.advancedlisthandling.exercise08_20;

import org.junit.Test;

import static org.junit.Assert.*;


public class ListTest {

  @Test
  public void testExists() throws Exception {
    List<Integer> list = List.list(1, 2, 3, 4, 5, 6, 7, 8);
    assertTrue(list.exists(x -> x < 4));
    assertFalse(list.exists(x -> x < 1));
    assertTrue(list.exists(x -> x > 0));
    assertTrue(list.exists(x -> x > 4));
  }

  @Test
  public void testExistsEmpty() throws Exception {
    List<Integer> list = List.list();
    assertFalse(list.exists(x -> x < 4));
    assertFalse(list.exists(x -> x < 1));
    assertFalse(list.exists(x -> x > 0));
    assertFalse(list.exists(x -> x > 4));
  }
}
