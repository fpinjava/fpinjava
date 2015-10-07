package com.fpinjava.application.generator;


import java.util.Random;

public class IntGenerator implements Generator<Integer> {

  private final Random random;

  public IntGenerator(long seed) {
    this.random = new Random(seed);
  }

  public IntGenerator() {
    this.random = new Random();
  }

  @Override
  public Integer next() {
    return random.nextInt();
  }
}
