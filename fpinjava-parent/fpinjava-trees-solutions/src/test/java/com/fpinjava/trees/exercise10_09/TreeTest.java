package com.fpinjava.trees.exercise10_09;

import com.fpinjava.common.Function;
import org.junit.Test;

import static org.junit.Assert.*;


public class TreeTest {

  @Test
  public void testFoldInOrder_inOrderLeft() {
    Function<String, Function<Integer, Function<String, String>>> f = s1 -> i -> s2 -> s1 + i + s2;
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    assertEquals("1234567", tree.foldInOrder("", f));
  }

  @Test
  public void testFoldInOrder_preOrderLeft() {
    Function<String, Function<Integer, Function<String, String>>> f = s1 -> i -> s2 -> i + s1 + s2;
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    assertEquals("4213657", tree.foldInOrder("", f));
  }

  @Test
  public void testFoldInOrder_postOrderLeft() {
    Function<String, Function<Integer, Function<String, String>>> f = s1 -> i -> s2 -> s1 + s2 + i;
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    assertEquals("1325764", tree.foldInOrder("", f));
  }

  @Test
  public void testFoldInOrder_inOrderRight() {
    Function<String, Function<Integer, Function<String, String>>> f = s1 -> i -> s2 -> s2 + i + s1;
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    assertEquals("7654321", tree.foldInOrder("", f));
  }

  @Test
  public void testFoldInOrder_preOrderRight() {
    Function<String, Function<Integer, Function<String, String>>> f = s1 -> i -> s2 -> s2 + s1 + i;
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    assertEquals("7563124", tree.foldInOrder("", f));
  }

  @Test
  public void testFoldInOrder_postOrderRight() {
    Function<String, Function<Integer, Function<String, String>>> f = s1 -> i -> s2 -> i + s2 + s1;
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    assertEquals("4675231", tree.foldInOrder("", f));
  }

  @Test
  public void testFoldPreOrder_preOrderLeft() {
    Function<Integer, Function<String, Function<String, String>>> f = i -> s1 -> s2 -> i + s1 + s2;
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    assertEquals("4213657", tree.foldPreOrder("", f));
  }

  @Test
  public void testFoldPreOrder_inOrderLeft() {
    Function<Integer, Function<String, Function<String, String>>> f = i -> s1 -> s2 -> s1 + i + s2;
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    assertEquals("1234567", tree.foldPreOrder("", f));
  }

  @Test
  public void testFoldPreOrder_preOrderRight() {
    Function<Integer, Function<String, Function<String, String>>> f = i -> s1 -> s2 -> i + s2 + s1;
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    assertEquals("4675231", tree.foldPreOrder("", f));
  }

  @Test
  public void testFoldPreOrder_postOrderRight() {
    Function<Integer, Function<String, Function<String, String>>> f = i -> s1 -> s2 -> s2 + s1 + i;
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    assertEquals("7563124", tree.foldPreOrder("", f));
  }

  @Test
  public void testFoldPreOrder_inOrderRight() {
    Function<Integer, Function<String, Function<String, String>>> f = i -> s1 -> s2 -> s2 + i + s1;
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    assertEquals("7654321", tree.foldPreOrder("", f));
  }

  @Test
  public void testFoldPreOrder_postOrderLeft() {
    Function<Integer, Function<String, Function<String, String>>> f = i -> s1 -> s2 -> s1 + s2 + i;
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    assertEquals("1325764", tree.foldPreOrder("", f));
  }

  @Test
  public void testFoldPostOrder_postOrderLeft() {
    Function<String, Function<String, Function<Integer, String>>> f = s1 -> s2 -> i -> s1 + s2 + i;
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    assertEquals("1325764", tree.foldPostOrder("", f));
  }

  @Test
  public void testFoldPostOrder_postOrderRight() {
    Function<String, Function<String, Function<Integer, String>>> f = s1 -> s2 -> i -> s2 + s1 + i;
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    assertEquals("7563124", tree.foldPostOrder("", f));
  }

  @Test
  public void testFoldPostOrder_inOrderLeft() {
    Function<String, Function<String, Function<Integer, String>>> f = s1 -> s2 -> i -> s1 + i + s2;
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    assertEquals("1234567", tree.foldPostOrder("", f));
  }

  @Test
  public void testFoldPostOrder_preOrderRight() {
    Function<String, Function<String, Function<Integer, String>>> f = s1 -> s2 -> i -> i + s2 + s1;
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    assertEquals("4675231", tree.foldPostOrder("", f));
  }

  @Test
  public void testFoldPostOrder_preOrderLeft() {
    Function<String, Function<String, Function<Integer, String>>> f = s1 -> s2 -> i -> i + s1 + s2;
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    assertEquals("4213657", tree.foldPostOrder("", f));
  }

  @Test
  public void testFoldPostOrder_inOrderRight() {
    Function<String, Function<String, Function<Integer, String>>> f = s1 -> s2 -> i -> s2 + i + s1;
    Tree<Integer> tree = Tree.tree(4, 2, 1, 3, 6, 5, 7);
    assertEquals("7654321", tree.foldPostOrder("", f));
  }
}
