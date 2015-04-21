package com.fpinjava.propertybasedtesting.listing01;

import com.fpinjava.common.List;


public class Listing01 {

  public void main(String... args) {
    
    Gen<List<Integer>> intList = Gen.listOf(Gen.choose(0, 100)); //#A
    Prop prop =  //#B
      Prop.<List<Integer>>forAll(intList, ns -> ns.reverse().reverse().equals(ns)).and( // #C
      Prop.<List<Integer>>forAll(intList, ns -> ns.headOption().equals(ns.reverse().lastOption()))); // #D
    Prop failingProp = Prop.<List<Integer>>forAll(intList, ns -> ns.reverse().equals(ns)); // #E
    prop.check();
    failingProp.check();
  }
}
