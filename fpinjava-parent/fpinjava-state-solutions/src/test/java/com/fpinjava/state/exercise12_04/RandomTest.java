package com.fpinjava.state.exercise12_04;

import com.fpinjava.common.Tuple;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class RandomTest {

  @Test
  public void testDoubleRnd() throws Exception {
    RNG rng = JavaRNG.rng(0);
    Tuple<Double, RNG> result1 = Random.doubleRnd.apply(rng);
    assertTrue(Math.abs(-0.5380644351243973 - result1._1) < 0.0001);
    Tuple<Double, RNG> result2 = Random.doubleRnd.apply(result1._2);
    assertTrue(Math.abs(-0.3371180035173893 - result2._1) < 0.0001);
    Tuple<Double, RNG> result3 = Random.doubleRnd.apply(result2._2);
    assertTrue(Math.abs(0.48107284028083086 - result3._1) < 0.0001);
  }
}
