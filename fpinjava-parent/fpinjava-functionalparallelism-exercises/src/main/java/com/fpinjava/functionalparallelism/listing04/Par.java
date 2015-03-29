package com.fpinjava.functionalparallelism.listing04;

import com.fpinjava.common.Function;
import com.fpinjava.common.Supplier;

public class Par<A> {

  public static <A> Par<A> unit(Supplier<A> a) {
    throw new IllegalStateException();
  }
  
  public static <A, B, C> Par<C> map2(Par<A> a, Par<B> b, Function<A, Function<B, C>> f) {
    throw new IllegalStateException();
  }
  
  public static <A> Par<A> fork(Supplier<Par<A>> a) {
    throw new IllegalStateException();
  }
  
  public static <A> Par<A> lazyUnit(Supplier<A> a) {
    throw new IllegalStateException();
  }
  
  public static <A> A run(Par<A> a) {
    throw new IllegalStateException();
  }
}
