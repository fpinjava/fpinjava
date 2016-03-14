package com.fpinjava.trees.exercise10_03;

import org.junit.Test;

import static org.junit.Assert.*;


public class TreeTest {

  @Test
  public void testTree() throws Exception {
    Tree<Integer> tree = Tree.tree(4, 2, 6, 1, 3, 7, 5);
    assertEquals("(T (T (T E 1 E) 2 (T E 3 E)) 4 (T (T E 5 E) 6 (T E 7 E)))", tree.toString());

  }
}
