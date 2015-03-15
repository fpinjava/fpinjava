package com.fpinjava.errorhandling.exercise06_03;

import com.fpinjava.common.Function;
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
    throw new RuntimeException("To be implemented");
  }
}