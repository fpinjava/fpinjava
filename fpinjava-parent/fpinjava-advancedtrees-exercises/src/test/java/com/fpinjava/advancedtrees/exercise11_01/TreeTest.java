package com.fpinjava.advancedtrees.exercise11_01;

import com.fpinjava.common.List;
import com.fpinjava.state.SimpleRNG;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TreeTest {

  /*
   * Adjust according to your environment. The faster the computer,
   * the lower this value should be.
   */
  int timeFactor = 500;

  @Test
  public void testInsertOrderedAscending7() {
    int limit = 7;
    List<Integer> list = List.range(1, limit + 1);
    Tree<Integer> tree = list.foldLeft(Tree.<Integer>empty(), t -> t::insert);
    assertEquals(limit, tree.size());
    assertTrue(tree.height() <= 2 * log2nlz(tree.size() + 1));
  }

  @Test
  public void testInsertOrderedDescending7() {
    int limit = 7;
    List<Integer> list = List.iterate(limit, x -> x - 1, limit);
    Tree<Integer> tree = list.foldLeft(Tree.<Integer>empty(), t -> t::insert);
    assertEquals(limit, tree.size());
    assertTrue(tree.height() <= 2 * log2nlz(tree.size() + 1));
  }

  @Test
  public void testInsertRandom7() {
    List<Integer> list = List.list(2, 5, 7, 3, 6, 1, 4);
    Tree<Integer> tree = list.foldLeft(Tree.<Integer>empty(), t -> t::insert);
    assertTrue(tree.height() <= 2 * log2nlz(tree.size() + 1));
  }

  @Test
  public void testInsertOrderedAscending() {
    int limit = 2_000_000;
    long maxTime = 2 * log2nlz(limit + 1) * timeFactor;
    List<Integer> list = List.range(1, limit + 1);
    long time = System.currentTimeMillis();
    Tree<Integer> tree = list.foldLeft(Tree.<Integer>empty(), t -> t::insert);
    long duration = System.currentTimeMillis() - time;
    assertTrue(duration < maxTime);
    assertEquals(limit, tree.size());
    assertTrue(tree.height() <= 2 * log2nlz(tree.size() + 1));
  }

  @Test
  public void testInsertOrderedDescending() {
    int limit = 2_000_000;
    long maxTime = 2 * log2nlz(limit + 1) * timeFactor;
    List<Integer> list = List.iterate(limit, x -> x - 1, limit);
    long time = System.currentTimeMillis();
    Tree<Integer> tree = list.foldLeft(Tree.<Integer>empty(), t -> t::insert);
    long duration = System.currentTimeMillis() - time;
    assertEquals(limit, tree.size());
    assertTrue(tree.height() <= 2 * log2nlz(tree.size() + 1));
    assertTrue(duration < maxTime);
  }

  @Test
  public void testInsertRandom() {
    int limit = 2_000_000;
    long maxTime = 2 * log2nlz(limit + 1) * timeFactor;
    List<Integer> list = SimpleRNG.doubles(limit, new SimpleRNG.Simple(3))._1.map(x -> (int) (x * limit * 3));
    long time = System.currentTimeMillis();
    Tree<Integer> tree = list.foldLeft(Tree.<Integer>empty(), t -> t::insert);
    long duration = System.currentTimeMillis() - time;
    assertTrue(tree.height() <= 2 * log2nlz(tree.size() + 1));
    assertTrue(duration < maxTime);
  }

  public static int log2nlz(int n) {
    return n == 0
        ? 0
        : 31 - Integer.numberOfLeadingZeros(n);
  }

}
