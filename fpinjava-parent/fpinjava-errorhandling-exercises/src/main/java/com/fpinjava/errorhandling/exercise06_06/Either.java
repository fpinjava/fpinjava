package com.fpinjava.errorhandling.exercise06_06;

import com.fpinjava.common.Function;
import com.fpinjava.common.Supplier;

public abstract class Either<E, A> {

  public abstract <B> Either<E, B> map(Function<A, B> f);

  public abstract <B> Either<E, B> flatMap(Function<A, Either<E, B>> f);

  public abstract Either<E, A> orElse(Supplier<Either<E, A>> a);

  public <B, C> Either<E, C> map2(Either<E, B> b, Function<A, Function<B, C>> f) {
    throw new RuntimeException("To be implemented");
  }

  private static class Left<E, A> extends Either<E, A> {

    private final E value;

    private Left(E value) {
      this.value = value;
    }

    public <B> Either<E, B> map(Function<A, B> f) {
      throw new RuntimeException("To be implemented");
    }

    public <B> Either<E, B> flatMap(Function<A, Either<E, B>> f) {
      throw new RuntimeException("To be implemented");
    }

    public Either<E, A> orElse(Supplier<Either<E, A>> a) {
      throw new RuntimeException("To be implemented");
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
      throw new RuntimeException("To be implemented");
    }

    public <B> Either<E, B> flatMap(Function<A, Either<E, B>> f) {
      throw new RuntimeException("To be implemented");
    }

    public Either<E, A> orElse(Supplier<Either<E, A>> a) {
      throw new RuntimeException("To be implemented");
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