package com.fpinjava.errorhandling.listing06_06to08;

import static com.fpinjava.errorhandling.exercise06_07.Either.left;
import static com.fpinjava.errorhandling.exercise06_07.Either.right;

import com.fpinjava.errorhandling.exercise06_07.Either;

public class Name {

  public final String value;

  private Name(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return String.format("[Name: %s]", value);
  }

  public static Either<String, Name> name(String name) {
    return name == null || "".equals(name)
        ? left("Name is empty")
        : right(new Name( name));
  }
}