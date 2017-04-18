package com.fpinjava.state.exercise12_03;

import com.fpinjava.common.Tuple;


public class JavaRNG implements RNG {

  private final long seed;

  private JavaRNG(long seed) {
    this.seed = seed;
  }

  private JavaRNG() {
    this(System.currentTimeMillis());
  }

  private long nextSeed(long seed) {
    return (seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
  }

  @Override
  public Tuple<Integer, RNG> nextInt() {
    return new Tuple<>((int)(seed >>> 16), new JavaRNG(nextSeed(seed)));
  }

  public static RNG rng(long seed) {
    return new JavaRNG(seed ^ 0x5DEECE66DL & ((1L << 48) - 1));
  }

  public static RNG rng() {
    return new JavaRNG();
  }
}