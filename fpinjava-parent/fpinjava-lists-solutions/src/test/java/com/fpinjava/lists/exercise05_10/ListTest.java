package com.fpinjava.lists.exercise05_10;

import static com.fpinjava.lists.exercise05_10.List.list;
import static org.junit.Assert.*;

import org.junit.Test;

public class ListTest {

  @Test
  public void testFoldLeft() {
    assertEquals(Integer.valueOf(0), List.<Integer>list().foldLeft(0, x -> y -> x + y));
    assertEquals(Integer.valueOf(6), list(1, 2, 3).foldLeft(0, x -> y -> x + y));
    assertEquals(Double.valueOf(1.0), List.<Double>list().foldLeft(1.0, x -> y -> x * y));
    assertEquals(Double.valueOf(24.0), list(1.0, 2.0, 3.0, 4.0).foldLeft(1.0, x -> y -> x * y));
  }

}
