package com.fpinjava.propertybasedtesting.exercise11;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.state.SimpleRNG;

public class SGenTest {

  @Test
  public void testMap() {
    Gen<Integer> intGen1 = Gen.choose(2000, 3000);
    Gen<Integer> intGen2 = Gen.choose(-5, 5);
    Gen<Integer> gen = intGen2.listOfN(intGen1).unsized().map(ns -> ns.foldRight(0, x -> y -> x + y)).apply(0);
    Integer i = gen.sample.run.apply(new SimpleRNG.Simple(System.currentTimeMillis()))._1;
    assertTrue(i >= 3000 * -5  && i < 3000 * 5);
  }

  @Test
  public void testFlatMap() {
    Gen<Integer> intGen1 = Gen.choose(2000, 3000);
    Gen<Integer> intGen2 = Gen.choose(-5, 5);
    Gen<Integer> gen = intGen2.listOfN(intGen1).unsized().flatMap(ns -> Gen.unit(() -> ns.foldRight(0, x -> y -> x + y))).apply(0);
    Integer i = gen.sample.run.apply(new SimpleRNG.Simple(System.currentTimeMillis()))._1;
    assertTrue(i >= 3000 * -5  && i < 3000 * 5);
  }

}
