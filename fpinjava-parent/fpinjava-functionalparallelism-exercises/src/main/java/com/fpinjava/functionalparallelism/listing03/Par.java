package com.fpinjava.functionalparallelism.listing03;

import com.fpinjava.common.Function;
import com.fpinjava.common.Supplier;

public class Par<A> {

  public static <A> Par<A> unit(Supplier<A> a) {
    throw new IllegalStateException("What could be the implementation?");
 }
 
  public static <A> A get(Par<A> a) {
    throw new IllegalStateException("What could be the implementation?");
 }

  public static <A, B, C> Par<C> map2(Par<A> a, Par<B> b, Function<A, Function<B, C>> f) {
    throw new IllegalStateException("What could be the implementation?");
  }
  
  public static <A> Par<A> unit(A a) {
    throw new IllegalStateException("What could be the implementation?");
  }
}
