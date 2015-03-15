package com.fpinjava.errorhandling.listing06_05;

public abstract class Either<E, A> {

  private static class Left<E, A> extends Either<E, A> {
    
    private final E value;

    private Left(E value) {
      this.value = value;
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