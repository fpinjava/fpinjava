package com.fpinjava.handlingerrors.listing07_06;


import com.fpinjava.common.Function;
import com.fpinjava.common.Supplier;

import java.io.Serializable;

public abstract class Result<V> implements Serializable {

  @SuppressWarnings("rawtypes")
  private static Result empty = new Empty();

  private Result() {
  }

  public abstract V getOrElse(final V defaultValue);

  public abstract V getOrElse(final Supplier<V> defaultValue);

  public abstract <U> Result<U> map(Function<V, U> f);

  public abstract <U> Result<U> flatMap(Function<V, Result<U>> f);

  public Result<V> orElse(Supplier<Result<V>> defaultValue) {
    return map(x -> this).getOrElse(defaultValue);
  }

  private static class Empty<V> extends Result<V> {

    public Empty() {
      super();
    }

    @Override
    public V getOrElse(final V defaultValue) {
      return defaultValue;
    }

    @Override
    public <U> Result<U> map(Function<V, U> f) {
      return empty();
    }

    @Override
    public <U> Result<U> flatMap(Function<V, Result<U>> f) {
      return empty();
    }

    @Override
    public String toString() {
      return "Empty()";
    }

    @Override
    public V getOrElse(Supplier<V> defaultValue) {
      return defaultValue.get();
    }
  }

  private static class Failure<V> extends Empty<V> {

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
      this.exception = new IllegalStateException(e.getMessage(), e);
    }

    @Override
    public String toString() {
      return String.format("Failure(%s)", exception.getMessage());
    }

    @Override
    public <U> Result<U> map(Function<V, U> f) {
      return failure(exception);
    }

    @Override
    public <U> Result<U> flatMap(Function<V, Result<U>> f) {
      return failure(exception);
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

    @Override
    public V getOrElse(V defaultValue) {
      return value;
    }

    @Override
    public V getOrElse(Supplier<V> defaultValue) {
      return value;
    }

    @Override
    public <U> Result<U> map(Function<V, U> f) {
      return success(f.apply(value));
    }

    @Override
    public <U> Result<U> flatMap(Function<V, Result<U>> f) {
      return f.apply(value);
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

  @SuppressWarnings("unchecked")
  public static <V> Result<V> empty() {
    return empty;
  }
}
