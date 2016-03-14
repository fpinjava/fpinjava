package com.fpinjava.laziness.listing09_04;

import com.fpinjava.common.*;


abstract class Stream<A> {

  private static Stream EMPTY = new Empty();

  public abstract A head();

  public abstract Stream<A> tail();

  public abstract Boolean isEmpty();

  private Stream() {}

  private static class Empty<A> extends Stream<A> {

    @Override
    public Stream<A> tail() {
      throw new IllegalStateException("tail called on empty");
    }

    @Override
    public A head() {
      throw new IllegalStateException("head called on empty");
    }

    @Override
    public Boolean isEmpty() {
      return true;
    }
  }

  private static class Cons<A> extends Stream<A> {

    private final Supplier<A> head;

    private final Supplier<Stream<A>> tail;

    private Cons(Supplier<A> h, Supplier<Stream<A>> t) {
      head = h;
      tail = t;
    }

    @Override
    public A head() {
      return head.get();
    }

    @Override
    public Stream<A> tail() {
      return tail.get();
    }

    @Override
    public Boolean isEmpty() {
      return false;
    }
  }

  static <A> Stream<A> cons(Supplier<A> hd, Supplier<Stream<A>> tl) {
    return new Cons<>(hd, tl);
  }

  @SuppressWarnings("unchecked")
  public static <A> Stream<A> empty() {
    return EMPTY;
  }

  public static Stream<Integer> from(int i) {
    return cons(() -> i, () -> from(i + 1));
  }
}
