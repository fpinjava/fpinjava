package com.fpinjava.trees.exercise10_04;


import org.junit.Test;

import static org.junit.Assert.*;


public class TreeTest {

  @Test
  public void testSize() throws Exception {
    Tree<Integer> tree = Tree.tree(4, 2, 6, 1, 3, 7, 5);
    assertEquals(7, tree.size());
  }

  @Test
  public void testHeight() throws Exception {
    Tree<Integer> tree = Tree.tree(4, 2, 6, 1, 3, 7, 5);
    assertEquals(2, tree.height());
  }

  @Test
  public void testSize2() throws Exception {
    Tree<Integer> tree = Tree.tree(1, 2, 3, 4, 5, 6, 7);
    assertEquals(7, tree.size());
  }

  @Test
  public void testHeight2() throws Exception {
    Tree<Integer> tree = Tree.tree(1, 2, 3, 4, 5, 6, 7);
    assertEquals(6, tree.height());
  }
}
