package com.fpinjava.trees.exercise10_15;

import com.fpinjava.common.List;
import org.junit.Test;

import static org.junit.Assert.*;


public class TreeTest {

  /*
   * Adjust according to your environment. The faster the computer,
   * the lower this value should be.
   */
  int timeFactor = 500;

  @Test
  public void testInsert() {
    int limit = 100_000;
    long maxTime = 2 * Tree.log2nlz(limit + 1) * timeFactor;
    List<Integer> orderedTestList = List.range(0, limit);
    long time = System.currentTimeMillis();
    Tree<Integer> temp = orderedTestList.foldLeft(Tree.empty(), m -> m::insert);
    Tree<Integer> tree = Tree.balance(temp);
    long duration = System.currentTimeMillis() - time;
    assertEquals(limit, tree.size());
    assertTrue(tree.height() <= 2 * Tree.log2nlz(tree.size() + 1));
    assertTrue(duration < maxTime);
  }
}
