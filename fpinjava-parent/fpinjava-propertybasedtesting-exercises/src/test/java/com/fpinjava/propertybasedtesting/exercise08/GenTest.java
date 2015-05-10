package com.fpinjava.propertybasedtesting.exercise08;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;
import com.fpinjava.state.SimpleRNG;

public class GenTest {

  @Test
  public void testWeighted() {
    Gen<Integer> intgen1 = Gen.choose(2000, 3000);
    Gen<Integer> intgen2 = Gen.weighted(new Tuple<>(Gen.choose(-5, 0), 0.1), new Tuple<>(Gen.choose(1, 5), 0.9));
    Gen<List<Integer>> gen = intgen2.listOfN(intgen1);
    List<Integer> list = gen.sample.run.apply(new SimpleRNG.Simple(System.currentTimeMillis()))._1;
    assertTrue(list.length() >= 2000 && list.length() < 3000);
    assertTrue(list.forAll(x -> (x >= -5 && x < 0) || (x >= 1 && x < 5)));
    int pos = list.filter(x -> x > 0).length();
    int neg = list.filter(x -> x < 0).length();
    assertTrue(pos > neg * 7);
  }

}
