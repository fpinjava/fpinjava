package com.fpinjava.errorhandling.listing06_02;

public abstract class Option<A> {

  @SuppressWarnings("rawtypes")
  private static Option none = new None();

  public abstract A get();

  private Option() {}

  private static class None<A> extends Option<A> {

    @Override
    public A get() {
      throw new IllegalStateException("get called on None");
    }

    private None() {}

    @Override
    public String toString() {
      return "None";
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

    @Override
    public String toString() {
      return String.format("Some(%s)", this.value);
    }
}

  public static <A> Option<A> some(A a) {
    return new Some<>(a);
  }

  @SuppressWarnings("unchecked")
  public static <A> Option<A> none() {
    return none;
  }
}