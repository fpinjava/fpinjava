package com.fpinjava.datastructures.exercise05_27;

import static org.junit.Assert.*;

import org.junit.Test;

public class ListTest {

  @Test
  public void testHasSubsequence() {
    assertTrue(List.hasSubsequence(List.list(1, 2, 3, 4), List.list(2, 3)));
    assertTrue(List.hasSubsequence(List.list(1, 2, 3, 4), List.list()));
    assertTrue(List.hasSubsequence(List.list(1, 2, 3, 4), List.list(1, 2, 3, 4)));
    assertFalse(List.hasSubsequence(List.list(1, 2, 3, 4), List.list(1, 3, 4)));
    assertTrue(List.hasSubsequence(List.list(1, 2, 3, 4, 5, 6), List.list(3, 4, 5, 6)));
    assertTrue(List.hasSubsequence(List.list(1, 2, 1, 4, 5, 6), List.list(1, 4, 5, 6)));
    assertTrue(List.hasSubsequence(List.list(1, 2, 1, 2, 1, 4), List.list(1, 2, 1, 4)));
  }

}
