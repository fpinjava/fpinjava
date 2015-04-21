package com.fpinjava.propertybasedtesting.listing01;

import com.fpinjava.common.Function;

public interface Prop {

  public Prop and(Prop forAll);
  public boolean check();

  public static <A> Prop forAll(Gen<A> intList, Function<A, Boolean> p) {
    return null;
  }

}
