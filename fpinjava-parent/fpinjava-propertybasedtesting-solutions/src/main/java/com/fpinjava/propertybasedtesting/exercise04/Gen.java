package com.fpinjava.propertybasedtesting.exercise04;

import com.fpinjava.common.Tuple;
import com.fpinjava.state.RNG;
import com.fpinjava.state.SimpleRNG;
import com.fpinjava.state.State;

public class Gen<A> {

  public final State<RNG, A> sample;

  public Gen(State<RNG, A> sample) {
    super();
    this.sample = sample;
  }

  public static Gen<Integer> choose(int start, int stopExclusive) {
    return new Gen<>(new State<>(SimpleRNG.nonNegativeInt()).map(n -> start + n % (stopExclusive - start)));
  }

  /*
   * We could write this as an explicit state action, but this is far less
   * convenient, since it requires us to manually thread the `RNG` through the
   * computation.
   */
  public static Gen<Integer> choose2(int start, int stopExclusive) {
    return new Gen<>(new State<>(rng -> {
      Tuple<Integer, RNG> tuple = SimpleRNG.nonNegativeInt(rng);
      return new Tuple<>(start + tuple._1 % (stopExclusive - start), tuple._2);
    }));
  }
}
