package com.fpinjava.laziness.exercise09_08;

import com.fpinjava.common.Function;
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

  public abstract Stream<A> takeWhile_(Function<A, Boolean> f);

  public abstract <B> B foldRight(Supplier<B> z, Function<A, Function<Supplier<B>, B>> f);

  public Result<A> headOptionViaFoldRight() {
    return foldRight(Result::empty, a -> ignore -> Result.success(a));
  }

  public Stream<A> takeWhile(Function<A, Boolean> f) {
    throw new IllegalStateException("To be implemented");
  }

  public boolean exists(Function<A, Boolean> p) {
    return exists(this, p).eval();
  }

  private TailCall<Boolean> exists(Stream<A> s, Function<A, Boolean> p) {
    return s.isEmpty()
        ? ret(false)
        : p.apply(s.head())
            ? ret(true)
            : sus(() -> exists(s.tail(), p));
  }

  public Stream<A> dropWhile(Function<A, Boolean> f) {
    return dropWhile(this, f).eval();
  }

  private TailCall<Stream<A>> dropWhile(Stream<A> acc, Function<A, Boolean> f) {
    return acc.isEmpty()
        ? ret(acc)
        : f.apply(acc.head())
            ? sus(() -> dropWhile(acc.tail(), f))
            : ret(acc);
  }

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
      return this;
    }

    @Override
    public Stream<A> drop(int n) {
      return this;
    }

    @Override
    public Stream<A> takeWhile_(Function<A, Boolean> f) {
      return this;
    }

    @Override
    public <B> B foldRight(Supplier<B> z, Function<A, Function<Supplier<B>, B>> f) {
      return z.get();
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
      return n <= 0
          ? empty()
          : cons(head, () -> tail().take(n - 1));
    }

    @Override
    public Stream<A> drop(int n) {
      return drop(this, n).eval();
    }

    @Override
    public Stream<A> takeWhile_(Function<A, Boolean> f) {
      return f.apply(head())
          ? cons(head, () -> tail().takeWhile_(f))
          : empty();
    }

    @Override
    public <B> B foldRight(Supplier<B> z, Function<A, Function<Supplier<B>, B>> f) {
      return f.apply(head()).apply(() -> tail().foldRight(z, f));
    }

    public TailCall<Stream<A>> drop(Stream<A> acc, int n) {
      return acc.isEmpty() || n <= 0
          ? ret(acc)
          : sus(() -> drop(acc.tail(), n - 1));
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
