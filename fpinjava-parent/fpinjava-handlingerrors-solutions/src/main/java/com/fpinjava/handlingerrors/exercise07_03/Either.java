package com.fpinjava.handlingerrors.exercise07_03;


import com.fpinjava.common.Function;
import com.fpinjava.common.Supplier;

public abstract class Either<E, A> {

  public abstract <B> Either<E, B> map(Function<A, B> f);

  public abstract <B> Either<E, B> flatMap(Function<A, Either<E, B>> f);

  public abstract A getOrElse(Supplier<A> defaultValue);

  public Either<E, A> orElse(Supplier<Either<E, A>> defaultValue) {
    return map(x -> this).getOrElse(defaultValue);
  }

  private static class Left<E, A> extends Either<E, A> {

    private final E value;

    private Left(E value) {
      this.value = value;
    }

    public <B> Either<E, B> map(Function<A, B> f) {
      return new Left<>(value);
    }

    public <B> Either<E, B> flatMap(Function<A, Either<E, B>> f) {
      return new Left<>(value);
    }

    @Override
    public A getOrElse(Supplier<A> defaultValue) {
      return defaultValue.get();
    }

    @Override
    public String toString() {
      return String.format("Left(%s)", value);
    }
  }

  private static class Right<E, A> extends Either<E, A> {

    private final A value;

    private Right(A value) {
      this.value = value;
    }

    public <B> Either<E, B> map(Function<A, B> f) {
      return new Right<>(f.apply(value));
    }

    public <B> Either<E, B> flatMap(Function<A, Either<E, B>> f) {
      return f.apply(value);
    }

    @Override
    public A getOrElse(Supplier<A> defaultValue) {
      return value;
    }

    @Override
    public String toString() {
      return String.format("Right(%s)", value);
    }
  }

  public static <E, A> Either<E, A> left(E value) {
    return new Left<>(value);
  }

  public static <E, A> Either<E, A> right(A value) {
    return new Right<>(value);
  }
}
