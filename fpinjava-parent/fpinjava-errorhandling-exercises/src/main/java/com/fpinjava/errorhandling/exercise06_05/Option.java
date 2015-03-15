package com.fpinjava.errorhandling.exercise06_05;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Supplier;

public abstract class Option<A> {

  @SuppressWarnings("rawtypes")
  private static Option none = new None();

  public abstract A get();

  public abstract <B> Option<B> map(Function<A, B> f);

  public abstract A getOrElse(Supplier<A> defaultValue);

  public abstract boolean isSome();

  public <B> Option<B> flatMap(Function<A, Option<B>> f) {
    return map(f).getOrElse(None::new);
  }

  public Option<A> orElse(Supplier<Option<A>> defaultValue) {
    return map(Option::some).getOrElse(defaultValue);
  }

  public Option<A> filter(Function<A, Boolean> f) {
    return flatMap(x -> f.apply(x)
        ? some(x)
        : none());
  }

  private Option() {
  }

  private static class None<A> extends Option<A> {

    private None() {
    }

    @Override
    public A get() {
      throw new IllegalStateException("get called on None");
    }

    @Override
    public <B> Option<B> map(Function<A, B> f) {
      return none();
    }

    @Override
    public A getOrElse(Supplier<A> defaultValue) {
      return defaultValue.get();
    }

    @Override
    public String toString() {
      return "None";
    }

    @Override
    public boolean isSome() {
      return false;
    }
  }

  private static class Some<A> extends Option<A> {

    private final A value;

    private Some(A a) {
      value = a;
    }

    @Override
    public A get() {
      return this.value;
    }

    public <B> Option<B> map(Function<A, B> f) {
      return new Some<>(f.apply(this.value));
    }

    public A getOrElse(Supplier<A> defaultValue) {
      return this.value;
    }

    @Override
    public String toString() {
      return String.format("Some(%s)", this.value);
    }

    @Override
    public boolean isSome() {
      return true;
    }
  }

  public static <A> Option<A> some(A a) {
    return new Some<>(a);
  }

  @SuppressWarnings("unchecked")
  public static <A> Option<A> none() {
    return none;
  }

  public static <A, B, C> Option<C> map2(Option<A> a, Option<B> b, Function<A, Function<B, C>> f) {
    return a.isSome() && b.isSome()
        ? some(f.apply(a.get()).apply(b.get()))
        : none();
  }
  
  public static <A> Option<List<A>> sequence(List<Option<A>> list) {
    return list.isEmpty()
        ? some(List.list())
        : list.head().flatMap(hh -> sequence(list.tail()).map(x -> x.cons(hh)));
  }
  
  public static <A> Option<List<A>> sequenceViaFoldRight(List<Option<A>> list) {
    return list.foldRight(some(List.list()), x -> y -> map2(x, y, a -> b -> b.cons(a)));
  }
  
  public static <A, B> Option<List<B>> traverse(List<A> list, Function<A, Option<B>> f) {
    throw new RuntimeException("To be implemented");
  }

  public static <A, B> Option<List<B>> traverseViaFoldRight(List<A> list, Function<A, Option<B>> f) {
    throw new RuntimeException("To be implemented");
  }

  public static <A> Option<List<A>> sequenceViaTraverse(List<Option<A>> list) {
    throw new RuntimeException("To be implemented");
  }

  public static <A> Option<List<A>> sequenceViaTraverseViaFoldRight(List<Option<A>> list) {
    throw new RuntimeException("To be implemented");
  }
}