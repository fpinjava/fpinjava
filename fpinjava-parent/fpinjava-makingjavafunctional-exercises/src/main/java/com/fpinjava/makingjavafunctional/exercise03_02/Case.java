package com.fpinjava.makingjavafunctional.exercise03_02;

import com.fpinjava.makingjavafunctional.exercise03_01.Result;

public class Case<T> { // Case class should extend Tuple

  private Case(Supplier<Boolean> booleanSupplier, Supplier<Result<T>> resultSupplier) {
    // Call super constructor
  }

  public static <T> Case<T> mcase(Supplier<Boolean> condition, Supplier<Result<T>> value) {
    throw new RuntimeException("To be implemented.");
  }

  public static <T> DefaultCase<T> mcase(Supplier<Result<T>> value) {
    throw new RuntimeException("To be implemented.");
  }

  private static class DefaultCase<T> extends Case<T> {

    private DefaultCase(Supplier<Boolean> booleanSupplier, Supplier<Result<T>> resultSupplier) {
      super(booleanSupplier, resultSupplier);
    }
  }

  @SafeVarargs
  public static <T> Result<T> match(DefaultCase<T> defaultCase, Case<T>... matchers) {
    throw new RuntimeException("To be implemented.");
  }
}
