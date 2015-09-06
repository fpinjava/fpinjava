package com.fpinjava.advancedlisthandling.exercise08_24;

import com.fpinjava.common.Function;
import com.fpinjava.common.TailCall;
import com.fpinjava.common.Tuple;
import com.fpinjava.common.Tuple3;
import com.fpinjava.state.RNG;

import java.util.Random;

public class SimpleRNG {

  public interface Rand<A> extends Function<RNG, Tuple<A, RNG>> {}

  public static class Simple implements RNG {

    private final long seed;

    public Simple(long seed) {
      super();
      this.seed = seed;
    }

    @Override
    public Tuple<Integer, RNG> nextInt() {
      final long newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL;
      final RNG nextRNG = new Simple(newSeed);
      final int  n = (int) (newSeed >>> 16);
      return new Tuple<>(n, nextRNG);
    }
  }

  public static int rollDie(Random rng) {
    return rng.nextInt(6);
  }

  public static Tuple<Integer, RNG> nonNegativeInt(RNG rng) {
    Tuple<Integer, RNG> t = rng.nextInt();
    return new Tuple<>((t._1 < 0)
        ? -(t._1 + 1)
        : t._1, t._2);
  }

  public static Function<RNG, Tuple<Integer, RNG>> nonNegativeInt() {
    return SimpleRNG::nonNegativeInt;
  }

  public static Tuple<Boolean, RNG> booleanRnd(RNG rng) {
    Tuple<Integer, RNG> t = rng.nextInt();
    return new Tuple<>(t._1 % 2 == 0, t._2);
  }

  public static Function<RNG, Tuple<Boolean, RNG>> booleanRnd() {
    return SimpleRNG::booleanRnd;
  }

  /*
   * We generate an integer >= 0 and divide it by one higher than the maximum.
   * This is just one possible solution.
   */
  public static Tuple<Double, RNG> doubleRnd(RNG rng) {
    Tuple<Integer, RNG> t = nonNegativeInt(rng);
    return new Tuple<>(t._1 / ((double) Integer.MAX_VALUE + 1), t._2);
  }

  public static Tuple<Tuple<Integer, Double>, RNG> intDouble(RNG rng) {
    Tuple<Integer, RNG> t1 = rng.nextInt();
    Tuple<Double, RNG> t2 = doubleRnd(t1._2);
    return new Tuple<>(new Tuple<>(t1._1, t2._1), t2._2);
  }

  public static Tuple<Tuple<Double, Integer>, RNG> doubleInt(RNG rng) {
    Tuple<Tuple<Integer, Double>, RNG> t = intDouble(rng);
    return new Tuple<>(new Tuple<>(t._1._2, t._1._1), t._2);
  }

  public static Tuple<Tuple3<Double, Double, Double>, RNG> double3(RNG rng) {
    Tuple<Double, RNG> t1 = doubleRnd(rng);
    Tuple<Double, RNG> t2 = doubleRnd(t1._2);
    Tuple<Double, RNG> t3 = doubleRnd(t2._2);
    return new Tuple<>(new Tuple3<>(t1._1, t2._1, t3._1), t3._2);
  }

  /*
   * A simple recursive solution
   */
  public static Tuple<List<Integer>, RNG> ints(int count, RNG rng) {
    return count == 0
        ? new Tuple<>(List.list(), rng)
        : intsHelper(count, rng);
  }

  private static Tuple<List<Integer>, RNG> intsHelper(int count, RNG rng) {
    Tuple<Integer, RNG> t1 = rng.nextInt();
    Tuple<List<Integer>, RNG> t2 = ints(count - 1, t1._2);
    return new Tuple<>(t2._1.cons(t1._1), t2._2);
  }

  /*
   * A tail-recursive stack safe solution. Note that the output list is in
   * reverse order, but this is perfectly acceptable regarding the requirements.
   */
  public static Tuple<List<Integer>, RNG> ints2(int count, RNG rng) {
    return ints2(count, rng, List.list()).eval();
  }

  private static TailCall<Tuple<List<Integer>, RNG>> ints2(int count, RNG rng, List<Integer> xs) {
    return count == 0
        ? TailCall.ret(new Tuple<>(xs, rng))
        : ints2Helper(count, rng, xs);
  }

  private static TailCall<Tuple<List<Integer>, RNG>> ints2Helper(int count, RNG rng, List<Integer> xs) {
    Tuple<Integer, RNG> t1 = rng.nextInt();
    return TailCall.sus(() -> ints2(count - 1, t1._2, xs.cons(t1._1)));
  }

  /*
   * A tail-recursive stack safe solution. Note that the output list is in
   * reverse order, but this is perfectly acceptable regarding the requirements.
   */
  public static Tuple<List<Double>, RNG> doubles(int count, RNG rng) {
    return doubles(count, rng, List.list()).eval();
  }

  private static TailCall<Tuple<List<Double>, RNG>> doubles(int count, RNG rng, List<Double> xs) {
    return count == 0
        ? TailCall.ret(new Tuple<>(xs, rng))
        : doublesHelper(count, rng, xs);
  }

  private static TailCall<Tuple<List<Double>, RNG>> doublesHelper(int count, RNG rng, List<Double> xs) {
    Tuple<Double, RNG> t1 = doubleRnd(rng);
    return TailCall.sus(() -> doubles(count - 1, t1._2, xs.cons(t1._1)));
  }

  public static Rand<Integer> intRnd = RNG::nextInt;

  public static <A> Rand<A> unit(A a) {
    return rng -> new Tuple<>(a, rng);
  }

  public static <A, B> Rand<B> map_(Rand<A> s, Function<A, B> f) {
    return rng -> {
      Tuple<A, RNG> t = s.apply(rng);
      return new Tuple<>(f.apply(t._1), t._2);
    };
  }

  //public static Rand<Integer> nonNegativeInt = SimpleRNG::nonNegativeInt;

  public static Rand<Integer> nonNegativeEven() {
    return SimpleRNG.<Integer, Integer> map(SimpleRNG::nonNegativeInt, i -> i - i % 2);
  }

  public static Rand<Double> doubleRnd = map(SimpleRNG::nonNegativeInt, x -> x
      / (((double) Integer.MAX_VALUE) + 1.0));

  /*
   * This implementation of map2 passes the initial RNG to the first argument
   * and the resulting RNG to the second argument. It's not necessarily wrong to
   * do this the other way around, since the results are random anyway. We could
   * even pass the initial RNG to both `f` and `g`, but that might have
   * unexpected results. E.g. if both arguments are `RNG.int` then we would
   * always get two of the same `Int` in the result. When implementing functions
   * like this, it's important to consider how we would test them for
   * correctness.
   */
  public static <A, B, C> Rand<C> map2_(Rand<A> ra, Rand<B> rb, Function<A, Function<B, C>> f) {
    return rng -> {
      Tuple<A, RNG> t1 = ra.apply(rng);
      Tuple<B, RNG> t2 = rb.apply(t1._2);
      return new Tuple<>(f.apply(t1._1).apply(t2._1), t2._2);
    };
  }

  public static <A, B> Rand<Tuple<A, B>> both(Rand<A> ra, Rand<B> rb) {
    return map2(ra, rb, x -> y -> new Tuple<>(x, y));
  }

  public static Rand<Tuple<Integer, Double>> randIntDouble = both(intRnd, doubleRnd);

  public static Rand<Tuple<Double, Integer>> randDoubleInt = both(doubleRnd, intRnd);

  /*
   * In `sequence`, the base case of the fold is a `unit` action that returns
   * the empty list. At each step in the fold, we accumulate in `acc` and `f` is
   * the current element in the list. `map2(f, acc)(_ :: _)` results in a value
   * of type `Rand[List[A]]` We map over that to prepend (cons) the element onto
   * the accumulated list.
   *
   * We are using `foldRight`. If we used `foldLeft` then the values in the
   * resulting list would appear in reverse order. It would be arguably better
   * to use `foldLeft` followed by `reverse`. What do you think?
   */
  public static <A> Rand<List<A>> sequence(List<Rand<A>> fs) {
    return fs.foldRight(unit(List.list()),
        f -> acc -> map2(f, acc, x -> y -> y.cons(x)));
  }

  /*
   * It's interesting that we never actually need to talk about the `RNG` value
   * in `sequence`. This is a strong hint that we could make this function
   * polymorphic in that type.
   */
  public static Rand<List<Integer>> ints(int count) {
    return sequence(List.fill(count, () -> intRnd));
  }

  public static <A, B> Rand<B> flatMap(Rand<A> f, Function<A, Rand<B>> g) {
    return rng -> {
      Tuple<A, RNG> t = f.apply(rng);
      return g.apply(t._1).apply(t._2); // We pass the new input along
    };
  }

  public static Rand<Integer> nonNegativeLessThan(int n) {
    return flatMap(SimpleRNG::nonNegativeInt, i -> {
      int mod = i % n;
      return (i + (n - 1) - mod >= 0)
          ? unit(mod)
          : nonNegativeLessThan(n);
    });
  }

  public static <A, B> Rand<B> map(Rand<A> s, Function<A, B> f) {
    return flatMap(s, a -> unit(f.apply(a)));
  }

  public static <A, B, C> Rand<C> map2(Rand<A> ra, Rand<B> rb, Function<A, Function<B, C>> f) {
    return flatMap(ra, a -> map(rb, b -> f.apply(a).apply(b)));
  }

  public static Rand<Integer> rollDieBug = nonNegativeLessThan(6);

  public static Rand<Integer> rollDie = map(nonNegativeLessThan(6), x -> x + 1);
}
