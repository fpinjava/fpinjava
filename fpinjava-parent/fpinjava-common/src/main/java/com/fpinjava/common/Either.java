package com.fpinjava.common;


public abstract class Either<E, A> {

  public abstract <B> Either<E, B> map(Function<A, B> f);

  public abstract <B> Either<E, B> flatMap(Function<A, Either<E, B>> f);

  public abstract Either<E, A> orElse(Supplier<Either<E, A>> a);

  public abstract boolean isLeft();

  public abstract boolean isRight();

  public abstract E left();

  public abstract A right();

  public <B, C> Either<E, C> map2(Either<E, B> b, Function<A, Function<B, C>> f) {
    return flatMap(a -> b.map(b1 -> f.apply(a).apply(b1)));
  }

  public static <E, A, B> Either<E, List<B>> traverseRecursive(List<A> as, Function<A, Either<E, B>> f) {
    return as.isEmpty()
        ? Either.right(List.list())
        : f.apply(as.head()).map2(traverseRecursive(as.tail(), f), x -> y -> y.cons(x));
  }

  public static <E, A, B> Either<E, List<B>> traverse(List<A> as, Function<A, Either<E, B>> f) {
    return as.foldRight(right(List.list()), a -> b -> f.apply(a).map2(b, x -> y -> y.cons(x)));
  }
//
// Below are equivalent versions with explicit typing.
//
//  public static <E, A, B> Either<E, List<B>> traverse(List<A> as, Function<A, Either<E, B>> f) {
//    return as.foldRight(right(List.list()), (A a) -> (Either<E, List<B>> b) -> f.apply(a).map2(b, x -> y -> y.cons(x)));
//  }

//  public static <E, A, B> Either<E, List<B>> traverse(List<A> as, Function<A, Either<E, B>> f) {
//	  Either<E, List<B>> id = right(List.list());
//	  Function<A, Function<Either<E, List<B>>, Either<E, List<B>>>> f_ = a -> b -> f.apply(a).map2(b, x -> y -> y.cons(x));
//	   return as.foldRight(id, f_ );
//	 }

  public static <E, A> Either<E, List<A>> sequenceViaTraverseRecursive(List<Either<E, A>> es) {
    return traverseRecursive(es, x -> x);
  }

  public static <E, A> Either<E, List<A>> sequence(List<Either<E, A>> es) {
    return traverse(es, x -> x);
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

    public Either<E, A> orElse(Supplier<Either<E, A>> a) {
      return a.get();
    }

    @Override
    public String toString() {
      return String.format("Left(%s)", value);
    }

    @Override
    public boolean isLeft() {
      return true;
    }

    @Override
    public boolean isRight() {
      return false;
    }

    @Override
    public E left() {
      return this.value;
    }

    @Override
    public A right() {
      throw new IllegalStateException("getRight called on Left");
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

    public Either<E, A> orElse(Supplier<Either<E, A>> a) {
      return this;
    }

    @Override
    public String toString() {
      return String.format("Right(%s)", value);
    }

    @Override
    public boolean isLeft() {
      return false;
    }

    @Override
    public boolean isRight() {
      return true;
    }

    @Override
    public E left() {
      throw new IllegalStateException("getLeft called on Right");
    }

    @Override
    public A right() {
      return this.value;
    }
  }

  public static <E, A> Either<E, A> left(E value) {
    return new Left<>(value);
  }

  public static <E, A> Either<E, A> right(A value) {
    return new Right<>(value);
  }
}
