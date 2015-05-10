package com.fpinjava.propertybasedtesting.listing03;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;
import com.fpinjava.state.SimpleRNG;

public class PropTest {

  @Test
  public void testForAll() {
    Gen<Integer> intGen = Gen.choose(10, 100);
    Gen<List<Integer>> gen = intGen.listOfN(intGen);
    Function<List<Integer>, Boolean> f = ns -> {
      int max = List.maxOption(ns).getOrElse(0);
      return !ns.exists(x -> x > max);
    };
    Prop maxProp = Prop.forAll(gen, f);
    Prop.Result result = maxProp.run.apply(new Tuple<>(new Prop.TestCases(100), new SimpleRNG.Simple(System.currentTimeMillis())));
    assertFalse(result.isFalsified());
  }

}
