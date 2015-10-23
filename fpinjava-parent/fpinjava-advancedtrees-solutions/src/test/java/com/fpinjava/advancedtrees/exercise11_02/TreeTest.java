package com.fpinjava.advancedtrees.exercise11_02;

import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import com.fpinjava.common.Tuple;
import com.fpinjava.state.SimpleRNG;
import org.junit.Test;

import static org.junit.Assert.*;


public class TreeTest {

  /*
   * Adjust according to your environment. The faster the computer,
   * the lower this value should be.
   */
  int timeFactor = 500;

  static int testLimit = 200_000;

  static final List<Integer> randomTestList = SimpleRNG.doubles(testLimit, new SimpleRNG.Simple(3))._1.map(x -> (int) (x * 30));
  static final List<Integer> orderedTestList = List.range(0, testLimit);

  static final Tree<Integer> orderedTree = orderedTestList.foldLeft(Tree.empty(), m -> m::insert);
  static final Tree<Integer> randomTree = randomTestList.foldLeft(Tree.empty(), m -> m::insert);

  private static boolean arePathsOk(Tree<?> tree) {
    List<List<Tree.Color>> keepBlacks = tree.pathColors().map(list -> list.filter(Tree.Color::isB));
    int blackLength = keepBlacks.headOption().map(List::length).getOrElse(0);
    return tree.pathColors().forAll(TreeTest::isPathOk) && keepBlacks.forAll(lst -> lst.length() == blackLength);
  }

  private static boolean arePathsBalanced(Tree<?> tree) {
    return tree.pathColors().forAll(TreeTest::isPathOk);
  }

  private static boolean arePathsEqual(Tree<?> tree) {
    List<List<Tree.Color>> keepBlacks = tree.pathColors().map(list -> list.filter(Tree.Color::isB));
    int blackLength = keepBlacks.headOption().map(List::length).getOrElse(0);
    return keepBlacks.forAll(lst -> lst.length() == blackLength);
  }

  private static boolean isPathOk(List<Tree.Color> colorList) {
    List<Tree.Color> colorList1 = colorList.cons(Tree.B);
    List<Tree.Color> colorList2 = colorList.reverse().cons(Tree.B).reverse();
    Result<List<Tuple<Tree.Color, Tree.Color>>> colorPairs = colorList1.zip(colorList2);
    return !colorPairs.map(list -> list.exists(t -> (t._1.isR() && t._2.isR()) || t._1.isBB() || t._2.isBB() || t._1.isNB() || t._2.isNB())).getOrElse(true);
  }

  @Test
  public void testInsertOrderedAscending7() {
    int limit = 7;
    List<Integer> list = List.range(1, limit + 1);
    Tree<Integer> tree = list.foldLeft(Tree.<Integer>empty(), t -> t::insert);
    assertEquals(limit, tree.size());
    assertTrue(tree.height() <= 2 * Tree.log2nlz(tree.size() + 1));
  }

  @Test
  public void testInsertOrderedDescending7() {
    int limit = 7;
    List<Integer> list = List.iterate(limit, x -> x - 1, limit);
    Tree<Integer> tree = list.foldLeft(Tree.<Integer>empty(), t -> t::insert);
    assertEquals(limit, tree.size());
    assertTrue(tree.height() <= 2 * Tree.log2nlz(tree.size() + 1));
  }

  @Test
  public void testInsertRandom7() {
    List<Integer> list = List.list(2, 5, 7, 3, 6, 1, 4);
    Tree<Integer> tree = list.foldLeft(Tree.<Integer>empty(), t -> t::insert);
    assertTrue(tree.height() <= 2 * Tree.log2nlz(tree.size() + 1));
  }

  @Test
  public void testInsertOrderedAscending() {
    int limit = 2_000_000;
    long maxTime = 2 * Tree.log2nlz(limit + 1) * timeFactor;
    List<Integer> list = List.range(1, limit + 1);
    long time = System.currentTimeMillis();
    Tree<Integer> tree = list.foldLeft(Tree.<Integer>empty(), t -> t::insert);
    long duration = System.currentTimeMillis() - time;
    assertTrue(duration < maxTime);
    assertEquals(limit, tree.size());
    assertTrue(tree.height() <= 2 * Tree.log2nlz(tree.size() + 1));
    long time2 = System.currentTimeMillis();
    Tree<Integer> tree2 = list.foldLeft(tree, t -> t::delete);
    long duration2 = System.currentTimeMillis() - time2;
    assertTrue(duration2 < maxTime);
    assertTrue(tree2.isEmpty());
  }

  @Test
  public void testInsertOrderedDescending() {
    int limit = 2_000_000;
    long maxTime = 2 * Tree.log2nlz(limit + 1) * timeFactor;
    List<Integer> list = List.iterate(limit, x -> x - 1, limit);
    long time = System.currentTimeMillis();
    Tree<Integer> tree = list.foldLeft(Tree.<Integer>empty(), t -> t::insert);
    long duration = System.currentTimeMillis() - time;
    assertEquals(limit, tree.size());
    assertTrue(tree.height() <= 2 * Tree.log2nlz(tree.size() + 1));
    assertTrue(duration < maxTime);
    long time2 = System.currentTimeMillis();
    Tree<Integer> tree2 = list.foldLeft(tree, t -> t::delete);
    long duration2 = System.currentTimeMillis() - time2;
    assertTrue(duration2 < maxTime);
    assertTrue(tree2.isEmpty());
  }

  @Test
  public void testInsertRandom() {
    int limit = 2_000_000;
    long maxTime = 2 * Tree.log2nlz(limit + 1) * timeFactor;
    List<Integer> list = SimpleRNG.doubles(limit, new SimpleRNG.Simple(3))._1.map(x -> (int) (x * limit * 3));
    long time = System.currentTimeMillis();
    Tree<Integer> tree = list.foldLeft(Tree.<Integer>empty(), t -> t::insert);
    long duration = System.currentTimeMillis() - time;
    assertTrue(tree.height() <= 2 * Tree.log2nlz(tree.size() + 1));
    assertTrue(duration < maxTime);
    long time2 = System.currentTimeMillis();
    Tree<Integer> tree2 = list.foldLeft(tree, t -> t::delete);
    long duration2 = System.currentTimeMillis() - time2;
    assertTrue(duration2 < maxTime);
    assertTrue(tree2.isEmpty());
  }

  /**
   * Test whether the test data is correct
   */
  @Test
  public void testAddOrdered() {
    assertEquals(orderedTestList.length(), orderedTree.size());
    assertTrue(arePathsOk(orderedTree));
  }

  /**
   * Test whether the test data is correct
   */
  @Test
  public void testAddRandom() {
    assertTrue(arePathsOk(randomTree));
  }

  /**
   * All values inserted in the map should be member of the map;
   */
  @Test
  public void testMemberOrdered() {
    assertTrue(orderedTestList.forAll(orderedTree::member));
  }

  /**
   * The longest path (to an empty node) should be no more than twice the shortest one (to an empty node too).
   * Note that compared to a "user path", which means not taking empty terminal node in account, it means that
   * maxPath + 1 <= (minPath + 1) * 2
   */
  @Test
  public void testPathLengthOrdered() {
    List<Integer> lengths = orderedTree.pathLengths();
    Result<List<Boolean>> rlb = List.minOption(lengths).map(min -> lengths.map(v -> v + 1 <= (min + 1) * 2));
    List<Boolean> lb = rlb.getOrElse(List.list());
    assertTrue(lb.forAll(x -> x));
  }

  /**
   * Given the number of elements n, and the maximum height d, the following should hold:
   *  d <= log2(n + 1)
   */
  @Test
  public void testMaxDepthOrdered() {
    assertTrue(orderedTree.height() <= 2 * Tree.log2nlz(orderedTree.size() + 1));
  }

  @Test
  public void testMemberRandom() {
    assertTrue(randomTestList.forAll(randomTree::member));
  }

  /**
   * The longest path (to an empty node) should be no more than twice the shortest one (to an empty node too).
   * Note that compared to a "user path", which means not taking empty terminal node in account, it means that
   * maxPath + 1 <= (minPath + 1) * 2
   */
  @Test
  public void testPathLengthRandom() {
    List<Integer> lengths = randomTree.pathLengths();
    Result<List<Boolean>> rlb = List.minOption(lengths).map(min -> lengths.map(v -> v + 1 <= (min + 1) * 2));
    List<Boolean> lb = rlb.getOrElse(List.list());
    assertTrue(lb.forAll(x -> x));
  }

  /**
   * Given the number of elements n, and the maximum height d, the following should hold:
   *  d <= log(n + 1)
   */
  @Test
  public void testMaxDepthRandom() {
    assertTrue(randomTree.height() <= 2 * Tree.log2nlz(randomTree.size() + 1));
  }

  /**
   * No single value should be present in the map after removal;
   */
  @Test
  public void testRemoveOneOrdered() {
    assertTrue(orderedTestList.map(i -> orderedTree.delete(i).member(i)).forAll(x -> !x));
  }

  @Test
  public void testRemoveOneRandom() {
    assertTrue(randomTestList.map(i -> randomTree.delete(i).member(i)).forAll(x -> !x));
  }

  /**
   * After removal of all inserted values, the map should be empty;
   */
  @Test
  public void testRemoveAllOrdered() {
    assertTrue(orderedTestList.foldLeft(orderedTree, m -> m::delete).isEmpty());
  }

  @Test
  public void testRemoveAllRandom() {
    assertTrue(randomTestList.foldLeft(randomTree, m -> m::delete).isEmpty());
  }

  @Test
  public void testRemoveHalfOrdered() {
    Tuple<List<Integer>, List<Integer>> lists = orderedTestList.splitAt(orderedTestList.length() / 2);
    List<Integer> list1 = lists._1;
    List<Integer> list2 = lists._2;
    Tree<Integer> m1 = list1.foldLeft(orderedTree, m -> m::delete);
    assertEquals(list2.length(), m1.size());
    assertTrue(arePathsBalanced(m1));
    assertTrue(arePathsEqual(m1));
    Tree<Integer> m2 = list1.foldLeft(m1, m -> m::insert);
    assertEquals(orderedTestList.length(), m2.size());
    assertTrue(arePathsBalanced(m2));
    assertTrue(arePathsEqual(m2));
  }

  @Test
  public void testRemoveHalfRandom() {
    assertTrue(randomTestList.foldLeft(randomTree, m -> m::delete).isEmpty());
  }

  /**
   * Test for 2000 random number list computed from a random seed whether inserting 'testLimit' entries
   * and the removing them results in an empty tree.
   */
  @Test
  public void testRemoveAllRandomX2000() {
    for (int j = 0; j < 2_000; j++) {
      long seed = System.nanoTime();
      assertTrue(SimpleRNG.doubles(10_000, new SimpleRNG.Simple(seed))._1.map(x -> (int) (x * 30)).foldLeft(randomTree, m -> m::delete).isEmpty());
    }
  }
}
