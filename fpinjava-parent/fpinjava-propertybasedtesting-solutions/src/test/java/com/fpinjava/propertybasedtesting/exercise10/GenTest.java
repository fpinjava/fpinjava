package com.fpinjava.propertybasedtesting.exercise10;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;
import com.fpinjava.state.SimpleRNG;

public class GenTest {

  @Test
  public void testUnsized() {
    Gen<Integer> intgen1 = Gen.choose(2000, 3000);
    Gen<Integer> intgen2 = Gen.choose(-5, 5);
    Gen<List<Integer>> gen = intgen2.listOfN(intgen1).unsized().forSize.apply(0);
    List<Integer> list = gen.sample.run.apply(new SimpleRNG.Simple(System.currentTimeMillis()))._1;
    assertTrue(list.length() >= 2000 && list.length() < 3000);
    assertTrue(list.forAll(x -> x >= -5 && x < 5));
  }

}
