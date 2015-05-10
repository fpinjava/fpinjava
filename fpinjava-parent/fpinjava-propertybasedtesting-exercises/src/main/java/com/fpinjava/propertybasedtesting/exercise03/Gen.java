package com.fpinjava.propertybasedtesting.exercise03;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;

public class Gen<A> {

  public static <A> Gen<A> choose(A i, A j) {
    throw new RuntimeException("Not implemented yet");
  }

  public static <A> Gen<List<A>> listOf(Gen<A> a) {
    throw new RuntimeException("Not implemented yet");
  }
  
  public static <A> Gen<List<A>> listOfN(int n, Gen<A> a) {
    throw new RuntimeException("Not implemented yet");
  }

  public static <A> Prop forAll(Gen<A> a, Function<A, Boolean> p) {
    throw new RuntimeException("Not implemented yet");
  }
}
