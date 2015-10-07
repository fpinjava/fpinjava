package com.fpinjava.trees.exercise10_19;

import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import com.fpinjava.common.Tuple;
import com.fpinjava.state.SimpleRNG;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MapTest {

  static int testLimit = 200_000;

  static List<Integer> randomTestList = SimpleRNG.doubles(testLimit, new SimpleRNG.Simple(3))._1.map(x -> (int) (x * 30));
  static List<Integer> orderedTestList = List.range(0, testLimit);

  static final Map<Integer, Integer> orderedMap = orderedTestList.foldLeft(Map.empty(), m -> i -> m.add(i, i));
  static final Map<Integer, Integer> randomMap = randomTestList.foldLeft(Map.empty(), m -> i -> m.add(i, i));

  private static boolean arePathsOk(Tree<?> tree) {
    List<List<Tree.Color>> keepBlacks = tree.pathColors().map(list -> list.filter(Tree.Color::isB));
    return tree.pathColors().forAll(MapTest::isPathOk) && keepBlacks.forAll(lst -> lst.length() == keepBlacks.head().length());
  }

  private static boolean arePathsBalanced(Tree<?> tree) {
    return tree.pathColors().forAll(MapTest::isPathOk);
  }

  private static boolean arePathsEquals(Tree<?> tree) {
    List<List<Tree.Color>> keepBlacks = tree.pathColors().map(list -> list.filter(Tree.Color::isB));
    return keepBlacks.forAll(lst -> lst.length() == keepBlacks.head().length());
  }

  private static boolean isPathOk(List<Tree.Color> colorList) {
    List<Tree.Color> colorList1 = colorList.cons(Tree.B);
    List<Tree.Color> colorList2 = colorList.reverse().cons(Tree.B).reverse();
    Result<List<Tuple<Tree.Color, Tree.Color>>> colorPairs = colorList1.zip(colorList2);
    return !colorPairs.map(list -> list.exists(t -> (t._1.isR() && t._2.isR()) || t._1.isBB() || t._2.isBB() || t._1.isNB() || t._2.isNB())).getOrElse(true);
  }

  /**
   * Test whether the test data is correct
   */
  @Test
  public void testAddOrdered() {
    assertEquals(orderedTestList.length(), orderedMap.delegate.size());
    assertTrue(arePathsOk(orderedMap.delegate));
  }

  /**
   * Test whether the test data is correct
   */
  @Test
  public void testAddRandom() {
    assertTrue(arePathsOk(randomMap.delegate));
  }

  /**
   * All values inserted in the map should be member of the map;
   */
  @Test
  public void testMemberOrdered() {
    assertTrue(orderedTestList.forAll(orderedMap::contains));
  }

  /**
   * The longest path (to an empty node) should be no more than twice the shortest one (to an empty node too).
   * Note that compared to a "user path", which means not taking empty terminal node in account, it means that
   * maxPath + 1 <= (minPath + 1) * 2
   */
  @Test
  public void testPathLengthOrdered() {
    List<Integer> lengths = orderedMap.delegate.pathLengths();
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
    assertTrue(orderedMap.delegate.height() <= 2 * Tree.log2nlz(orderedMap.delegate.size() + 1));
  }

  @Test
  public void testMemberRandom() {
    assertTrue(randomTestList.forAll(randomMap::contains));
  }

  /**
   * The longest path (to an empty node) should be no more than twice the shortest one (to an empty node too).
   * Note that compared to a "user path", which means not taking empty terminal node in account, it means that
   * maxPath + 1 <= (minPath + 1) * 2
   */
  @Test
  public void testPathLengthRandom() {
    List<Integer> lengths = randomMap.delegate.pathLengths();
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
    assertTrue(randomMap.delegate.height() <= 2 * Tree.log2nlz(randomMap.delegate.size() + 1));
  }

  /**
   * No single value should be present in the map after removal;
   */
  @Test
  public void testRemoveOneOrdered() {
    assertTrue(orderedTestList.map(i -> orderedMap.remove(i).contains(i)).forAll(x -> !x));
  }

  @Test
  public void testRemoveOneRandom() {
    assertTrue(randomTestList.map(i -> randomMap.remove(i).contains(i)).forAll(x -> !x));
  }

  /**
   * After removal of all inserted values, the map should be empty;
   */
  @Test
  public void testRemoveAllOrdered() {
    assertTrue(orderedTestList.foldLeft(orderedMap, m -> m::remove).isEmpty());
  }

  @Test
  public void testRemoveAllRandom() {
    assertTrue(randomTestList.foldLeft(randomMap, m -> m::remove).isEmpty());
  }

  @Test
  public void testRemoveHalfOrdered() {
    Tuple<List<Integer>, List<Integer>> lists = orderedTestList.splitAt(orderedTestList.length() / 2);
    List<Integer> list1 = lists._1;
    List<Integer> list2 = lists._2;
    Map<Integer, Integer> m1 = list1.foldLeft(orderedMap, m -> m::remove);
    assertEquals(list2.length(), m1.delegate.size());
    Map<Integer, Integer> m2 = list1.foldLeft(m1, m -> i -> m.add(i, i));
    assertEquals(orderedTestList.length(), m2.delegate.size());
    assertTrue(arePathsBalanced(m2.delegate));
    assertTrue(arePathsEquals(m2.delegate));
  }

  @Test
  public void testRemoveHalfRandom() {
    assertTrue(randomTestList.foldLeft(randomMap, m -> m::remove).isEmpty());
  }

  /**
   * Test for 2000 random number list computed from a random seed whether inserting 'testLimit' entries
   * and the removing them results in an empty tree.
   */
  @Test
  public void testRemoveAllRandomX2000() {
    for (int j = 0; j < 2_000; j++) {
      long seed = System.nanoTime();
      assertTrue(SimpleRNG.doubles(10_000, new SimpleRNG.Simple(seed))._1.map(x -> (int) (x * 30)).foldLeft(randomMap, m -> m::remove).isEmpty());
    }
  }

}
