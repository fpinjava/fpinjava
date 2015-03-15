package com.fpinjava.functionalstate.exercise08_01;

import java.util.Random;

import com.fpinjava.common.Tuple;

public class SimpleRNG {

  public static int rollDie(Random rng)  {
    return rng.nextInt(6);
  }
  
  public static Tuple<Integer, RNG> nonNegativeInt(RNG rng) {
    Tuple<Integer, RNG> t = rng.nextInt();
    return new Tuple<>((t._1 < 0) ? -(t._1 + 1) : t._1, t._2);
  }
}
