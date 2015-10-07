package com.fpinjava.trees.exercise10_07;

import com.fpinjava.common.List;
import com.fpinjava.state.SimpleRNG;
import org.junit.Test;

import static org.junit.Assert.*;


public class TreeTest {

  static int testLimitRandom = 1_000;
  static int testLimitOrdered = 1_000;

  static List<Integer> randomTestList1 = SimpleRNG.doubles(testLimitRandom, new SimpleRNG.Simple(1))._1.map(x -> (int) (x * 30));
  static List<Integer> randomTestList2 = SimpleRNG.doubles(testLimitRandom, new SimpleRNG.Simple(1000))._1.map(x -> (int) (x * 30));
  static List<Integer> orderedTestList1 = List.range(0, testLimitOrdered).filter(i -> i % 2 != 0);
  static List<Integer> orderedTestList2 = List.range(0, testLimitOrdered).filter(i -> i % 2 == 0).reverse();

  static final Tree<Integer> orderedTree1 = orderedTestList1.foldLeft(Tree.empty(), m -> m::insert);
  static final Tree<Integer> orderedTree2 = orderedTestList2.foldLeft(Tree.empty(), m -> m::insert);
  static final Tree<Integer> randomTree1 = randomTestList1.foldLeft(Tree.empty(), m -> m::insert);
  static final Tree<Integer> randomTree2 = randomTestList2.foldLeft(Tree.empty(), m -> m::insert);

  Tree<Integer> tree1 = List.list(3, 1, 5, 0, 2, 4, 6, 7).foldLeft(Tree.empty(), m -> m::insert);
  Tree<Integer> tree2 = List.list(5, 3, 7, 1, 4, 6, 8, -2).foldLeft(Tree.empty(), m -> m::insert);
  Tree<Integer> tree3 = List.list(3, 7, 1, 4, 6, 8, -1, 9).foldLeft(Tree.empty(), m -> m::insert);

  @Test
  public void testMerge1() {
    assertEquals("(T (T (T (T E -2 E) 0 E) 1 (T E 2 E)) 3 (T (T E 4 E) 5 (T E 6 (T E 7 (T E 8 E)))))", tree1.merge(tree2).toString());
    assertEquals("(T (T (T (T E -2 (T E 0 E)) 1 (T E 2 E)) 3 (T E 4 E)) 5 (T (T E 6 E) 7 (T E 8 E)))", tree2.merge(tree1).toString());
  }

  @Test
  public void testMerge2() {
    assertEquals("(T (T (T (T E -1 E) 0 E) 1 (T E 2 E)) 3 (T (T E 4 E) 5 (T E 6 (T E 7 (T E 8 (T E 9 E))))))", tree1.merge(tree3).toString());
    assertEquals("(T (T (T E -1 (T E 0 E)) 1 (T E 2 E)) 3 (T (T E 4 (T (T E 5 E) 6 E)) 7 (T E 8 (T E 9 E))))", tree3.merge(tree1).toString());
  }

  @Test
  public void testMerge3() {
    Tree<Integer> mergedTree = randomTree1.merge(randomTree2);
    assertTrue(List.concat(randomTestList1, randomTestList2).forAll(mergedTree::member));
  }

  @Test
  public void testMerge4() {
    Tree<Integer> mergedTree = randomTree2.merge(randomTree1);
    assertTrue(List.concat(randomTestList1, randomTestList2).forAll(mergedTree::member));
  }

  @Test
  public void testMerge5() {
    Tree<Integer> mergedTree = orderedTree1.merge(orderedTree2);
    assertTrue(List.concat(orderedTestList1, orderedTestList2).forAll(mergedTree::member));
  }

  @Test
  public void testMerge6() {
    Tree<Integer> mergedTree = orderedTree2.merge(orderedTree1);
    assertTrue(List.concat(orderedTestList1, orderedTestList2).forAll(mergedTree::member));
  }

  @Test
  public void testMerge7() {
    Tree<Integer> mergedTree = orderedTree2.merge(randomTree1);
    assertTrue(List.concat(randomTestList1, orderedTestList2).forAll(mergedTree::member));
  }

  @Test
  public void testMerge8() {
    Tree<Integer> mergedTree = randomTree2.merge(orderedTree1);
    assertTrue(List.concat(randomTestList2, orderedTestList1).forAll(mergedTree::member));
  }
}
