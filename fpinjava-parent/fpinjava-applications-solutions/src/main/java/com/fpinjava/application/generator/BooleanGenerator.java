package com.fpinjava.application.generator;


import java.util.Random;

public class BooleanGenerator implements Generator<Boolean> {

  private final Random random;

  private final int percent;

  protected BooleanGenerator(long seed, int percentTrue) {
    this.random = new Random(seed);
    this.percent = validatePercent(percentTrue);
  }

  protected BooleanGenerator(int percentTrue) {
    this.random = new Random();
    this.percent = validatePercent(percentTrue);
  }

  private int validatePercent(int percentTrue) {
    return Math.max(0, Math.min(100, percentTrue));
  }

  @Override
  public Boolean next() {
    return Math.abs(random.nextInt()) % 100 < percent;
  }
}
