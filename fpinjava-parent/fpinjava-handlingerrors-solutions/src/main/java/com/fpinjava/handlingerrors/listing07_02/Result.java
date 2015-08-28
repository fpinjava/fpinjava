package com.fpinjava.handlingerrors.listing07_02;


import java.io.Serializable;

public abstract class Result<V> implements Serializable {

  private Result() {
  }

  private static class Failure<V> extends Result<V> {

    private final RuntimeException exception;

    private Failure(String message) {
      super();
      this.exception = new IllegalStateException(message);
    }

    private Failure(RuntimeException e) {
      super();
      this.exception = e;
    }

    private Failure(Exception e) {
      super();
      this.exception = new IllegalStateException(e);
    }

    @Override
    public String toString() {
      return String.format("Failure(%s)", exception.getMessage());
    }
  }

  private static class Success<V> extends Result<V> {

    private final V value;

    private Success(V value) {
      super();
      this.value = value;
    }

    @Override
    public String toString() {
      return String.format("Success(%s)", value.toString());
    }
  }

  public static <V, U> Result<V> failure(Failure<U> failure) {
    return new Failure<>(failure.exception);
  }

  public static <V> Result<V> failure(String message) {
    return new Failure<>(message);
  }

  public static <V> Result<V> failure(Exception e) {
    return new Failure<V>(e);
  }

  public static <V> Result<V> failure(RuntimeException e) {
    return new Failure<V>(e);
  }

  public static <V> Result<V> success(V value) {
    return new Success<>(value);
  }
}
