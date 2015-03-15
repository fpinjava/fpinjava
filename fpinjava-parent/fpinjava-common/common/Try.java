package com.fpinjava.common;

import java.io.Serializable;
import java.util.concurrent.Callable;

@SuppressWarnings("serial")
public abstract class Try<V> implements Serializable {

  private Try() {
  }

  public abstract V getOrElse(final V defaultValue);

  public abstract V getOrThrow();

  public static <V> Try<V> failure(String message, Exception e) {
    return new Failure<>(message, e);
  }

  public static <V> Try<V> failure(String message) {
    return new Failure<>(message);
  }

  public static <V> Try<V> success(V value) {
    return new Success<>(value);
  }

  private static class Failure<V> extends Empty<V> {

    private final String message;

    private Exception exception;

    public Failure(String message) {
      super();
      this.message = message;
    }

    public Failure(String message, Exception e) {
      super();
      this.message = message;
      this.exception = e;
    }

    @Override
    public V getOrElse(final V defaultValue) {
      return defaultValue;
    }

    @Override
    public V getOrThrow() {
      throw this.exception != null
          ? new IllegalStateException(exception)
          : new IllegalStateException(message);
    }
  }
  
  public static <V> Try<V> of(Callable<V> callable, String message) {
    try {
      V value = callable.call();
      return value == null
          ? Try.failure(message)
          : Try.success(value);
    } catch (Exception e) {
      return Try.failure(e.getMessage(), e);
    }
  }

  private static class Empty<V> extends Try<V> {

    public Empty() {
      super();
    }

    @Override
    public V getOrElse(final V defaultValue) {
      return defaultValue;
    }

    @Override
    public V getOrThrow() {
      throw new IllegalStateException("Empty eval");
    }

  }

  private static class Success<V> extends Try<V> {

    private final V value;

    public Success(V value) {
      super();
      this.value = value;
    }

    @Override
    public V getOrElse(final V defaultValue) {
      return this.value;
    }

    @Override
    public V getOrThrow() {
      return this.value;
    }
  }
}
