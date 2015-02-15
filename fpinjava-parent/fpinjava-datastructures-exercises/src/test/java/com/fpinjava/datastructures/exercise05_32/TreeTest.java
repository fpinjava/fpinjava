package com.fpinjava.datastructures.exercise05_32;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.datastructures.exercise05_32.Tree;

public class TreeTest {

  Tree<Integer> tree1 = Tree.tree(1);
  Tree<Integer> tree2 = Tree.tree(2);
  Tree<Integer> tree3 = Tree.tree(3);
  Tree<Integer> tree4 = Tree.tree(4);
  Tree<Integer> tree5 = Tree.tree(tree1, tree2);
  Tree<Integer> tree6 = Tree.tree(tree3, tree4);
  Tree<Integer> tree = Tree.tree(tree5, tree6);

  @Test
  public void testSizeViaFold() {
    assertEquals(7, Tree.sizeViaFold(tree));
  }

  @Test
  public void testMaximumViaFold() {
    assertEquals(4, Tree.maximumViaFold(tree));
  }

  @Test
  public void testDepthViaFold() {
    assertEquals(2, Tree.depthViaFold(tree));
  }

}
