package com.fpinjava.functionalparallelism.listing02;

import com.fpinjava.common.Supplier;


public class Par<A> {

  public static <A> Par<A> unit(Supplier<A> a) {
    throw new IllegalStateException("What could be the implementation?");
 }
 
  public static <A> A get(Par<A> a) {
    throw new IllegalStateException("What could be the implementation?");
 }

}
