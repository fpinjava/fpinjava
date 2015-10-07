package com.fpinjava.trees.exercise10_12;

import org.junit.Test;

import static org.junit.Assert.*;


public class TreeTest {

  @Test
  public void testRotateLeft() {
    Tree<Integer> tree = Tree.tree(4, 6, 7, 5, 2, 1, 3);
    assertEquals("(T (T (T E 1 E) 2 (T E 3 E)) 4 (T (T E 5 E) 6 (T E 7 E)))", tree.toString());
    Tree<Integer> tree1 = tree.rotateLeft();
    assertEquals("(T (T (T (T E 1 E) 2 (T E 3 E)) 4 (T E 5 E)) 6 (T E 7 E))", tree1.toString());
    Tree<Integer> tree2 = tree1.rotateLeft();
    assertEquals("(T (T (T (T (T E 1 E) 2 (T E 3 E)) 4 (T E 5 E)) 6 E) 7 E)", tree2.toString());
    Tree<Integer> tree3 = tree2.rotateLeft();
    assertEquals("(T (T (T (T (T E 1 E) 2 (T E 3 E)) 4 (T E 5 E)) 6 E) 7 E)", tree3.toString());
  }

  @Test
  public void testRotateRight() {
    Tree<Integer> tree = Tree.tree(7, 6, 5, 4, 3, 2, 1);
    Tree<Integer> tree1 = tree.rotateRight();
    assertEquals("(T (T (T (T (T (T E 1 E) 2 E) 3 E) 4 E) 5 E) 6 (T E 7 E))", tree1.toString());
    Tree<Integer> tree2 = tree1.rotateRight();
    assertEquals("(T (T (T (T (T E 1 E) 2 E) 3 E) 4 E) 5 (T E 6 (T E 7 E)))", tree2.toString());
    Tree<Integer> tree3 = tree2.rotateRight();
    assertEquals("(T (T (T (T E 1 E) 2 E) 3 E) 4 (T E 5 (T E 6 (T E 7 E))))", tree3.toString());
    Tree<Integer> tree4 = tree3.rotateRight();
    assertEquals("(T (T (T E 1 E) 2 E) 3 (T E 4 (T E 5 (T E 6 (T E 7 E)))))", tree4.toString());
    Tree<Integer> tree5 = tree4.rotateRight();
    assertEquals("(T (T E 1 E) 2 (T E 3 (T E 4 (T E 5 (T E 6 (T E 7 E))))))", tree5.toString());
    Tree<Integer> tree6 = tree5.rotateRight();
    assertEquals("(T E 1 (T E 2 (T E 3 (T E 4 (T E 5 (T E 6 (T E 7 E)))))))", tree6.toString());
    Tree<Integer> tree7 = tree6.rotateRight();
    assertEquals("(T E 1 (T E 2 (T E 3 (T E 4 (T E 5 (T E 6 (T E 7 E)))))))", tree7.toString());
  }
}
