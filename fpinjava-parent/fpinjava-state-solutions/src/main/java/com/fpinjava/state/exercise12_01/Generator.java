package com.fpinjava.state.exercise12_01;


import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import com.fpinjava.common.Tuple;

public class Generator {

  public static Tuple<Integer, RNG> integer(RNG rng) {
    return rng.nextInt();
  }

  public static Tuple<Integer, RNG> integer(RNG rng, int limit) {
    Tuple<Integer, RNG> random = rng.nextInt();
    return new Tuple<>(Math.abs(random._1 % limit), random._2);
  }
}
