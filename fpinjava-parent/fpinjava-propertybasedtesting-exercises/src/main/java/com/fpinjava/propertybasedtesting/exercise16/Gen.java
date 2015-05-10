package com.fpinjava.propertybasedtesting.exercise16;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Supplier;
import com.fpinjava.common.Tuple;
import com.fpinjava.parallelism.Par;
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

  public <B> Gen<B> map(Function<A, B> f) {
    return new Gen<>(sample.map(f));
  }

  public <B, C> Gen<C> map2(Gen<B> g, Function<A, Function<B, C>> f) {
    return new Gen<>(sample.map2(g.sample, f));
  }

  public <B> Gen<Tuple<A, B>> product(Gen<B> g) {
    return this.map2(g, x -> y -> new Tuple<>(x, y));
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

  public static <A> Gen<A> weighted(Tuple<Gen<A>, Double> g1, Tuple<Gen<A>, Double> g2) {
    /*
     * The probability we should pull from `g1`.
     */
    double g1Threshold = Math.abs(g1._2) / (Math.abs(g1._2) + Math.abs(g2._2));
    return new Gen<>(new State<>(SimpleRNG.doubleRnd).flatMap(d -> (d < g1Threshold) ? g1._1.sample : g2._1.sample));
  }

  public SGen<A> unsized() {
    return new SGen<>(ignore -> this);
  }

  public static Gen<Par<Integer>> PINT = Gen.choose(0, 10).map(x -> Par.unit(() -> x));

  /* A `Gen<Par<Integer>>` generated from a list summation that spawns a new parallel
   * computation for each element of the input list summed to produce the final
   * result. This is not the most compelling example, but it provides at least some
   * variation in structure to use for testing.
   */
  public static Gen<Par<Integer>> PINT2 = Gen.choose(-100,100).listOfN(Gen.choose(0,20)).map(list ->
      list.foldLeft(Par.unit(() -> 0), p -> i -> Par.fork(() -> Par.map2(p, Par.unit(() -> i), x -> y -> x + y))));

}
