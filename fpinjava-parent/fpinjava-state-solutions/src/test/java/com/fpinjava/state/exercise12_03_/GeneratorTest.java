package com.fpinjava.state.exercise12_03_;

import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;
import org.junit.Test;

import static org.junit.Assert.*;


public class GeneratorTest {

  @Test
  public void testInteger() {
    RNG rng = JavaRNG.rng(0);
    Tuple<Integer, RNG> t1 = Generator.integer.apply(rng);
    assertEquals(Integer.valueOf(-1155484576), t1._1);
    Tuple<Integer, RNG> t2 = Generator.integer.apply(t1._2);
    assertEquals(Integer.valueOf(-723955400), t2._1);
    Tuple<Integer, RNG> t3 = Generator.integer.apply(t2._2);
    assertEquals(Integer.valueOf(1033096058), t3._1);
    Tuple<Integer, RNG> t4 = Generator.integer.apply(t3._2);
    assertEquals(Integer.valueOf(-1690734402), t4._1);
  }

  @Test
  public void testIntegerLimit() {
    RNG rng = JavaRNG.rng(0);
    Tuple<Integer, Tuple<RNG, Integer>> t1 = Generator.limitedInteger.apply(new Tuple<>(rng, 100));
    assertEquals(Integer.valueOf(76), t1._1);
    Tuple<Integer, Tuple<RNG, Integer>> t2 = Generator.limitedInteger.apply(new Tuple<>(rng, 100));
    assertEquals(Integer.valueOf(0), t2._1);
    Tuple<Integer, Tuple<RNG, Integer>> t3 = Generator.limitedInteger.apply(new Tuple<>(rng, 100));
    assertEquals(Integer.valueOf(58), t3._1);
  }

  @Test
  public void testIntegers() {
    RNG rng = JavaRNG.rng(0);
    Tuple<List<Integer>, Tuple<RNG, Integer>> result = Generator.integers.apply(new Tuple<>(rng, 3));
    assertEquals(List.list(1033096058, -723955400, -1155484576), result._1);
    Tuple<Integer, RNG> t = Generator.integer(result._2._1);
    assertEquals(Integer.valueOf(-1690734402), t._1);
  }

  @Test
  public void testIntegersLength1() {
    RNG rng = JavaRNG.rng(0);
    Tuple<List<Integer>, Tuple<RNG, Integer>> result = Generator.integers.apply(new Tuple<>(rng, 1));
    assertEquals(List.list(-1155484576), result._1);
    Tuple<Integer, RNG> t = Generator.integer(result._2._1);
    assertEquals(Integer.valueOf(-723955400), t._1);
  }

  @Test
  public void testIntegersLength0() {
    RNG rng = JavaRNG.rng(0);
    Tuple<List<Integer>, Tuple<RNG, Integer>> result = Generator.integers.apply(new Tuple<>(rng, 0));
    assertEquals(0, result._1.length());
    Tuple<Integer, RNG> t = Generator.integer(result._2._1);
    assertEquals(Integer.valueOf(-1155484576), t._1);
  }

  @Test
  public void testIntegersNegativeLength() {
    RNG rng = JavaRNG.rng(0);
    Tuple<List<Integer>, Tuple<RNG, Integer>> result = Generator.integers.apply(new Tuple<>(rng, -3));
    assertEquals(0, result._1.length());
    Tuple<Integer, RNG> t = Generator.integer(result._2._1);
    assertEquals(Integer.valueOf(-1155484576), t._1);
  }
}
