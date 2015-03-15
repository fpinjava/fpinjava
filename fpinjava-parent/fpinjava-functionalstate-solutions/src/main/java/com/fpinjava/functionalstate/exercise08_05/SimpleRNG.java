package com.fpinjava.functionalstate.exercise08_05;

import java.util.Random;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.TailCall;
import com.fpinjava.common.Tuple;
import com.fpinjava.common.Tuple3;

public class SimpleRNG {

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
  
  public static Rand<Integer> intRnd = x -> x.nextInt();
  
  public static <A> Rand<A> unit(A a) {
    return rng -> new Tuple<>(a, rng);
  }
  
  public static <A, B> Rand<B> map(Rand<A> s, Function<A, B> f) {
    return rng -> {
        Tuple<A, RNG> t = s.apply(rng);
        return new Tuple<>(f.apply(t._1), t._2);
      };
  }
  
  public static Rand<Integer> nonNegativeInt = x -> nonNegativeInt(x);
  
  public static Rand<Integer> nonNegativeEven() {
    return SimpleRNG.<Integer, Integer>map(nonNegativeInt, i -> i - i % 2);
  }
  
  public static Rand<Double> doubleRnd = map(nonNegativeInt, x -> x / (((double) Integer.MAX_VALUE) + 1.0));
}
