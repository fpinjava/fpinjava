package com.fpinjava.errorhandling.listing06_06to08;

import static com.fpinjava.errorhandling.exercise06_07.Either.left;
import static com.fpinjava.errorhandling.exercise06_07.Either.right;

import com.fpinjava.errorhandling.exercise06_07.Either;


public class Age {

  public final int value;

  private Age(int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return String.format("[Age: %s]", value);
  }

  public static Either<String, Age> age(int age) {
    return age < 0 || age > 120
        ? left("Age is out of range")
        : right(new Age(age));
  }
}