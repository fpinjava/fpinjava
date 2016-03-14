package com.fpinjava.optionaldata.exercise06_02;


import com.fpinjava.common.Supplier;

public abstract class Option<A> {

  @SuppressWarnings("rawtypes")
  private static Option none = new None();

  public abstract A getOrThrow();

  /*
   After solving the exercise, uncomment the corresponding lines in
   exercise 06_2.OptionTest
   */
  public abstract A getOrElse(A defaultValue);

  private static class None<A> extends Option<A> {

    private None() {}

    @Override
    public A getOrThrow() {
      throw new IllegalStateException("getOrThrow called on None");
    }

    @Override
    public A getOrElse(A defaultValue) {
      throw new IllegalStateException("Not implemented yet");
    }

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
    public A getOrThrow() {
      return this.value;
    }

    public A getOrElse(A defaultValue) {
      throw new IllegalStateException("Not implemented yet");
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
