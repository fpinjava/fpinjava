package com.fpinjava.propertybasedtesting.listing01;

import com.fpinjava.common.List;


public class Listing01 {

  public void main(String... args) {

    Gen<List<Integer>> intList = Gen.listOf(Gen.choose(0, 100));
    Prop prop =
      Prop.forAll(intList, ns -> ns.reverse().reverse().equals(ns)).and(
      Prop.forAll(intList, ns -> ns.headOption().equals(ns.reverse().lastOption())));
    Prop failingProp = Prop.forAll(intList, ns -> ns.reverse().equals(ns));
    prop.check();
    failingProp.check();
  }
}
