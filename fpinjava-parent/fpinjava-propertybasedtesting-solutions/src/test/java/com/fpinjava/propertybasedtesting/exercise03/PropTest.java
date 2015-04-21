package com.fpinjava.propertybasedtesting.exercise03;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;

public class PropTest {


  @Test
  public void testProp() {
    
//    Gen<List<Integer>> intList = Gen.listOf(Gen.choose(0, 100));
//    Prop prop =
//      Prop.<List<Integer>>forAll(intList, ns -> ns.reverse().reverse().equals(ns)).and(
//      Prop.<List<Integer>>forAll(intList, ns -> ns.headOption().equals(ns.reverse().lastOption()))
//      );
//    
//    Prop failingProp = Prop.<List<Integer>>forAll(intList, ns -> ns.reverse().equals(ns));
//    
//    assertTrue(prop.check());
//    assertFalse(failingProp.check());
  }

}
