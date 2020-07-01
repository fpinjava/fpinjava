package com.fpinjava.functions.exercise02_06;


public interface Function<T, U> {

  U apply(T arg);

  static <T, U, V> Function<Function<U, V>, Function<Function<T, U>, Function<T, V>>> higherCompose() {
    return f -> g -> x -> f.apply(g.apply(x));
  }
  /*
  X = T
  f = (T, U)
  g = (U, V)

  F<f, F<g, F<X>>
  F<F<T,U> , F<F<U,V>, F<T,V>
   */

  static <T, V, U> Function<Function<T, U>, Function<Function<U, V>, Function<T, V>>> higherAndThen() {
    return f ->   g -> x -> g.apply(f.apply(x));
  }
}
