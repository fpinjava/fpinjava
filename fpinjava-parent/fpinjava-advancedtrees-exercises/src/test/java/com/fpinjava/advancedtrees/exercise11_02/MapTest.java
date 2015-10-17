package com.fpinjava.advancedtrees.exercise11_02;

import com.fpinjava.common.List;
import com.fpinjava.state.SimpleRNG;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class MapTest {

  /*
   * Adjust according to your environment. The faster the computer,
   * the lower this value should be.
   */
  int timeFactor = 500;

  /*- Uncomment when needed
  @Test
  public void testInsertOrderedAscending7() {
    int limit = 7;
    List<Integer> list = List.range(1, limit + 1);
    Map<Integer, String> map = list.foldLeft(Map.<Integer, String>empty(), m -> i -> m.add(i, NumbersToEnglish.convertUS.apply(i)));
    assertTrue(list.forAll(map::contains));
    assertTrue(List.sequence(list.map(i -> map.get(i).flatMap(x -> x.value).map(y -> y.equals(NumbersToEnglish.convertUS.apply(i))))).map(z -> z.forAll(w -> w)).getOrElse(false));
  }

  @Test
  public void testInsertOrderedDescending7() {
    int limit = 7;
    List<Integer> list = List.iterate(limit, x -> x - 1, limit);
    Map<Integer, String> map = list.foldLeft(Map.<Integer, String>empty(), m -> i -> m.add(i, NumbersToEnglish.convertUS.apply(i)));
    assertTrue(list.forAll(map::contains));
    assertTrue(List.sequence(list.map(i -> map.get(i).flatMap(x -> x.value).map(y -> y.equals(NumbersToEnglish.convertUS.apply(i))))).map(z -> z.forAll(w -> w)).getOrElse(false));
  }

  @Test
  public void testInsertRandom7() {
    List<Integer> list = List.list(2, 5, 7, 3, 6, 1, 4);
    Map<Integer, String> map = list.foldLeft(Map.<Integer, String>empty(), m -> i -> m.add(i, NumbersToEnglish.convertUS.apply(i)));
    assertTrue(list.forAll(map::contains));
    assertTrue(List.sequence(list.map(i -> map.get(i).flatMap(x -> x.value).map(y -> y.equals(NumbersToEnglish.convertUS.apply(i))))).map(z -> z.forAll(w -> w)).getOrElse(false));
  }

  @Test
  public void testInsertOrderedAscending() {
    int limit = 200_000;
    long maxTime = 2 * Tree.log2nlz(limit + 1) * timeFactor;
    List<Integer> list = List.range(1, limit + 1);
    long time = System.currentTimeMillis();
    Map<Integer, String> map = list.foldLeft(Map.<Integer, String>empty(), m -> i -> m.add(i, NumbersToEnglish.convertUS.apply(i)));
    long duration = System.currentTimeMillis() - time;
    assertTrue(duration < maxTime);
    assertTrue(List.sequence(list.map(i -> map.get(i).flatMap(x -> x.value).map(y -> y.equals(NumbersToEnglish.convertUS.apply(i))))).map(z -> z.forAll(w -> w)).getOrElse(false));
    long time2 = System.currentTimeMillis();
    Map<Integer, String> map2 = list.foldLeft(map, m -> m::remove);
    long duration2 = System.currentTimeMillis() - time2;
    assertTrue(duration2 < maxTime);
    assertTrue(map2.isEmpty());
  }

  @Test
  public void testInsertOrderedDescending() {
    int limit = 200_000;
    long maxTime = 2 * Tree.log2nlz(limit + 1) * timeFactor;
    List<Integer> list = List.iterate(limit, x -> x - 1, limit);
    long time = System.currentTimeMillis();
    Map<Integer, String> map = list.foldLeft(Map.<Integer, String>empty(), m -> i -> m.add(i, NumbersToEnglish.convertUS.apply(i)));
    long duration = System.currentTimeMillis() - time;
    assertTrue(duration < maxTime);
    assertTrue(List.sequence(list.map(i -> map.get(i).flatMap(x -> x.value).map(y -> y.equals(NumbersToEnglish.convertUS.apply(i))))).map(z -> z.forAll(w -> w)).getOrElse(false));
    long time2 = System.currentTimeMillis();
    Map<Integer, String> map2 = list.foldLeft(map, m -> m::remove);
    long duration2 = System.currentTimeMillis() - time2;
    assertTrue(duration2 < maxTime);
    assertTrue(map2.isEmpty());
  }

  @Test
  public void testInsertRandom() {
    int limit = 200_000;
    long maxTime = 2 * Tree.log2nlz(limit + 1) * timeFactor;
    List<Integer> list = SimpleRNG.doubles(limit, new SimpleRNG.Simple(3))._1.map(x -> (int) (x * limit * 3));
    long time = System.currentTimeMillis();
    Map<Integer, String> map = list.foldLeft(Map.<Integer, String>empty(), m -> i -> m.add(i, NumbersToEnglish.convertUS.apply(i)));
    long duration = System.currentTimeMillis() - time;
    assertTrue(duration < maxTime);
    assertTrue(List.sequence(list.map(i -> map.get(i).flatMap(x -> x.value).map(y -> y.equals(NumbersToEnglish.convertUS.apply(i))))).map(z -> z.forAll(w -> w)).getOrElse(false));
    long time2 = System.currentTimeMillis();
    Map<Integer, String> map2 = list.foldLeft(map, m -> m::remove);
    long duration2 = System.currentTimeMillis() - time2;
    assertTrue(duration2 < maxTime);
    assertTrue(map2.isEmpty());
  }
  //*/
}
