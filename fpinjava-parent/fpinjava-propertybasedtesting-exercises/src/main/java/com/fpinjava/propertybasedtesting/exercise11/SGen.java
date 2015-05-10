package com.fpinjava.propertybasedtesting.exercise11;

import com.fpinjava.common.Function;

public class SGen<A> {

  public final Function<Integer, Gen<A> >forSize;

  public SGen(Function<Integer, Gen<A>> forSize) {
    super();
    this.forSize = forSize;
  }
  
  public Gen<A> apply(int n) {
    throw new IllegalStateException("To be implemented");
  }
  
  public <B> SGen<B> map(Function<A, B> f) {
    throw new IllegalStateException("To be implemented");
  }
  
  public <B> SGen<B> flatMap(Function<A, Gen<B>> f) {
    throw new IllegalStateException("To be implemented");
  }
}
