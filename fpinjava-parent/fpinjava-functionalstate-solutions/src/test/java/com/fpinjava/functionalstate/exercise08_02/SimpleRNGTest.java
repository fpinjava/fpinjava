package com.fpinjava.functionalstate.exercise08_02;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.Tuple;

public class SimpleRNGTest {

  @Test
  public void testDoubleRnd() {
    Tuple<Double, RNG> t1 = SimpleRNG.doubleRnd(new Simple(1));
    assertEquals(Double.valueOf(1.7916224896907806E-4), t1._1);
    Tuple<Double, RNG> t2 = SimpleRNG.doubleRnd(t1._2);
    assertEquals(Double.valueOf(0.5360936457291245), t2._1);
  }

}
