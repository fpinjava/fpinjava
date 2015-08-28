package com.fpinjava.advancedlisthandling.exercise08_16;

import org.junit.Test;

import static org.junit.Assert.*;


public class ListTest {

  @Test
  public void testHasSubList() throws Exception {
    assertTrue(List.hasSubList(List.list(1, 2, 3, 4), List.list(2, 3)));
    assertTrue(List.hasSubList(List.list(1, 2, 3, 4), List.list()));
    assertTrue(List.hasSubList(List.list(1, 2, 3, 4), List.list(1, 2, 3, 4)));
    assertFalse(List.hasSubList(List.list(1, 2, 3, 4), List.list(1, 3, 4)));
    assertTrue(List.hasSubList(List.list(1, 2, 3, 4, 5, 6), List.list(3, 4, 5, 6)));
    assertTrue(List.hasSubList(List.list(1, 2, 1, 4, 5, 6), List.list(1, 4, 5, 6)));
    assertTrue(List.hasSubList(List.list(1, 2, 1, 2, 1, 4), List.list(1, 2, 1, 4)));
  }
}
