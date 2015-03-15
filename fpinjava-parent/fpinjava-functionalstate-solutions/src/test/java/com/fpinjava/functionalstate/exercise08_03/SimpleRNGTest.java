package com.fpinjava.functionalstate.exercise08_03;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.Tuple;
import com.fpinjava.common.Tuple3;

public class SimpleRNGTest {

  @Test
  public void testIntDouble() {
    Tuple<Tuple<Integer, Double>, RNG> t1 = SimpleRNG.intDouble(new Simple(1));
    assertEquals(Integer.valueOf(384748), t1._1._1);
    assertEquals(Double.valueOf(0.5360936457291245), t1._1._2);
  }

  @Test
  public void testDoubleInt() {
    Tuple<Tuple<Double, Integer>, RNG> t1 = SimpleRNG.doubleInt(new Simple(1));
    assertEquals(Double.valueOf(0.5360936457291245), t1._1._1);
    assertEquals(Integer.valueOf(384748), t1._1._2);
  }

  @Test
  public void testDouble3() {
    Tuple<Tuple3<Double, Double, Double>, RNG> t1 = SimpleRNG.double3(new Simple(1));
    assertEquals(Double.valueOf(1.7916224896907806E-4), t1._1._1);
    assertEquals(Double.valueOf(0.5360936457291245), t1._1._2);
    assertEquals(Double.valueOf(0.2558267889544368), t1._1._3);
  }

}
