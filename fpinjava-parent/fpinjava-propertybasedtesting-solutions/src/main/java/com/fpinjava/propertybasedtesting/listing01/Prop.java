package com.fpinjava.propertybasedtesting.listing01;

import com.fpinjava.common.Function;

public interface Prop {

  Prop and(Prop forAll);
  boolean check();

  static <A> Prop forAll(Gen<A> intList, Function<A, Boolean> p) {
    return null;
  }

}
