package com.fpinjava.propertybasedtesting.exercise06;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;
import com.fpinjava.state.SimpleRNG;

public class GenTest {

  @Test
  public void testListOfNGenOfInteger() {
    Gen<Integer> intgen = Gen.choose(10, 100);
    Gen<List<Integer>> gen = intgen.listOfN(intgen);
    List<Integer> list = gen.sample.run.apply(new SimpleRNG.Simple(System.currentTimeMillis()))._1;
    assertTrue(list.length() >= 10 && list.length() < 100);
    assertTrue(list.forAll(x -> x >= 0 && x < 100));
  }

}
