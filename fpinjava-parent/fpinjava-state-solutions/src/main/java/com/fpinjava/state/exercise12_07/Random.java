package com.fpinjava.state.exercise12_07;


import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;

public interface Random<A> extends Function<RNG, Tuple<A, RNG>> {

  static <A> Random<A> unit(A a) {
    return rng -> new Tuple<>(a, rng);
  }

  static <A, B> Random<B> map(Random<A> s, Function<A, B> f) {
    return rng -> {
      Tuple<A, RNG> t = s.apply(rng);
      return new Tuple<>(f.apply(t._1), t._2);
    };
  }

  static <A, B, C> Random<C> map2(Random<A> ra, Random<B> rb, Function<A, Function<B, C>> f) {
    return rng -> {
      Tuple<A, RNG> t1 = ra.apply(rng);
      Tuple<B, RNG> t2 = rb.apply(t1._2);
      return new Tuple<>(f.apply(t1._1).apply(t2._1), t2._2);
    };
  }

  static <A> Random<List<A>> sequence(List<Random<A>> rs) {
    return rs.foldLeft(unit(List.list()), acc -> r -> map2(r, acc, x -> y -> y.cons(x)));
  }

  static <A> Random<List<A>> sequence2(List<Random<A>> rs) {
    return rs.foldRight(unit(List.list()), r -> acc -> map2(r, acc, x -> y -> y.cons(x)));
  }

  static <A, B> Random<B> flatMap(Random<A> s, Function<A, Random<B>> f) {
    return rng -> {
      Tuple<A, RNG> t = s.apply(rng);
      return f.apply(t._1).apply(t._2);
    };
  }

  Random<Integer> intRnd = RNG::nextInt;

  Random<Boolean> booleanRnd = Random.map(intRnd, x -> x % 2 == 0);

  Random<Double> doubleRnd = map(intRnd, x -> x / (((double) Integer.MAX_VALUE) + 1.0));

  Random<Tuple<Integer, Integer>> intPairRnd = map2(intRnd, intRnd, x -> y -> new Tuple<>(x, y));

  Function<Integer, Random<List<Integer>>> integersRnd = length -> sequence(List.fill(length, () -> intRnd));

  Random<Integer> notMultipleOfFiveRnd = Random.flatMap(intRnd, x -> {
      int mod = x % 5;
      return mod != 0
          ? unit(x)
          : Random.notMultipleOfFiveRnd;
  });
}
