package com.fpinjava.functionalstate.exercise08_09;

import java.util.Random;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.TailCall;
import com.fpinjava.common.Tuple;
import com.fpinjava.common.Tuple3;

/**
 * In this class, we have just replaced Rand with State in SimpleRNG. This
 * modification corresponds to the sentence: "We should then come up with a more
 * general type than Rand, for handling any type of state:
 * 
 * public interface State<S, A> extends Function<S, Tuple<A, S>> {}"
 * 
 * in section 8.5.
 * 
 * @author pysaumont
 */
public class SimpleState {

  public static int rollDie(Random rng) {
    return rng.nextInt(6);
  }

  public static Tuple<Integer, RNG> nonNegativeInt(RNG rng) {
    Tuple<Integer, RNG> t = rng.nextInt();
    return new Tuple<>((t._1 < 0)
        ? -(t._1 + 1)
        : t._1, t._2);
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
    if (count == 0) {
      return new Tuple<>(List.list(), rng);
    } else {
      Tuple<Integer, RNG> t1 = rng.nextInt();
      Tuple<List<Integer>, RNG> t2 = ints(count - 1, t1._2);
      return new Tuple<>(t2._1.cons(t1._1), t2._2);
    }
  }

  /*
   * A tail-recursive stack safe solution. Note that the ouptut list is in
   * reverse order, but this is perfectly acceptable regarding the requirements.
   */
  public static Tuple<List<Integer>, RNG> ints2(int count, RNG rng) {
    return ints2_(count, rng, List.list()).eval();
  }

  private static TailCall<Tuple<List<Integer>, RNG>> ints2_(int count, RNG rng, List<Integer> xs) {
    if (count == 0)
      return TailCall.ret(new Tuple<>(xs, rng));
    else {
      Tuple<Integer, RNG> t1 = rng.nextInt();
      return TailCall.sus(() -> ints2_(count - 1, t1._2, xs.cons(t1._1)));
    }
  }

  public static State<RNG, Integer> intRnd = x -> x.nextInt();

  public static <S, A> State<S, A> unit(A a) {
    return s -> new Tuple<>(a, s);
  }

  public static <S, A, B> State<S, B> map_(State<S, A> s0, Function<A, B> f) {
    return s -> {
      Tuple<A, S> t = s0.apply(s);
      return new Tuple<>(f.apply(t._1), t._2);
    };
  }

  public static State<RNG, Integer> nonNegativeInt = x -> nonNegativeInt(x);

  public static State<RNG, Integer> nonNegativeEven() {
    return SimpleState.<RNG, Integer, Integer> map(nonNegativeInt, i -> i - i
        % 2);
  }

  public static State<RNG, Double> doubleRnd = map(nonNegativeInt, x -> x
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
  public static <S, A, B, C> State<S, C> map2_(State<S, A> ra, State<S, B> rb, Function<A, Function<B, C>> f) {
    return s -> {
      Tuple<A, S> t1 = ra.apply(s);
      Tuple<B, S> t2 = rb.apply(t1._2);
      return new Tuple<>(f.apply(t1._1).apply(t2._1), t2._2);
    };
  }

  public static <S, A, B> State<S, Tuple<A, B>> both(State<S, A> ra, State<S, B> rb) {
    return map2(ra, rb, x -> y -> new Tuple<>(x, y));
  }

  public static State<RNG, Tuple<Integer, Double>> randIntDouble = both(intRnd,
      doubleRnd);

  public static State<RNG, Tuple<Double, Integer>> randDoubleInt = both(
      doubleRnd, intRnd);

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
  public static <S, A> State<S, List<A>> sequence(List<State<S, A>> fs) {
    return fs.foldRight(unit(List.list()),
        f -> acc -> map2(f, acc, x -> y -> y.cons(x)));
  }

  /*
   * It's interesting that we never actually need to talk about the `RNG` value
   * in `sequence`. This is a strong hint that we could make this function
   * polymorphic in that type.
   */
  public static State<RNG, List<Integer>> ints(int count) {
    return sequence(List.fill(count, () -> intRnd));
  }

  public static <S, A, B> State<S, B> flatMap(State<S, A> f, Function<A, State<S, B>> g) {
    return s -> {
      Tuple<A, S> t = f.apply(s);
      return g.apply(t._1).apply(t._2); // We pass the new state along
    };
  }

  public static State<RNG, Integer> nonNegativeLessThan(int n) {
    return flatMap(nonNegativeInt, i -> {
      int mod = i % n;
      return (i + (n - 1) - mod >= 0)
          ? unit(mod)
          : nonNegativeLessThan(n);
    });
  }

  public static <S, A, B> State<S, B> map(State<S, A> s, Function<A, B> f) {
    return flatMap(s, a -> unit(f.apply(a)));
  }

  public static <S, A, B, C> State<S, C> map2(State<S, A> ra, State<S, B> rb, Function<A, Function<B, C>> f) {
    return flatMap(ra, a -> map(rb, b -> f.apply(a).apply(b)));
  }

  public static State<RNG, Integer> rollDieBug = nonNegativeLessThan(6);

  public static State<RNG, Integer> rollDie = map(nonNegativeLessThan(6), x -> x + 1);
}
