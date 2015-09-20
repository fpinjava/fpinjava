package com.fpinjava.laziness.listing09_04;


import com.fpinjava.common.List;
import com.fpinjava.common.Supplier;

public abstract class Stream0<T> {

  @SuppressWarnings("rawtypes")
  private static Stream0 EMPTY = new Empty();

  public abstract Supplier<T> head();
  public abstract Supplier<Stream0<T>> tail();
  public abstract boolean isEmpty();

  private Stream0() {}

  public static class Empty<T> extends Stream0<T> {  // #A

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
    public Supplier<Stream0<T>> tail() {
      throw new IllegalStateException("tail called on Empty stream");
    }
  }

  public static class Cons<T> extends Stream0<T> {  // #B

    protected final Supplier<T> head;  // #C

    protected final Supplier<Stream0<T>> tail;  // #D

    private Cons(Supplier<T> head, Supplier<Stream0<T>> tail) {
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
    public Supplier<Stream0<T>> tail() {  // #F
      return this.tail;
    }
  }

  public static <T> Stream0<T> cons(Supplier<T> hd, Stream0<T> tl) { // #G
    return new Cons<>(hd, () -> tl);
  }

  @SuppressWarnings("unchecked")
  public static <T> Stream0<T> empty() {  // #H
    return EMPTY;
  }

  public static <T> Stream0<T> cons(List<T> list) {  // #I
    return list.isEmpty()
        ? empty()
        : new Cons<>(list::head, () -> cons(list.tail()));
  }

  @SafeVarargs
  public static <T> Stream0<T> cons(T... t) {  // #J
    return cons(List.list(t));
  }
}
