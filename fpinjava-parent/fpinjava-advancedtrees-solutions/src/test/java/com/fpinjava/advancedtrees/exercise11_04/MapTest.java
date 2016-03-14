package com.fpinjava.advancedtrees.exercise11_04;

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

  @Test
  public void testInsertOrderedAscending7() {
    int limit = 7;
    List<Integer> list = List.range(1, limit + 1);
    Map<Number, String> map = list.foldLeft(Map.<Number, String>empty(), m -> i -> m.add(number(i), NumbersToEnglish.convertUS.apply(i)));
    assertTrue(list.forAll(i -> map.contains(number(i))));
    assertTrue(List.sequence(list.map(i -> map.get(number(i)).map(x -> x._2).map(y -> y.equals(NumbersToEnglish.convertUS.apply(i))))).map(z -> z.forAll(w -> w)).getOrElse(false));
  }

  @Test
  public void testInsertOrderedDescending7() {
    int limit = 7;
    List<Integer> list = List.iterate(limit, x -> x - 1, limit);
    Map<Number, String> map = list.foldLeft(Map.<Number, String>empty(), m -> i -> m.add(number(i), NumbersToEnglish.convertUS.apply(i)));
    assertTrue(list.forAll(i -> map.contains(number(i))));
    assertTrue(List.sequence(list.map(i -> map.get(number(i)).map(x -> x._2).map(y -> y.equals(NumbersToEnglish.convertUS.apply(i))))).map(z -> z.forAll(w -> w)).getOrElse(false));
  }

  @Test
  public void testInsertRandom7() {
    List<Integer> list = List.list(2, 5, 7, 3, 6, 1, 4);
    Map<Number, String> map = list.foldLeft(Map.<Number, String>empty(), m -> i -> m.add(number(i), NumbersToEnglish.convertUS.apply(i)));
    assertTrue(list.forAll(i -> map.contains(number(i))));
    assertTrue(List.sequence(list.map(i -> map.get(number(i)).map(x -> x._2).map(y -> y.equals(NumbersToEnglish.convertUS.apply(i))))).map(z -> z.forAll(w -> w)).getOrElse(false));
  }

  @Test
  public void testInsertOrderedAscending() {
    int limit = 200_000;
    long maxTime = 2 * Tree.log2nlz(limit + 1) * timeFactor;
    List<Integer> list = List.range(1, limit + 1);
    long time = System.currentTimeMillis();
    Map<Number, String> map = list.foldLeft(Map.<Number, String>empty(), m -> i -> m.add(number(i), NumbersToEnglish.convertUS.apply(i)));
    long duration = System.currentTimeMillis() - time;
    assertTrue(duration < maxTime);
    assertTrue(List.sequence(list.map(i -> map.get(number(i)).map(x -> x._2).map(y -> y.equals(NumbersToEnglish.convertUS.apply(i))))).map(z -> z.forAll(w -> w)).getOrElse(false));
    long time2 = System.currentTimeMillis();
    Map<Number, String> map2 = list.foldLeft(map, m -> i -> m.remove(number(i)));
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
    Map<Number, String> map = list.foldLeft(Map.<Number, String>empty(), m -> i -> m.add(number(i), NumbersToEnglish.convertUS.apply(i)));
    long duration = System.currentTimeMillis() - time;
    assertTrue(duration < maxTime);
    assertTrue(List.sequence(list.map(i -> map.get(number(i)).map(x -> x._2).map(y -> y.equals(NumbersToEnglish.convertUS.apply(i))))).map(z -> z.forAll(w -> w)).getOrElse(false));
    long time2 = System.currentTimeMillis();
    Map<Number, String> map2 = list.foldLeft(map, m -> i -> m.remove(number(i)));
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
    Map<Number, String> map = list.foldLeft(Map.<Number, String>empty(), m -> i -> m.add(number(i), NumbersToEnglish.convertUS.apply(i)));
    long duration = System.currentTimeMillis() - time;
    assertTrue(duration < maxTime);
    assertTrue(List.sequence(list.map(i -> map.get(number(i)).map(x -> x._2).map(y -> y.equals(NumbersToEnglish.convertUS.apply(i))))).map(z -> z.forAll(w -> w)).getOrElse(false));
    long time2 = System.currentTimeMillis();
    Map<Number, String> map2 = list.foldLeft(map, m -> i -> m.remove(number(i)));
    long duration2 = System.currentTimeMillis() - time2;
    assertTrue(duration2 < maxTime);
    assertTrue(map2.isEmpty());
  }

  private Number number(int num) {
    return new Number(num);
  }

  private class Number {
    public final int value;

    private Number(int value) {
      this.value = value;
    }

    public int hashCode() {
      return value / 5;
    }

    public boolean equals(Object that) {
      return that instanceof Number && this.value == ((Number) that).value;
    }

    public String toString() {
      return Integer.toString(value);
    }
  }
}
