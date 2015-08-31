package com.fpinjava.laziness.listing09_04;


import com.fpinjava.common.List;
import com.fpinjava.common.Supplier;

public abstract class Stream<T> {

  @SuppressWarnings("rawtypes")
  private static Stream EMPTY = new Empty();

  public abstract Supplier<T> head();
  public abstract Supplier<Stream<T>> tail();
  public abstract boolean isEmpty();

  private Stream() {}

  public static class Empty<T> extends Stream<T> {  // #A

    private Empty() {
    }

    @Override
    public boolean isEmpty() {
      return true;
    }

    @Override
    public Supplier<T> head() {
      throw new IllegalStateException("head called on Empty stream");
    }

    @Override
    public Supplier<Stream<T>> tail() {
      throw new IllegalStateException("tail called on Empty stream");
    }
  }

  public static class Cons<T> extends Stream<T> {  // #B

    protected final Supplier<T> head;  // #C

    protected final Supplier<Stream<T>> tail;  // #D

    private Cons(Supplier<T> head, Supplier<Stream<T>> tail) {
      this.head = head;
      this.tail = tail;
    }

    @Override
    public boolean isEmpty() {
      return false;
    }

    @Override
    public Supplier<T> head() {  // #E
      return this.head;
    }

    @Override
    public Supplier<Stream<T>> tail() {  // #F
      return this.tail;
    }
  }

  public static <T> Stream<T> cons(Supplier<T> hd, Stream<T> tl) { // #G
    return new Cons<>(hd, () -> tl);
  }

  @SuppressWarnings("unchecked")
  public static <T> Stream<T> empty() {  // #H
    return EMPTY;
  }

  public static <T> Stream<T> cons(List<T> list) {  // #I
    return list.isEmpty()
        ? empty()
        : new Cons<>(list::head, () -> cons(list.tail()));
  }

  @SafeVarargs
  public static <T> Stream<T> cons(T... t) {  // #J
    return cons(List.list(t));
  }
}
