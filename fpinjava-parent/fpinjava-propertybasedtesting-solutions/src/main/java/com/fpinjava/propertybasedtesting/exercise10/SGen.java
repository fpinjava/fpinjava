package com.fpinjava.propertybasedtesting.exercise10;

import com.fpinjava.common.Function;

public class SGen<A> {

  public final Function<Integer, Gen<A> >forSize;

  public SGen(Function<Integer, Gen<A>> forSize) {
    super();
    this.forSize = forSize;
  }
}
