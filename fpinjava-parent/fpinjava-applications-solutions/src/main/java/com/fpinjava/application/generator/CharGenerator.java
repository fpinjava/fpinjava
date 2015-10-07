package com.fpinjava.application.generator;


import com.fpinjava.common.Function;

import java.util.Random;

public class CharGenerator implements Generator<Character> {

  private final Random random;

  private final Function<Character, Boolean> filter;

  public CharGenerator(long seed) {
    this.random = new Random(seed);
    this.filter = x -> true;
  }

  public CharGenerator(long seed, Function<Character, Boolean> filter) {
    this.random = new Random(seed);
    this.filter = filter;
  }

  public CharGenerator() {
    this.random = new Random();
    this.filter = x -> true;
  }

  public CharGenerator(Function<Character, Boolean> filter) {
    this.random = new Random();
    this.filter = filter;
  }

  @Override
  public Character next() {
    char b =  (char) (random.nextInt() % 256);
    return filter.apply(b)
        ? b
        : next(); // caution: if filter is always false, will stack overflow!
  }
}
