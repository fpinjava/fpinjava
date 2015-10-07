package com.fpinjava.application.generator;


public class NameGenerator extends StringGenerator {

  protected NameGenerator(long seed) {
    super(seed, x -> (x >= 'a' && x <= 'z') || (x >= 'A' && x <= 'Z' || x == ' ' || x == '-'));
  }

  protected NameGenerator() {
    super(x -> (x >= 'a' && x <= 'z') || (x >= 'A' && x <= 'Z' || x == ' ' || x == '-'));
  }

  @Override
  public String next() {
    String string = super.next();
    return string.length() > 3
        ? format(string)
        : next(); // Careful: possible stack overflow if infinite recursion. Check the depth and return Result.success or Result.failure if more than 1000 consecutive failure to satisfy predicate.

  }

  private String format(String string) {
    return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
  }
}
