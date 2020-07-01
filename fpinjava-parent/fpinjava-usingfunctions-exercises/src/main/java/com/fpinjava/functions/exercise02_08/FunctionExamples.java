package com.fpinjava.functions.exercise02_08;

public class FunctionExamples {

  public static <A, B, C> Function<A, C> partialB(B b, Function<A, Function<B, C>> f) {
    return arg -> f.apply(arg).apply(b);
  }
}
