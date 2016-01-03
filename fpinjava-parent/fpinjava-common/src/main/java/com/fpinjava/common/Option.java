package com.fpinjava.common;

import java.util.Objects;

public abstract class Option<A> {

  public abstract <B> Option<B> map(Function<A, B> f);

  protected abstract A getOrThrow();

  public abstract A getOrElse(Supplier<A> defaultValue);

  public abstract A getOrElse(A defaultValue);

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

  @SuppressWarnings("rawtypes")
  private static Option none = new None();

  private Option() {
  }

  private static class None<A> extends Option<A> {

    private None() {
    }

    @Override
    public String toString() {
      return "None";
    }

    @SuppressWarnings("unchecked")
    @Override
    public <B> Option<B> map(Function<A, B> f) {
      return none;
    }

    @Override
    protected A getOrThrow() {
      throw new IllegalStateException("None has no value");
    }

    @Override
    public A getOrElse(Supplier<A> defaultValue) {
      return defaultValue.get();
    }

    @Override
    public boolean isSome() {
      return false;
    }

    @Override
    public boolean equals(Object o) {
      return this == o || o instanceof None;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public A getOrElse(A defaultValue) {
      return defaultValue;
    }
  }

  private static class Some<A> extends Option<A> {

    private final A value;

    private Some(A a) {
      this.value = a;
    }

    @Override
    public String toString() {
      return String.format("Some(%s)", this.value);
    }

    @Override
    public <B> Option<B> map(Function<A, B> f) {
      return new Some<>(f.apply(this.value));
    }

    @Override
    protected A getOrThrow() {
      return this.value;
    }

    @Override
    public A getOrElse(Supplier<A> defaultValue) {
      return this.value;
    }

    @Override
    public boolean isSome() {
      return true;
    }

    @Override
    public boolean equals(Object o) {
      return (this == o || o instanceof Some) && this.value.equals(((Some<?>) o).value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public A getOrElse(A defaultValue) {
      return value;
    }
}

  public static <A> Option<A> some(A a) {
    return new Some<>(a);
  }

  @SuppressWarnings("unchecked")
  public static <A> Option<A> none() {
    return none;
  }

  public static <A, B> Function<Option<A>, Option<B>> lift(Function<A, B> f) {
    return x -> x.map(f);
  }

  public static <A, B, C> Option<C> map2(Option<A> a, Option<B> b, Function<A, Function<B, C>> f) {
    return a.isSome() && b.isSome()
        ? some(f.apply(a.getOrThrow()).apply(b.getOrThrow()))
        : none();
  }

  public static Function<Integer, Function<Integer, Double>> insuranceRateQuote = x -> y -> 0.0 + x + y;

  public static Option<Double> parseInsuranceRateQuote(String age, String numberOfSpeedingTickets) {
    final Option<Integer> optAge = validate(() -> Integer.valueOf(age));
    final Option<Integer> optTickets = validate(() -> Integer.parseInt(numberOfSpeedingTickets));
    return map2(optAge, optTickets, insuranceRateQuote);
  }

  public static <A> Option<A> validate(Supplier<A> a) {
    try {
      return some(a.get());
    } catch (Exception e) {
      return none();
    }
  }

  public static <A> Option<List<A>> sequence_1(List<Option<A>> list) {
    return list.isEmpty()
        ? some(List.list())
        : list.head().flatMap(hh -> sequence_1(list.tail()).map(x -> x.cons(hh)));
  }

  public static <A> Option<List<A>> sequence(List<Option<A>> list) {
    return list.foldRight(some(List.list()), x -> y -> map2(x, y, a -> b -> b.cons(a)));
  }

  public static Option<List<Integer>> parseInts(List<String> list) {
    return sequence(list.map(i -> validate(() -> Integer.valueOf(i))));
  }

  public static <A, B> Option<List<B>> traverse(List<A> list, Function<A, Option<B>> f) {
    return list.isEmpty()
        ? Option.some(List.list())
        : map2(f.apply(list.head()), traverse(list.tail(), f), a -> b -> b.cons(a));
  }

  public static <A, B> Option<List<B>> traverse_(List<A> list, Function<A, Option<B>> f) {
    return list.foldRight(some(List.list()), x -> y -> map2(f.apply(x), y, a -> b -> b.cons(a)));
  }

  public static <A> Option<List<A>> sequenceViaTraverse(List<Option<A>> list) {
    return traverse(list, x -> x);
  }

}
