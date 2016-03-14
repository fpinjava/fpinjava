package com.fpinjava.state.exercise12_02;


import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;

public class Generator {

  public static Tuple<Integer, RNG> integer(RNG rng) {
    return rng.nextInt();
  }

  public static Tuple<Integer, RNG> integer(RNG rng, int limit) {
    Tuple<Integer, RNG> random = rng.nextInt();
    return new Tuple<>(Math.abs(random._1 % limit), random._2);
  }

  public static Tuple<List<Integer>, RNG> integers(RNG rng, int length) {
    throw new IllegalStateException("To be implemented");
  }
}
