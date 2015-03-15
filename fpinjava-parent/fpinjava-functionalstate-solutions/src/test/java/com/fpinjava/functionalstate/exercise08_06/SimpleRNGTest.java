package com.fpinjava.functionalstate.exercise08_06;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.Tuple;

public class SimpleRNGTest {

  @Test
  public void testMap2IntDouble() {
    Tuple<Tuple<Integer, Double>, RNG> t1 = SimpleRNG.randIntDouble.apply(new Simple(1));
    assertEquals(Integer.valueOf(384748), t1._1._1);
    assertEquals(Double.valueOf(0.5360936457291245), t1._1._2);
  }

  @Test
  public void testMap2DoubleInt() {
    Tuple<Tuple<Double, Integer>, RNG> t1 = SimpleRNG.randDoubleInt.apply(new Simple(1));
    assertEquals(Double.valueOf(1.7916224896907806E-4), t1._1._1);
    assertEquals(Integer.valueOf(-1151252339), t1._1._2);
  }
  
}
