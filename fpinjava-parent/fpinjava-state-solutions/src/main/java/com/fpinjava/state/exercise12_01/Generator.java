package com.fpinjava.state.exercise12_01;


import com.fpinjava.common.Function;
import com.fpinjava.common.Tuple;

public interface Generator<T> {

  Tuple<T, JavaRNG> next();

  default <U> Generator<U> map(Function<T, U> f) {
    return () -> f.apply(Generator.this.next());
  }

  default <U> Generator<U> flatMap(Function<T, Generator<U>> f) {
    return () -> f.apply(Generator.this.next()).next();
  }

  public static <T> Generator<T> unit(T t) {
    return () -> t;
  }

}
