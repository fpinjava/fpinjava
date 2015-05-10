package com.fpinjava.propertybasedtesting.exercise13;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;

public class PropTest {

  @Test
  public void testForAllSGenOfAFunctionOfABoolean() {
    Gen<Integer> smallInt = Gen.choose(-10, 10);
    
    Prop maxProp1 = Prop.forAll(SGen.listOf1(smallInt), ns -> {
      int max = List.max(ns);
      return !ns.exists(x -> x > max);
    });
    String result = maxProp1.run();
    assertTrue(result.startsWith("+ OK"));
  }

}
