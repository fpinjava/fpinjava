package com.fpinjava.laziness.exercise09_03;

import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import com.fpinjava.common.Supplier;
import com.fpinjava.common.TailCall;

import static com.fpinjava.common.TailCall.ret;
import static com.fpinjava.common.TailCall.sus;


abstract class Stream<A> {

  private static Stream EMPTY = new Empty();

  public abstract A head();

  public abstract Stream<A> tail();

  public abstract Boolean isEmpty();

  public abstract Result<A> headOption();

  public abstract Stream<A> take(int n);

  public abstract Stream<A> drop(int n);

  public List<A> toList() {
    return toList(this, List.list()).eval().reverse();
  }

  private TailCall<List<A>> toList(Stream<A> s, List<A> acc) {
    return s.isEmpty()
        ? ret(acc)
        : sus(() -> toList(s.tail(), List.cons(s.head(), acc)));
  }

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

    @Override
    public Result<A> headOption() {
      return Result.empty();
    }

    @Override
    public Stream<A> take(int n) {
      throw new IllegalStateException("To be implemented");
    }

    @Override
    public Stream<A> drop(int n) {
      throw new IllegalStateException("To be implemented");
    }
  }

  private static class Cons<A> extends Stream<A> {

    private final Supplier<A> head;
    private A h;
    private final Supplier<Stream<A>> tail;
    private Stream<A> t;

    private Cons(Supplier<A> h, Supplier<Stream<A>> t) {
      head = h;
      tail = t;
    }

    @Override
    public A head() {
      if (h == null) {
        h = head.get();
      }
      return h;
    }

    @Override
    public Stream<A> tail() {
      if (t == null) {
        t = tail.get();
      }
      return t;
    }

    @Override
    public Boolean isEmpty() {
      return false;
    }

    @Override
    public Result<A> headOption() {
      return Result.success(head());
    }

    @Override
    public Stream<A> take(int n) {
      throw new IllegalStateException("To be implemented");
    }

    @Override
    public Stream<A> drop(int n) {
      throw new IllegalStateException("To be implemented");
    }
  }

  static <A> Stream<A> cons(Supplier<A> hd, Supplier<Stream<A>> tl) {
    return new Cons<>(hd, tl);
  }

  static <A> Stream<A> cons(Supplier<A> hd, Stream<A> tl) {
    return new Cons<>(hd, () -> tl);
  }

  @SuppressWarnings("unchecked")
  public static <A> Stream<A> empty() {
    return EMPTY;
  }

  public static Stream<Integer> from(int i) {
    return cons(() -> i, () -> from(i + 1));
  }
}
