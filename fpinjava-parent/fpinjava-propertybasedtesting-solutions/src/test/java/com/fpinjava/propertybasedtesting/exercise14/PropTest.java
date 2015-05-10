package com.fpinjava.propertybasedtesting.exercise14;

import static org.junit.Assert.*;

import org.junit.Test;


public class PropTest {

  @Test
  public void testForAllSGenOfAFunctionOfABoolean() {
    Gen<Integer> smallInt = Gen.choose(-10, 10);
    
    Prop maxProp = Prop.forAll(SGen.listOf(smallInt), ns -> {
      int sum1 = ns.foldLeft(0, x -> y -> x + y);
      int sum2 = ns.reverse().foldLeft(0, x -> y -> x + y);
      return sum1 == sum2;
    });
    String result = maxProp.run();
    assertTrue(result.startsWith("+ OK"));
  }
}
