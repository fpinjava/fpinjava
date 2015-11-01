package com.fpinjava.state.exercise12_05;

import org.junit.Test;

import static org.junit.Assert.*;

import com.fpinjava.common.Tuple;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class RandomTest {

  @Test
  public void testMap2() {
    RNG rng = JavaRNG.rng(0);
    Tuple<Tuple<Integer, Integer>, RNG> result = Random.intPairRnd.apply(rng);
    assertEquals(Integer.valueOf(-1155484576), result._1._1);
    assertEquals(Integer.valueOf(-723955400), result._1._2);
    Tuple<Integer, RNG> t = Random.intRnd.apply(result._2);
    assertEquals(Integer.valueOf(1033096058), t._1);
  }
}
