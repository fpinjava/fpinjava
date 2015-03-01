package com.fpinjava.laziness.listing07_03;


import com.fpinjava.common.List;
import com.fpinjava.common.Supplier;

public abstract class Stream<T> {

  @SuppressWarnings("rawtypes")
  private static Stream EMPTY = new Empty();

  public abstract Supplier<T> head();
  public abstract Supplier<Stream<T>> tail();
  public abstract boolean isEmpty();
  
  private Stream() {}

  public static class Empty<T> extends Stream<T> {

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

  public static class Cons<T> extends Stream<T> {  //#A

    protected final Supplier<T> head;
    
    protected final Supplier<Stream<T>> tail;

    private Cons(Supplier<T> head, Supplier<Stream<T>> tail) {
      this.head = head;
      this.tail = tail;
    }

    @Override
    public boolean isEmpty() {
      return false;
    }

    @Override
    public Supplier<T> head() {
      return this.head;
    }

    @Override
    public Supplier<Stream<T>> tail() {
      return this.tail;
    }
  }

  public static <T> Stream<T> cons(Supplier<T> hd, Stream<T> tl) { //#B
    return new Cons<T>(hd, () -> tl);
  }

  @SuppressWarnings("unchecked")
  public static <T> Stream<T> empty() { // #C
    return EMPTY;
  }

  public static <T> Stream<T> cons(List<T> list) { //#D
    return list.isEmpty()
        ? empty()
        : new Cons<T>(list::head, () -> cons(list.tail()));
  }

  @SafeVarargs
  public static <T> Stream<T> cons(T... t) { //#E
    return cons(List.list(t));
  }
}
