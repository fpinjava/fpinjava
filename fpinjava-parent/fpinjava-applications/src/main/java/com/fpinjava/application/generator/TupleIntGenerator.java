package com.fpinjava.application.generator;


import com.fpinjava.common.Tuple;

import java.util.Random;

public class TupleIntGenerator implements Generator<Tuple<Integer, Integer>> {

  private final Random random;

  protected TupleIntGenerator(long seed) {
    this.random = new Random(seed);
  }

  protected TupleIntGenerator() {
    this.random = new Random();
  }

  @Override
  public Tuple<Integer, Integer> next() {
    return new Tuple<>(random.nextInt(), random.nextInt());
  }
}
