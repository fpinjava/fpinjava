package com.fpinjava.propertybasedtesting.listing05;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;

public class PropTest {

  @Test
  public void testForAllSGenOfAFunctionOfABoolean() {
    Gen<Integer> smallInt = Gen.choose(-10, 10);
    Prop maxProp = Prop.forAll(SGen.listOf(smallInt), ns -> {
      int max = List.max(ns);
      return !ns.exists(x -> x > max);
    });
    String result = maxProp.run();
    /*
     * The result is falsified. We will see why in a moment. Print falsified.failure.value if you are impatient to know the reason.
     */
    assertTrue(result.startsWith("! Falsified"));
  }

}
