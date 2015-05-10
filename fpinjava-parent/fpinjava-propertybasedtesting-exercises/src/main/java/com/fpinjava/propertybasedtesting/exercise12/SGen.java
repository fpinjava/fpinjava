package com.fpinjava.propertybasedtesting.exercise12;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;

public class SGen<A> {

  public final Function<Integer, Gen<A> >forSize;

  public SGen(Function<Integer, Gen<A>> forSize) {
    super();
    this.forSize = forSize;
  }

  public Gen<A> apply(int n) {
    return forSize.apply(n);
  }

  public <B> SGen<B> map(Function<A, B> f) {
    return new SGen<>(forSize.andThen(x -> x.map(f)));
  }

  public <B> SGen<B> flatMap(Function<A, Gen<B>> f) {
    return new SGen<>(forSize.andThen(x -> x.flatMap(f)));
  }

  public static <A> SGen<List<A>> listOf(Gen<A> g) {
    throw new IllegalStateException("To be implemented");
  }
}
