package com.fpinjava.functionalstate;

import com.fpinjava.common.Tuple;
import com.fpinjava.functionalstate.listing08_02.RNG;

public class RamdomPair {

  public Tuple<Integer, Integer> randomPair(RNG rng) {
    final Tuple<Integer, RNG> t1 = rng.nextInt();
    final Tuple<Integer, RNG> t2 = t1._2.nextInt();
    return new Tuple<>(t1._1, t2._1);
  }
}
