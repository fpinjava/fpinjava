package com.fpinjava.trees.exercise10_06;

import org.junit.Test;

import static org.junit.Assert.*;


public class TreeTest {

  @Test
  public void testRemoveLower() {
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    assertEquals("(T (T E 1 (T E 3 E)) 4 (T (T E 5 E) 6 (T E 7 E)))", tree.remove(2).toString());
  }

  @Test
  public void testRemoveEquals() {
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    assertEquals("(T (T E 1 E) 2 (T E 3 (T (T E 5 E) 6 (T E 7 E))))", tree.remove(4).toString());
  }

  @Test
  public void testRemoveHigher() {
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    assertEquals("(T (T (T E 1 E) 2 (T E 3 E)) 4 (T E 5 (T E 7 E)))", tree.remove(6).toString());
  }

  @Test
  public void testRemoveEmpty() {
    Tree<Integer> tree = Tree.tree();
    assertEquals("E", tree.remove(6).toString());
  }
}
