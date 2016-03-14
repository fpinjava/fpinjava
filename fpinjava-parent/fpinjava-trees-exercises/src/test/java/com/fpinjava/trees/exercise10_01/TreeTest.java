package com.fpinjava.trees.exercise10_01;

import org.junit.Test;

import static org.junit.Assert.*;


public class TreeTest {

  @Test
  public void testInsert() {
    Tree<Integer> tree = Tree.<Integer>empty().insert(4).insert(2).insert(6).insert(1).insert(3).insert(7).insert(5);
    assertEquals("(T (T (T E 1 E) 2 (T E 3 E)) 4 (T (T E 5 E) 6 (T E 7 E)))", tree.toString());
  }
}
