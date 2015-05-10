package com.fpinjava.propertybasedtesting.exercise04;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.fpinjava.common.List;
import com.fpinjava.state.SimpleRNG;

public class GenTest {

  @Test
  public void testChooseIntInt() {
    List.range(0, 10000).forEach(x -> {
      Gen<Integer> gen = Gen.choose(0, 10);
      Integer i = gen.sample.run.apply(new SimpleRNG.Simple(System.currentTimeMillis()))._1;
      assertTrue(i >= 0 && i < 10);
    });
  }

  @Test
  public void testChoose2() {
    List.range(0, 10000).forEach(x -> {
      Gen<Integer> gen = Gen.choose2(0, 10);
      Integer i = gen.sample.run.apply(new SimpleRNG.Simple(System.currentTimeMillis()))._1;
      assertTrue(i >= 0 && i < 10);
    });
  }

}
