package com.fpinjava.functions.exercise02_06;


public interface Function<T, U> {

  U apply(T arg);

  public static <T, U, V> Function<Function<U, V>, Function<Function<T, U>, Function<T, V>>> higherCompose() {
    return x -> y -> z -> x.apply(y.apply(z));
  }

  public static <T, U, V> Function<Function<T, U>, Function<Function<U, V>, Function<T, V>>> higherAndThen() {
    throw new RuntimeException("To be implemented.");
  }
}
