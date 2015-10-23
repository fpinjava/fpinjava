package com.fpinjava.trees.exercise10_05;

import org.junit.Test;

import static org.junit.Assert.*;


public class TreeTest {

  @Test
  public void testMax() throws Exception {
    Tree<Integer> tree = Tree.tree(4, 2, 6, 1, 3, 7, 5);
    assertTrue(tree.max().map(i -> i.equals(7)).getOrElse(false));
  }

  @Test
  public void testMin() throws Exception {
    Tree<Integer> tree = Tree.tree(4, 2, 6, 1, 3, 7, 5);
    assertTrue(tree.min().map(i -> i.equals(1)).getOrElse(false));
  }

  @Test
  public void testMax2() throws Exception {
    Tree<Integer> tree = Tree.tree(1, 2, 3, 4, 5, 6, 7);
    assertTrue(tree.max().map(i -> i.equals(7)).getOrElse(false));
  }

  @Test
  public void testMin2() throws Exception {
    Tree<Integer> tree = Tree.tree(1, 2, 3, 4, 5, 6, 7);
    assertTrue(tree.min().map(i -> i.equals(1)).getOrElse(false));
  }
}
