package com.fpinjava.advancedlisthandling.exercise08_01;

import static org.junit.Assert.*;

import org.junit.Test;

public class ListTest {

  @Test
  public void testLengthMemoizedEmpty() {
    assertEquals(0, List.list().lengthMemoized());
  }

  @Test
  public void testLengthMemoizedNonEmpty() {
    assertEquals(3, List.list(1, 2, 3).lengthMemoized());
  }

}
