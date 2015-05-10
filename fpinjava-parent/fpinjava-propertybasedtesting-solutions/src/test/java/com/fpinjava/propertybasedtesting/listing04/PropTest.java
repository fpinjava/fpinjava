package com.fpinjava.propertybasedtesting.listing04;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;
import com.fpinjava.common.Tuple3;
import com.fpinjava.state.SimpleRNG;

public class PropTest {

  @Test
  public void testForAllSGenOfAFunctionOfABoolean() {
    Gen<Integer> smallInt = Gen.choose(-10, 10);
    Prop maxProp = Prop.forAll(SGen.listOf(smallInt), ns -> {
      int max = List.max(ns);
      return !ns.exists(x -> x > max);
    });
    Prop.Result result = maxProp.run.apply(new Tuple3<>(new Prop.MaxSize(100), new Prop.TestCases(100), new SimpleRNG.Simple(System.currentTimeMillis())));
    /*
     * The result is falsified. We will see why in a moment. Print falsified.failure.value if you are impatient to know the reason.
     */
    assertTrue(result.isFalsified());
    Prop.Falsified falsified = (Prop.Falsified) result;
    assertTrue(falsified.failure.value.startsWith("test case: [NIL]"));
  }

}
