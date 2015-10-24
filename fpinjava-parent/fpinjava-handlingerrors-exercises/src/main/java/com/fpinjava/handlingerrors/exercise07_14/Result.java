package com.fpinjava.handlingerrors.exercise07_14;


import com.fpinjava.common.Effect;
import com.fpinjava.common.Function;
import com.fpinjava.common.Supplier;

import java.io.Serializable;
import java.util.Objects;

public abstract class Result<T> implements Serializable {

  @SuppressWarnings("rawtypes")
  private static Result empty = new Empty();

  private Result() {
  }

  public abstract T getOrElse(final T defaultValue);

  public abstract T getOrElse(final Supplier<T> defaultValue);

  public abstract <U> Result<U> map(Function<T, U> f);

  public abstract <U> Result<U> flatMap(Function<T, Result<U>> f);

  public abstract Result<T> mapFailure(String s);

  public abstract Result<T> mapFailure(String s, Exception e);

  public abstract Result<T> mapFailure(Exception e);

  public abstract Result<T> failIfEmpty(String message);

  public abstract void forEach(Effect<T> ef);

  public abstract void forEachOrThrow(Effect<T> ef);

  public abstract Result<RuntimeException> forEachOrException(Effect<T> ef);

  public Result<T> orElse(Supplier<Result<T>> defaultValue) {
    return map(x -> this).getOrElse(defaultValue);
  }

  public Result<T> filter(Function<T, Boolean> p) {
    return flatMap(x -> p.apply(x)
        ? this
        : failure("Condition not matched"));
  }

  public Result<T> filter(Function<T, Boolean> p, String message) {
    return flatMap(x -> p.apply(x)
        ? this
        : failure(message));
  }

  public boolean exists(Function<T, Boolean> p) {
    return map(p).getOrElse(false);
  }

  private static class Empty<T> extends Result<T> {

    public Empty() {
      super();
    }

    @Override
    public T getOrElse(final T defaultValue) {
      return defaultValue;
    }

    @Override
    public <U> Result<U> map(Function<T, U> f) {
      return empty();
    }

    @Override
    public <U> Result<U> flatMap(Function<T, Result<U>> f) {
      return empty();
    }

    @Override
    public Result<T> mapFailure(String s) {
      return this;
    }

    @Override
    public Result<T> mapFailure(String s, Exception e) {
      return this;
    }

    @Override
    public Result<T> mapFailure(Exception e) {
      return this;
    }

    @Override
    public Result<T> failIfEmpty(String message) {
      return failure(message);
    }

    @Override
    public void forEach(Effect<T> ef) {
      // Do nothing
    }

    @Override
    public void forEachOrThrow(Effect<T> ef) {
      // Do nothing
    }

    @Override
    public Result<RuntimeException> forEachOrException(Effect<T> ef) {
      return empty();
    }

    @Override
    public String toString() {
      return "Empty()";
    }

    @Override
    public T getOrElse(Supplier<T> defaultValue) {
      return defaultValue.get();
    }

    /**
     * There is only one instance of Empty, so all Empty are equals.
     */
    @Override
    public boolean equals(Object o) {
      return this == o;
    }

    @Override
    public int hashCode() {
      return 0;
    }
  }

  private static class Failure<T> extends Empty<T> {

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
    public <U> Result<U> map(Function<T, U> f) {
      return failure(exception);
    }

    @Override
    public <U> Result<U> flatMap(Function<T, Result<U>> f) {
      return failure(exception);
    }

    @Override
    public Result<T> mapFailure(String s) {
        return failure(new IllegalStateException(s, exception));
    }

    @Override
    public Result<T> mapFailure(String s, Exception e) {
      return failure(new IllegalStateException(s, e));
    }

    @Override
    public Result<T> mapFailure(Exception e) {
      return failure(e);
    }

    @Override
    public Result<T> failIfEmpty(String message) {
      return failure(message);
    }

    @Override
    public void forEachOrThrow(Effect<T> ef) {
      throw exception;
    }

    @Override
    public Result<RuntimeException> forEachOrException(Effect<T> ef) {
      return success(exception);
    }

    /**
     * Failures are equals only if they are the same object.
     */
    @Override
    public boolean equals(Object o) {
      return this == o;
    }

    /**
     * We need a better hashCode method returning a different hashCode for
     * all failures. See http://code.google.com/p/smhasher and:or the Guava
     * Hashing class for examples.
     */
    @Override
    public int hashCode() {
      return this.exception.hashCode();
    }
  }

  private static class Success<T> extends Result<T> {

    private final T value;

    private Success(T value) {
      super();
      this.value = value;
    }

    @Override
    public String toString() {
      return String.format("Success(%s)", value.toString());
    }

    @Override
    public T getOrElse(T defaultValue) {
      return value;
    }

    @Override
    public T getOrElse(Supplier<T> defaultValue) {
      return value;
    }

    @Override
    public <U> Result<U> map(Function<T, U> f) {
      return success(f.apply(value));
    }

    @Override
    public <U> Result<U> flatMap(Function<T, Result<U>> f) {
      return f.apply(value);
    }

    @Override
    public Result<T> mapFailure(String s) {
      return this;
    }

    @Override
    public Result<T> mapFailure(String s, Exception e) {
      return this;
    }

    @Override
    public Result<T> mapFailure(Exception e) {
      return this;
    }

    @Override
    public Result<T> failIfEmpty(String message) {
      return this;
    }

    @Override
    public void forEach(Effect<T> ef) {
      ef.apply(value);
    }

    @Override
    public void forEachOrThrow(Effect<T> ef) {
      ef.apply(value);
    }

    @Override
    public Result<RuntimeException> forEachOrException(Effect<T> ef) {
      ef.apply(value);
      return empty();
    }

    @Override
    public boolean equals(Object o) {
      return (this == o || o instanceof Success)
          && this.value.equals(((Success<?>) o).value);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(value);
    }
  }

  public static <T> Result<T> failure(String message) {
    return new Failure<>(message);
  }

  public static <T> Result<T> failure(Exception e) {
    return new Failure<>(e);
  }

  public static <T> Result<T> failure(RuntimeException e) {
    return new Failure<>(e);
  }

  public static <T> Result<T> success(T value) {
    return new Success<>(value);
  }

  @SuppressWarnings("unchecked")
  public static <T> Result<T> empty() {
    return empty;
  }

  public static <T> Result<T> of(T value) {
    return value != null
        ? success(value)
        : empty();
  }

  public static <T> Result<T> of(T value, String message) {
    return value != null
        ? success(value)
        : failure(message);
  }

  public static <T> Result<T> of(Function<T, Boolean> predicate, T value) {
    try {
      return predicate.apply(value)
          ? success(value)
          : empty();
    } catch (Exception e) {
      String errMessage =
          String.format("Exception while evaluating predicate: %s", value);
      return Result.failure(new IllegalStateException(errMessage, e));
    }
  }

  public static <T> Result<T> of(Function<T, Boolean> predicate,
                                 T value, String message) {
    try {
      return predicate.apply(value)
          ? Result.success(value)
          : Result.failure(String.format(message, value));
    } catch (Exception e) {
      String errMessage =
          String.format("Exception while evaluating predicate: %s",
              String.format(message, value));
      return Result.failure(new IllegalStateException(errMessage, e));
    }
  }

  public static <A, B> Function<Result<A>, Result<B>> lift(final Function<A, B> f) {
    return x -> x.map(f);
  }

  public static <A, B, C> Function<Result<A>, Function<Result<B>, Result<C>>> lift2(Function<A, Function<B, C>> f) {
    return a -> b -> a.map(f).flatMap(b::map);
  }

  public static <A, B, C, D> Function<Result<A>, Function<Result<B>, Function<Result<C>, Result<D>>>> lift3(Function<A, Function<B, Function<C, D>>> f) {
    return a -> b -> c -> a.map(f).flatMap(b::map).flatMap(c::map);
  }

  public static <A, B, C> Result<C> map2(Result<A> a, Result<B> b, Function<A, Function<B, C>> f) {
    throw new RuntimeException("To be implemented");
  }
}
