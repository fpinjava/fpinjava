package com.fpinjava.functions.exercise02_08;

public class FunctionExamples {

  public static <A, B, C> Function<A, C> partialB(B b, Function<A, Function<B, C>> f) {
    return a -> f.apply(a).apply(b);
  }
}
