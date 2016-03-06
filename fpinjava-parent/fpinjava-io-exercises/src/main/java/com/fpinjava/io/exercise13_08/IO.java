package com.fpinjava.io.exercise13_08;


import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Nothing;

public interface IO<A> {

  IO<Nothing> empty = () -> Nothing.instance;

  A run();

  default <B> IO<B> map(Function<A, B> f) {
    return () -> f.apply(this.run());
  }

  default <B> IO<B> flatMap(Function<A, IO<B>> f) {
    return () -> f.apply(this.run()).run();
  }

  static <B> IO<B> unit(B b) {
    return () -> b;
  }

  static <A, B, C> IO<C> map2(IO<A> ioa, IO<B> iob, Function<A, Function<B, C>> f) {
    return ioa.flatMap(a -> iob.map(b -> f.apply(a).apply(b)));
  }

  static <A> IO<List<A>> repeat(int n, IO<A> io) {
    throw new IllegalStateException("To be implemented");
  }
}
