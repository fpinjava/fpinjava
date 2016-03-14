package com.fpinjava.state.exercise12_08;

import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;
import org.junit.Test;

import static org.junit.Assert.*;


public class RandomTest {

  @Test
  public void testMap() throws Exception {
    RNG rng = JavaRNG.rng(0);
    Tuple<Boolean, RNG> result1 = Random.booleanRnd.apply(rng);
    assertTrue(result1._1);
    Tuple<Boolean, RNG> result2 = Random.booleanRnd.apply(result1._2);
    assertTrue(result2._1);
    Tuple<Boolean, RNG> result3 = Random.booleanRnd.apply(result2._2);
    assertTrue(result3._1);
  }

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
