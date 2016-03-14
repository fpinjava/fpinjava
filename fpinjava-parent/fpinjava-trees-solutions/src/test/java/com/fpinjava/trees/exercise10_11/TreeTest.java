package com.fpinjava.trees.exercise10_11;

import org.junit.Test;

import static org.junit.Assert.*;


public class TreeTest {

  @Test
  public void testMap() {
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    Tree<Integer> result = tree.map(x -> x + 2);
    assertEquals("(T (T (T E 3 E) 4 (T E 5 E)) 6 (T (T E 7 E) 8 (T E 9 E)))", result.toString());
  }

  @Test
  public void testMap2() {
    Tree<Integer> tree = Tree.tree(-4, 2, -1, 3, 6, -5, 7);
    Tree<Integer> result = tree.map(x -> x * x);
    assertEquals("(T (T (T E 1 E) 4 (T E 9 E)) 16 (T E 25 (T E 36 (T E 49 E))))", result.toString());
  }
}
