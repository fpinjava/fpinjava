package com.fpinjava.io.exercise13_06;


import com.fpinjava.common.Function;
import com.fpinjava.common.Nothing;

public interface IO<A> {

  IO<Nothing> empty = () -> Nothing.instance;

  A run();

  default <B> IO<B> map(Function<A, B> f) {
    throw new IllegalStateException("To be implemented");
  }

  static <A> IO<A> unit(A a) {
    return () -> a;
  }
}
