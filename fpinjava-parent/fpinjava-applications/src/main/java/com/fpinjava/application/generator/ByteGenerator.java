package com.fpinjava.application.generator;


import com.fpinjava.common.Function;

import java.util.Random;

public class ByteGenerator implements Generator<Byte> {

  private final Random random;

  private final Function<Byte, Boolean> filter;

  public ByteGenerator(long seed) {
    this.random = new Random(seed);
    this.filter = x -> true;
  }

  public ByteGenerator(long seed, Function<Byte, Boolean> filter) {
    this.random = new Random(seed);
    this.filter = filter;
  }

  public ByteGenerator() {
    this.random = new Random();
    this.filter = x -> true;
  }

  public ByteGenerator(Function<Byte, Boolean> filter) {
    this.random = new Random();
    this.filter = filter;
  }

  @Override
  public Byte next() {
    byte b =  (byte) (random.nextInt() % 256);
    return filter.apply(b)
        ? b
        : next(); // caution: if filter is always false, will stack overflow!
  }
}
