package com.fpinjava.state.exercise12_03;

import org.junit.Test;

import static org.junit.Assert.*;

import com.fpinjava.common.Tuple;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class RandomTest {

  @Test
  public void testBoolean() throws Exception {
    RNG rng = JavaRNG.rng(0);
    Tuple<Boolean, RNG> result1 = Random.booleanRnd.apply(rng);
    assertTrue(result1._1);
    Tuple<Boolean, RNG> result2 = Random.booleanRnd.apply(result1._2);
    assertTrue(result2._1);
    Tuple<Boolean, RNG> result3 = Random.booleanRnd.apply(result2._2);
    assertTrue(result3._1);
  }
}
