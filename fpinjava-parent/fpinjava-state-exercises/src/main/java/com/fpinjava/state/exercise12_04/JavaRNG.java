package com.fpinjava.state.exercise12_04;

import com.fpinjava.common.Tuple;

import java.util.Random;

public class JavaRNG implements RNG {

  private final Random random;

  private JavaRNG(long seed) {
    this.random = new Random(seed);
  }

  private JavaRNG() {
    this.random = new Random();
  }

  @Override
  public Tuple<Integer, RNG> nextInt() {
    return new Tuple<>(random.nextInt(), this);
  }

  public static RNG rng(long seed) {
    return new JavaRNG(seed);
  }

  public static RNG rng() {
    return new JavaRNG();
  }
}
