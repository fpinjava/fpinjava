package com.fpinjava.propertybasedtesting.exercise05;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;
import com.fpinjava.state.SimpleRNG;

public class GenTest {

  @Test
  public void testUnit() {
    Gen<Integer> gen = Gen.unit(() -> 99);
    int i = gen.sample.run.apply(new SimpleRNG.Simple(System.currentTimeMillis()))._1;
    assertEquals(99, i);
  }

  @Test
  public void testBooleanRnd() {
    Gen<Boolean> gen = Gen.booleanRnd();
    assertTrue(gen.sample.run.apply(new SimpleRNG.Simple(0))._1);
    assertFalse(gen.sample.run.apply(new SimpleRNG.Simple(2))._1);
  }

  @Test
  public void testListOfN() {
    Gen<List<Integer>> gen = Gen.listOfN(1000, Gen.choose(0, 100));
    List<Integer> list = gen.sample.run.apply(new SimpleRNG.Simple(System.currentTimeMillis()))._1;
    assertEquals(1000, list.length());
    assertTrue(list.forAll(x -> x >= 0 && x < 100));
  }
}
