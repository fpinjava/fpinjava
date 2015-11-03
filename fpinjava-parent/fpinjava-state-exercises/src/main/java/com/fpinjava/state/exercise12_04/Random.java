package com.fpinjava.state.exercise12_04;


import com.fpinjava.common.Function;
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

  Random<Integer> intRnd = RNG::nextInt;

  Random<Boolean> booleanRnd = Random.map(intRnd, x -> x % 2 == 0);

  Random<Double> doubleRnd = null; // To be implemented

}
