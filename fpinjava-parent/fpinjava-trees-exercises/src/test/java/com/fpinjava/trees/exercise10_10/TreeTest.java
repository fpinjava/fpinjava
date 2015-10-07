package com.fpinjava.trees.exercise10_10;

import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import com.fpinjava.common.Tuple;
import org.junit.Test;

import static org.junit.Assert.*;


public class TreeTest {

  @Test
  public void testListFoldRight() {
    List<Integer> list1 = List.unfold(0, i -> i < 20
        ? Result.success(new Tuple<>(i, i + 1))
        : Result.empty());
    List<Integer> list2 = list1.foldRight(List.list(), i -> l -> l.cons(i));
    assertEquals(list1, list2);
  }

  @Test
  public void testListFoldLeft() {
    List<Integer> list1 = List.unfold(0, i -> i < 20
        ? Result.success(new Tuple<>(i, i + 1))
        : Result.empty());
    List<Integer> list2 = list1.foldLeft(List.list(), l -> i -> l.reverse().cons(i).reverse());
    assertEquals(list1, list2);
  }

  @Test
  public void testMergeLeftEmpty() {
    Tree<Integer> tree = Tree.tree(6, 7, 5, 9, 8);
    Tree<Integer> result = Tree.tree(Tree.empty(), 4, tree);
    assertEquals("(T E 4 (T (T E 5 E) 6 (T E 7 (T (T E 8 E) 9 E))))", result.toString());
  }

  @Test
  public void testMergeLeftEmptyNok() {
    Tree<Integer> tree = Tree.tree(4, 6, 7, 5, 2, 1, 0);
    Tree<Integer> result = Tree.tree(Tree.empty(), 4, tree);
    assertEquals("(T (T (T (T E 0 E) 1 E) 2 E) 4 (T (T E 5 E) 6 (T E 7 E)))", result.toString());
  }

  @Test
  public void testMergeRightEmpty() {
    Tree<Integer> tree = Tree.tree(4, 6, 7, 5, 2, 1, 0);
    Tree<Integer> result = Tree.tree(tree, 4, Tree.<Integer>empty());
    assertEquals("(T (T (T (T E 0 E) 1 E) 2 E) 4 (T (T E 5 E) 6 (T E 7 E)))", result.toString());
  }

  @Test
  public void testMergeNok() {
    Tree<Integer> tree1 = Tree.tree(4, 6, 7, 5, 2);
    Tree<Integer> tree2 = Tree.tree(7, 5, 2, 1, 0);
    Tree<Integer> result = Tree.tree(tree1, 4, tree2);
    assertEquals("(T (T (T (T E 0 E) 1 E) 2 E) 4 (T (T E 5 E) 6 (T E 7 E)))", result.toString());
  }

  @Test
  public void testMergeEmpty() {
    Tree<Integer> result = Tree.tree(Tree.<Integer>empty(), 4, Tree.<Integer>empty());
    assertEquals("(T E 4 E)", result.toString());
  }

  @Test
  public void testMerge() {
    Tree<Integer> tree1 = Tree.tree(2, 1, 3);
    Tree<Integer> tree2 = Tree.tree(6, 5, 7);
    Tree<Integer> result = Tree.tree(tree1, 4, tree2);
    assertEquals("(T (T (T E 1 E) 2 (T E 3 E)) 4 (T (T E 5 E) 6 (T E 7 E)))", result.toString());
    assertEquals("[4, 2, 1, 3, 6, 5, 7, NIL]", result.foldPreOrder(List.<Integer>list(), i -> l1 -> l2 -> List.list(i).concat(l1).concat(l2)).toString());
  }

  @Test
  public void testMergeInverseOrder() {
    Tree<Integer> tree1 = Tree.tree(2, 1, 3);
    Tree<Integer> tree2 = Tree.tree(6, 5, 7);
    Tree<Integer> result = Tree.tree(tree2, 4, tree1);
    assertEquals("(T (T (T E 1 E) 2 (T E 3 E)) 4 (T (T E 5 E) 6 (T E 7 E)))", result.toString());
    assertEquals("[4, 2, 1, 3, 6, 5, 7, NIL]", result.foldPreOrder(List.<Integer>list(), i -> l1 -> l2 -> List.list(i).concat(l1).concat(l2)).toString());
  }

  @Test
  public void testTreeFold1() {
    List<Integer> list = List.list(4, 6, 7, 5, 2, 1, 3);
    Tree<Integer> tree0 = Tree.tree(list);
    Tree<Integer> tree1 = tree0.foldInOrder(Tree.<Integer>empty(), t1 -> i -> t2 -> Tree.tree(t1, i, t2));
    assertEquals(tree0.toString(), tree1.toString());
    Tree<Integer> tree2 = tree0.foldPostOrder(Tree.<Integer>empty(), t1 -> t2 -> i -> Tree.tree(t1, i, t2));
    assertEquals(tree0.toString(), tree2.toString());
    Tree<Integer> tree3 = tree0.foldPreOrder(Tree.<Integer>empty(), i -> t1 -> t2 -> Tree.tree(t1, i, t2));
    assertEquals(tree0.toString(), tree3.toString());
  }

}
