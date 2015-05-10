package com.fpinjava.propertybasedtesting.exercise07;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Supplier;
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
    return new Gen<>(new State<>(SimpleRNG.nonNegativeInt()).map(n -> start + n
        % (stopExclusive - start)));
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

  public static <A> Gen<A> unit(Supplier<A> a) {
    State<RNG, A> rnd = State.unit(a.get());
    return new Gen<>(rnd);
  }

  public static Gen<Boolean> booleanRnd() {
    return new Gen<>(new State<>(SimpleRNG.booleanRnd()));
  }

  public static <A> Gen<List<A>> listOfN(int n, Gen<A> g) {
    return new Gen<>(State.sequence(List.fill(n, () -> g.sample)));
  }

  public <B> Gen<B> flatMap(Function<A, Gen<B>> f) {
    return new Gen<>(sample.flatMap(a -> f.apply(a).sample));
  }

  /*
   * An instance method allowing to call the static method we defined earlier
   * with object notation.
   */
  public Gen<List<A>> listOfN(int size) {
    return listOfN(size, this);
  }

  /*
   * A version of `listOfN` that generates the size to use dynamically.
   */
  public Gen<List<A>> listOfN(Gen<Integer> size) {
    return size.flatMap(this::listOfN);
  }

  public static <A> Gen<A> union(Gen<A> g1, Gen<A> g2) {
    return booleanRnd().flatMap(b -> b ? g1 : g2);
  }
}
