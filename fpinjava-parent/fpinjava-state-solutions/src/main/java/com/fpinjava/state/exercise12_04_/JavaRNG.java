package com.fpinjava.state.exercise12_04_;

import com.fpinjava.common.Tuple;

import java.util.Random;

public class JavaRNG implements RNG {

  private final Random random;
  private final int limit;
  private final int length;

  private JavaRNG(long seed) {
    this(seed, 0, 0);
  }

  private JavaRNG() {
    this(0, 0);
  }

  private JavaRNG(long seed, int limit, int length) {
    this.random = new Random(seed);
    this.limit = limit;
    this.length = length;
  }

  private JavaRNG(int limit, int length) {
    this.random = new Random();
    this.limit = limit;
    this.length = length;
  }

  @Override
  public Tuple<Integer, RNG> nextInt() {
    return new Tuple<>(random.nextInt(), this);
  }

  @Override
  public int limit() {
    return limit;
  }

  @Override
  public int length() {
    return length;
  }

  public static RNG rng(long seed) {
    return new JavaRNG(seed);
  }

  public static RNG rng() {
    return new JavaRNG();
  }

  public static RNG rng(long seed, int limit, int length) {
    return new JavaRNG(seed, limit, length);
  }

  public static RNG rng(int limit, int length) {
    return new JavaRNG(limit, length);
  }
}
