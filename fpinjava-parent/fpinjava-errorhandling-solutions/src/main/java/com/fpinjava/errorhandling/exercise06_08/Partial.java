package com.fpinjava.errorhandling.exercise06_08;

import com.fpinjava.common.List;

public abstract class Partial<A, B> {

  public static class Errors<A, B> extends Partial<A, B> {

    private final List<A> errors;

    public Errors(List<A> errors) {
      this.errors = errors;
    }
  }

  public static class Success<A, B> extends Partial<A, B> {
      
    public final B value;

    public Success(B value) {
      this.value = value;
    }
  }
}