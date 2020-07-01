package com.fpinjava.functions.exercise02_05;

public interface Function<T, U> {

  U apply(T arg);
  /*
  X = T
  f2 = (T, V)
  f1 = (V, U)

  F<f1, F<f2, F<X>>
  F<F<U, V>, F<F<T,U> , F<T, V>>

   */


  static <T, U, V> Function<Function<U, V>, Function<Function<T, U>, Function<T, V>>> higherCompose() {
    return f1 -> f2 -> x -> f1.apply(f2.apply(x));
  }
}
