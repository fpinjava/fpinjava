package com.fpinjava.datastructures.exercise05_31;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.datastructures.exercise05_31.Tree;

public class TreeTest {

  @Test
  public void testMap() {
    Tree<Integer> tree1 = Tree.tree(1);
    Tree<Integer> tree2 = Tree.tree(2);
    Tree<Integer> tree3 = Tree.tree(3);
    Tree<Integer> tree4 = Tree.tree(4);
    Tree<Integer> tree5 = Tree.tree(tree1, tree2);
    Tree<Integer> tree6 = Tree.tree(tree3, tree4);
    Tree<Integer> tree = Tree.tree(tree5, tree6);
    assertEquals(8, Tree.maximum(tree.map(x -> x * 2)));
  }

}
