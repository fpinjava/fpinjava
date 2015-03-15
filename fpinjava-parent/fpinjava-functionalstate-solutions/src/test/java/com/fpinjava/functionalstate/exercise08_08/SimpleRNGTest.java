package com.fpinjava.functionalstate.exercise08_08;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.Tuple;

public class SimpleRNGTest {

  @Test
  public void testNonNegativeLessThan() {
    Tuple<Integer, RNG> t1 = SimpleRNG.nonNegativeLessThan(10).apply(new Simple(1));
    assertEquals(Integer.valueOf(8), t1._1);
    Tuple<Integer, RNG> t2 = SimpleRNG.nonNegativeLessThan(10).apply(t1._2);
    assertEquals(Integer.valueOf(8), t2._1);
    Tuple<Integer, RNG> t3 = SimpleRNG.nonNegativeLessThan(10).apply(t2._2);
    assertEquals(Integer.valueOf(6), t3._1);
    Tuple<Integer, RNG> t4 = SimpleRNG.nonNegativeLessThan(10).apply(t3._2);
    assertEquals(Integer.valueOf(1), t4._1);
    Tuple<Integer, RNG> t5 = SimpleRNG.nonNegativeLessThan(10).apply(t4._2);
    assertEquals(Integer.valueOf(1), t5._1);
  }

}
