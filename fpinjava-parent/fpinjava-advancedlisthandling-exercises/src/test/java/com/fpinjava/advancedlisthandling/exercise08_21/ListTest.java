package com.fpinjava.advancedlisthandling.exercise08_21;

import org.junit.Test;

import static org.junit.Assert.*;


public class ListTest {

  @Test
  public void testForAll() throws Exception {
    List<Integer> list = List.list(1, 2, 3, 4, 5, 6, 7, 8);
    assertFalse(list.forAll(x -> x < 4));
    assertFalse(list.forAll(x -> x < 1));
    assertTrue(list.forAll(x -> x > 0));
    assertFalse(list.forAll(x -> x > 4));
  }

  @Test
  public void testForAllEmpty() throws Exception {
    List<Integer> list = List.list();
    assertTrue(list.forAll(x -> x < 4));
    assertTrue(list.forAll(x -> x < 1));
    assertTrue(list.forAll(x -> x > 0));
    assertTrue(list.forAll(x -> x > 4));
  }
}
