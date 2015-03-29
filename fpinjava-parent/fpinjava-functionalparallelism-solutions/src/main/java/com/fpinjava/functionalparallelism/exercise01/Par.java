package com.fpinjava.functionalparallelism.exercise01;

import com.fpinjava.common.Function;

public class Par<A> {

  public static <A, B, C> Par<C> map2(Par<A> a, Par<B> b, Function<A, Function<B, C>> f) {
    throw new IllegalStateException("No implementation in this exercise");
  }
}
