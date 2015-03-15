package com.fpinjava.functionalstate.exercise08_01;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.Tuple;

public class SimpleRNGTest {

  @Test
  public void testNonNegativeInt() {
    Tuple<Integer, RNG> t1 = SimpleRNG.nonNegativeInt(new Simple(1));
    assertEquals(Integer.valueOf(384748), t1._1);
    Tuple<Integer, RNG> t2 = SimpleRNG.nonNegativeInt(t1._2);
    assertEquals(Integer.valueOf(1151252338), t2._1);
  }

}
