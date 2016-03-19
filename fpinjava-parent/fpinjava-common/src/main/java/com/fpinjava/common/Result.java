package com.fpinjava.common;

import com.fpinjava.io.IO;

import java.io.Serializable;
import java.util.concurrent.Callable;


public abstract class Result<T> implements Serializable {

  private Result() {
  }

  public abstract Boolean isSuccess();
  public abstract Boolean isFailure();
  public abstract Boolean isEmpty();
  public abstract T getOrElse(final T defaultValue);
  public abstract T getOrElse(final Supplier<T> defaultValue);
  public abstract <V> V foldLeft(final V identity, Function<V, Function<T, V>> f);
  public abstract <V> V foldRight(final V identity, Function<T, Function<V, V>> f);
  public abstract T successValue();
  public abstract Exception failureValue();
  public abstract void forEach(Effect<T> c);
  public abstract void forEachOrThrow(Effect<T> c);
  public abstract Result<String> forEachOrFail(Effect<T> e);
  public abstract Result<RuntimeException> forEachOrException(Effect<T> e);
  public abstract Result<T> filter(Function<T, Boolean> f);
  public abstract Result<T> filter(Function<T, Boolean> p, String message);
  public abstract <U> Result<U> map(Function<T, U> f);
  public abstract Result<T> mapFailure(String s, Exception e);
  public abstract Result<T> mapFailure(String s);
  public abstract Result<T> mapFailure(Exception e);
  public abstract Result<T> mapFailure(Result<T> v);
  public abstract Result<Nothing> mapEmpty();
  public abstract <U> Result<U> flatMap(Function<T, Result<U>> f);
  public abstract Boolean exists(Function<T, Boolean> f);
  public abstract IO<Nothing> tryIO(Function<T, IO<Nothing>> success, Function<String, IO<Nothing>> failure);

  public Result<T> orElse(Supplier<Result<T>> defaultValue) {
    return map(x -> this).getOrElse(defaultValue);
  }

  public static <T, U> Result<T> failure(Failure<U> failure) {
    return new Failure<>(failure.exception);
  }

  public static <T> Result<T> failure(String message) {
    return new Failure<>(message);
  }

  public static <T> Result<T> failure(String message, Exception e) {
    return new Failure<>(new IllegalStateException(message, e));
  }

  public static <V> Result<V> failure(Exception e) {
    return new Failure<>(e);
  }

  public static <T> Result<T> success(T value) {
    return new Success<>(value);
  }

  public static <T> Result<T> empty() {
    return new Empty<>();
  }

  public static <T> Result<T> flatten(Result<Result<T>> result) {
    return result.flatMap(x -> x);
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
      this.exception = new IllegalStateException(e);
    }

    @Override
    public Boolean isSuccess() {
      return false;
    }

    @Override
    public Boolean isFailure() {
      return true;
    }

    @Override
    public T getOrElse(final T defaultValue) {
      return defaultValue;
    }

    @Override
    public T successValue() {
      throw new IllegalStateException("Method successValue() called on a Failure instance");
    }

    @Override
    public RuntimeException failureValue() {
      return this.exception;
    }

    @Override
    public void forEachOrThrow(Effect<T> c) {
      throw exception;
    }

    @Override
    public Result<RuntimeException> forEachOrException(Effect<T> c) {
      return success(exception);
    }

    @Override
    public Result<String> forEachOrFail(Effect<T> c) {
      return success(exception.getMessage());
    }

    @Override
    public Result<T> filter(Function<T, Boolean> f) {
      return failure(this);
    }

    @Override
    public Result<T> filter(Function<T, Boolean> p, String message) {
      return failure(this);
    }

    @Override
    public <U> Result<U> map(Function<T, U> f) {
      return failure(this);
    }

    @Override
    public Result<T> mapFailure(String s, Exception e) {
      return failure(s, e);
    }

    @Override
    public Result<T> mapFailure(String s) {
      return failure(s, exception);
    }

    @Override
    public Result<T> mapFailure(Exception e) {
      return failure(e.getMessage(), e);
    }

    @Override
    public Result<T> mapFailure(Result<T> v) {
      return v;
    }

    @Override
    public Result<Nothing> mapEmpty() {
      return failure(this);
    }

    @Override
    public <U> Result<U> flatMap(Function<T, Result<U>> f) {
      return failure(exception.getMessage(), exception);
    }

    @Override
    public String toString() {
      return String.format("Failure(%s)", failureValue());
    }

    @Override
    public Boolean exists(Function<T, Boolean> f) {
      return false;
    }

    @Override
    public T getOrElse(Supplier<T> defaultValue) {
      return defaultValue.get();
    }

    @Override
    public IO<Nothing> tryIO(Function<T, IO<Nothing>> success, Function<String, IO<Nothing>> failure) {
      return failure.apply(exception.getMessage());
    }
  }

  private static class Empty<T> extends Result<T> {

    public Empty() {
      super();
    }

    @Override
    public Boolean isSuccess() {
      return false;
    }

    @Override
    public Boolean isFailure() {
      return false;
    }

    @Override
    public Boolean isEmpty() {
      return true;
    }

    @Override
    public T getOrElse(final T defaultValue) {
      return defaultValue;
    }

    @Override
    public T successValue() {
      throw new IllegalStateException("Method successValue() called on a Empty instance");
    }

    @Override
    public RuntimeException failureValue() {
      throw new IllegalStateException("Method failureMessage() called on a Empty instance");
    }

    @Override
    public void forEach(Effect<T> c) {
      /* Empty. Do nothing. */
    }

    @Override
    public void forEachOrThrow(Effect<T> c) {
      /* Do nothing */
    }

    @Override
    public Result<String> forEachOrFail(Effect<T> c) {
      return empty();
    }

    @Override
    public Result<RuntimeException> forEachOrException(Effect<T> c) {
      return empty();
    }

    @Override
    public Result<T> filter(Function<T, Boolean> f) {
      return empty();
    }

    @Override
    public Result<T> filter(Function<T, Boolean> p, String message) {
      return empty();
    }

    @Override
    public <U> Result<U> map(Function<T, U> f) {
      return empty();
    }

    @Override
    public Result<T> mapFailure(String s, Exception e) {
      return failure(s, e);
    }

    @Override
    public Result<T> mapFailure(String s) {
      return failure(s);
    }

    @Override
    public Result<T> mapFailure(Exception e) {
      return failure(e.getMessage(), e);
    }

    @Override
    public Result<T> mapFailure(Result<T> v) {
      return v;
    }

    @Override
    public Result<Nothing> mapEmpty() {
      return success(Nothing.instance);
    }

    @Override
    public <U> Result<U> flatMap(Function<T, Result<U>> f) {
      return empty();
    }

    @Override
    public String toString() {
      return "Empty()";
    }

    @Override
    public Boolean exists(Function<T, Boolean> f) {
      return false;
    }

    @Override
    public T getOrElse(Supplier<T> defaultValue) {
      return defaultValue.get();
    }

    @Override
    public <V> V foldLeft(V identity, Function<V, Function<T, V>> f) {
      return identity;
    }

    @Override
    public <V> V foldRight(V identity, Function<T, Function<V, V>> f) {
      return identity;
    }

    @Override
    public IO<Nothing> tryIO(Function<T, IO<Nothing>> success, Function<String, IO<Nothing>> failure) {
      return failure.apply("Empty Result");
    }
  }

  private static class Success<T> extends Result<T> {

    private final T value;

    public Success(T value) {
      super();
      this.value = value;
    }

    @Override
    public Boolean isSuccess() {
      return true;
    }

    @Override
    public Boolean isFailure() {
      return false;
    }

    @Override
    public Boolean isEmpty() {
      return false;
    }

    @Override
    public T getOrElse(final T defaultValue) {
      return successValue();
    }

    @Override
    public T successValue() {
      return this.value;
    }

    @Override
    public RuntimeException failureValue() {
      throw new IllegalStateException("Method failureValue() called on a Success instance");
    }

    @Override
    public void forEach(Effect<T> e) {
      e.apply(this.value);
    }

    @Override
    public void forEachOrThrow(Effect<T> e) {
      e.apply(this.value);
    }

    @Override
    public Result<String> forEachOrFail(Effect<T> e) {
      e.apply(this.value);
      return empty();
    }

    @Override
    public Result<RuntimeException> forEachOrException(Effect<T> e) {
      e.apply(this.value);
      return empty();
    }

    @Override
    public Result<T> filter(Function<T, Boolean> p) {
      return filter(p, "Unmatched predicate with no error message provided.");
    }

    @Override
    public Result<T> filter(Function<T, Boolean> p, String message) {
      try {
        return p.apply(successValue())
            ? this
            : failure(message);
      } catch (Exception e) {
        return failure(e.getMessage(), e);
      }
    }

    @Override
    public <U> Result<U> map(Function<T, U> f) {
      try {
        return success(f.apply(successValue()));
      } catch (Exception e) {
        return failure(e.getMessage(), e);
      }
    }

    @Override
    public Result<T> mapFailure(String f, Exception e) {
      return this;
    }

    @Override
    public Result<T> mapFailure(String s) {
      return this;
    }

    @Override
    public Result<T> mapFailure(Exception e) {
      return this;
    }

    @Override
    public Result<T> mapFailure(Result<T> v) {
      return this;
    }

    @Override
    public Result<Nothing> mapEmpty() {
      return failure("Not empty");
    }

    @Override
    public <U> Result<U> flatMap(Function<T, Result<U>> f) {
      try {
        return f.apply(successValue());
      } catch (Exception e) {
        return failure(e.getMessage());
      }
    }

    @Override
    public String toString() {
      return String.format("Success(%s)", successValue().toString());
    }

    @Override
    public Boolean exists(Function<T, Boolean> f) {
      return f.apply(successValue());
    }

    @Override
    public T getOrElse(Supplier<T> defaultValue) {
      return successValue();
    }

    @Override
    public <V> V foldLeft(V identity, Function<V, Function<T, V>> f) {
      return f.apply(identity).apply(successValue());
    }

    @Override
    public <V> V foldRight(V identity, Function<T, Function<V, V>> f) {
      return f.apply(successValue()).apply(identity);
    }

    @Override
    public IO<Nothing> tryIO(Function<T, IO<Nothing>> success, Function<String, IO<Nothing>> failure) {
      return success.apply(this.value);
    }
  }

  public static <T> Result<T> of(final Callable<T> callable) {
    return of(callable, "Null value");
  }

  public static <T> Result<T> of(final Callable<T> callable,
                                 final String message) {
    try {
      T value = callable.call();
      return value == null
          ? Result.failure(message)
          : Result.success(value);
    } catch (Exception e) {
      return Result.failure(e.getMessage(), e);
    }
  }

  public static <T> Result<T> of(final Function<T, Boolean> predicate,
                                 final T value,
                                 final String message) {
    try {
      return predicate.apply(value)
          ? Result.success(value)
          : Result.failure(String.format(message, value));
    } catch (Exception e) {
      String errMessage = String.format("Exception while evaluating predicate: %s", String.format(message, value));
      return Result.failure(errMessage, e);
    }
  }

  public static <T> Result<T> of(final T value) {
    return value != null
        ? success(value)
        : Result.failure("Null value");
  }

  public static <T> Result<T> of(final T value, final String message) {
    return value != null
        ? Result.success(value)
        : Result.failure(message);
  }

  public static <A, B> Function<Result<A>, Result<B>> lift(final Function<A, B> f) {
    return x -> x.map(f);
  }

  public static <A, B, C> Function<Result<A>, Function<Result<B>, Result<C>>> lift2(final Function<A, Function<B, C>> f) {
    return a -> b -> a.map(f).flatMap(b::map);
  }

  public static <A, B, C, D> Function<Result<A>, Function<Result<B>, Function<Result<C>, Result<D>>>> lift3(final Function<A, Function<B, Function<C, D>>> f) {
    return a -> b -> c -> a.map(f).flatMap(b::map).flatMap(c::map);
  }

  @SuppressWarnings("unchecked")
  public static <A, B, C> Result<C> map2_(final Result<A> a,
                                         final Result<B> b,
                                         final Function<A, Function<B, C>> f) {
    return a.isSuccess()
        ? b.isSuccess()
            ? Result.of(() -> f.apply(a.successValue()).apply(b.successValue()))
            : Result.failure((Failure<C>) b)
        : b.isSuccess()
            ? Result.failure((Failure<C>) a)
            : Result.failure(String.format("%s, %s", a.failureValue(), b.failureValue()));
  }

  public static <A, B, C> Result<C> map2(final Result<A> a,
                                         final Result<B> b,
                                         final Function<A, Function<B, C>> f) {
    return lift2(f).apply(a).apply(b);
  }

  public static <A> Result<A> unfold(A a, Function<A, Result<A>> f) {
    Result<A> ra = Result.success(a);
    return unfold(new Tuple<>(ra, ra), f).eval()._2;
  }

  public static <A> TailCall<Tuple<Result<A>, Result<A>>> unfold(Tuple<Result<A>, Result<A>> a, Function<A, Result<A>> f) {
    Result<A> x = a._2.flatMap(f::apply);
    return x.isSuccess()
        ? TailCall.sus(() -> unfold(new Tuple<>(a._2, x), f))
        : TailCall.ret(a);
  }
}
