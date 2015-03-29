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
  
  public abstract boolean isSuccess();
  
  public abstract boolean isFailure();
  
  public abstract boolean isEmpty();

  public abstract Try<Exception> forEachOrException(Effect<V> ef);

  public abstract void forEach(Effect<V> ef);

  private static class Failure<V> extends Empty<V> {

    private final String message;

    private final Option<Exception> exception;

    public Failure(String message) {
      super();
      this.message = message;
      this.exception = Option.none();
    }

    public Failure(String message, Exception e) {
      super();
      this.message = message;
      this.exception = Option.some(e);
    }

    @Override
    public V getOrElse(final V defaultValue) {
      return defaultValue;
    }

    @Override
    public V getOrThrow() {
      throw this.exception.isSome()
          ? new IllegalStateException(exception.get())
          : new IllegalStateException(message);
    }
    
    @Override
    public String toString() {
      return String.format("Failure(%s)", message);
    }
    
    @Override
    public Try<Exception> forEachOrException(Effect<V> c) {
      return exception.isSome()
          ? success(exception.get())
          : success(new IllegalStateException(this.message));
    }


    @Override
    public boolean isSuccess() {
      return false;
    }

    @Override
    public boolean isFailure() {
      return true;
    }

    @Override
    public boolean isEmpty() {
      return false;
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

    @Override
    public String toString() {
      return "Empty()";
    }

    @Override
    public Try<Exception> forEachOrException(Effect<V> c) {
      return success(new IllegalStateException("Empty Try"));
    }

    @Override
    public void forEach(Effect<V> ef) {
      // Do nothing
    }

    @Override
    public boolean isSuccess() {
      return false;
    }

    @Override
    public boolean isFailure() {
      return false;
    }

    @Override
    public boolean isEmpty() {
      return true;
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
    
    @Override
    public String toString() {
      return String.format("Success(%s)", value.toString());
    }

    @Override
    public Try<Exception> forEachOrException(Effect<V> ef) {
      ef.apply(value);
      return failure("No exception");
    }

    @Override
    public void forEach(Effect<V> ef) {
      ef.apply(value);
    }

    @Override
    public boolean isSuccess() {
      return true;
    }

    @Override
    public boolean isFailure() {
      return false;
    }

    @Override
    public boolean isEmpty() {
      return false;
    }
  }
}
