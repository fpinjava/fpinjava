package com.fpinjava.trees.exercise10_13;

import org.junit.Test;

import static org.junit.Assert.*;


public class TreeTest {

  @Test
  public void testToListInOrderRight() {
    Tree<Integer> tree2 = Tree.tree(4, 6, 7, 5, 2, 1, 3);
    assertEquals("[7, 6, 5, 4, 3, 2, 1, NIL]", tree2.toListInOrderRight().toString());
  }
}
