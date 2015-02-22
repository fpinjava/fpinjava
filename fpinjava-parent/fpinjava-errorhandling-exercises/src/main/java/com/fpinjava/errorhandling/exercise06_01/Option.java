package com.fpinjava.errorhandling.exercise06_01;

import com.fpinjava.common.Function;
import com.fpinjava.common.Supplier;


public abstract class Option<A> {

  @SuppressWarnings("rawtypes")
  private static Option none = new None();

  public abstract A get();
  public abstract <B> Option<B> map(Function<A, B> f);
  public abstract A getOrElse(Supplier<A> defaultValue);
  
  public <B> Option<B> flatMap(Function<A, Option<B>> f) {
    throw new RuntimeException("To be implemented");
  }

  public Option<A> orElse(Supplier<Option<A>> defaultValue) {
    throw new RuntimeException("To be implemented");
  }

  public Option<A> filter(Function<A, Boolean> f) {
    throw new RuntimeException("To be implemented");
  }
  private Option() {}

  private static class None<A> extends Option<A> {

    private None() {}
    
    @Override
    public A get() {
      throw new IllegalStateException("get called on None");
    }

    @Override
    public <B> Option<B> map(Function<A, B> f) {
      throw new RuntimeException("To be implemented");
    }

    @Override
    public A getOrElse(Supplier<A> defaultValue) {
      throw new RuntimeException("To be implemented");
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
    public A get() {
      return this.value;
    }

    public <B> Option<B> map(Function<A, B> f) {
      throw new RuntimeException("To be implemented");
    }

    public A getOrElse(Supplier<A> defaultValue) {
      throw new RuntimeException("To be implemented");
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