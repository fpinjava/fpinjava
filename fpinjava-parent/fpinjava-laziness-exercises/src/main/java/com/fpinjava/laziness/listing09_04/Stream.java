package com.fpinjava.laziness.listing09_04;

import com.fpinjava.common.*;


abstract class Stream<A> {

  private static Stream EMPTY = new Empty(); // #A

  public abstract A head();

  public abstract Stream<A> tail();

  public abstract Boolean isEmpty();

  private Stream() {} // #B

  private static class Empty<A> extends Stream<A> { // #C

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

  private static class Cons<A> extends Stream<A> { // #D

    private final Supplier<A> head; // #E

    private final Supplier<Stream<A>> tail; // #F

    private Cons(Supplier<A> h, Supplier<Stream<A>> t) {
      head = h;
      tail = t;
    }

    @Override
    public A head() { // #G
      return head.get();
    }

    @Override
    public Stream<A> tail() { // #H
      return tail.get();
    }

    @Override
    public Boolean isEmpty() {
      return false;
    }
  }

  static <A> Stream<A> cons(Supplier<A> hd, Supplier<Stream<A>> tl) { // #I
    return new Cons<>(hd, tl);
  }

  @SuppressWarnings("unchecked")
  public static <A> Stream<A> empty() { // #J
    return EMPTY;
  }

  public static Stream<Integer> from(int i) { // #K
    return cons(() -> i, () -> from(i + 1));
  }
}
