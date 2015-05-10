package com.fpinjava.propertybasedtesting.exercise04;

import com.fpinjava.state.RNG;
import com.fpinjava.state.State;

public class Gen<A> {

  public final State<RNG, A> sample;

  public Gen(State<RNG, A> sample) {
    super();
    this.sample = sample;
  }

  public static Gen<Integer> choose(int start, int stopExclusive) {
    throw new IllegalStateException("To be implemented");
  }
}
