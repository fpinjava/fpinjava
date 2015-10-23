package com.fpinjava.laziness.exercise09_19;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import com.fpinjava.common.Supplier;
import com.fpinjava.common.TailCall;
import com.fpinjava.common.Tuple;

import static com.fpinjava.common.TailCall.ret;
import static com.fpinjava.common.TailCall.sus;


abstract class Stream<A> {

  private static Stream EMPTY = new Empty();

  public abstract Tuple<A, Stream<A>> head();

  public abstract Stream<A> tail();

  public abstract Boolean isEmpty();

  public abstract Tuple<Result<A>, Stream<A>> headOption();

  public abstract Stream<A> take(int n);

  public abstract Stream<A> drop(int n);

  public abstract Stream<A> takeWhile_(Function<A, Boolean> f);

  public abstract <B> B foldRight(Supplier<B> z, Function<A, Function<Supplier<B>, B>> f);

  public Stream<A> filter(Function<A, Boolean> p) {
    throw new IllegalStateException("To be implemented");
  }

  public Stream<A> takeViaUnfold(int n) {
    return unfold(new Tuple<>(this, n), x -> x._1.isEmpty()
        ? Result.empty()
        : x._2 > 0
            ? Result.success(new Tuple<>(x._1.head()._1, new Tuple<>(x._1.tail(), x._2 - 1)))
            : Result.empty());
  }

  public boolean forAll(Function<A, Boolean> p) {
    return !exists(x -> !p.apply(x));
  }

  public boolean forAllViaFoldRight(Function<A, Boolean> p) {
    return foldRight(() -> true, a -> b -> p.apply(a) && b.get());
  }

  public Result<A> find(Function<A, Boolean> p) {
    return filter(p).headOption()._1;
  }

  public <B> Stream<B> flatMap(Function<A, Stream<B>> f) {
    return foldRight(Stream::empty, a -> b -> f.apply(a).append(b));
  }

  public Stream<A> append(Supplier<Stream<A>> s) {
    return foldRight(s, a -> b -> cons(() -> a, b));
  }

  public <B> Stream<B> map(Function<A, B> f) {
    return foldRight(Stream::empty, a -> b -> cons(() -> f.apply(a), b));
  }

  public <B> Stream<B> mapViaUnfold(Function<A, B> f) {
    return unfold(this, x -> x.isEmpty()
        ? Result.empty()
        : Result.success(new Tuple<>(f.apply(x.head()._1), x.tail())));
  }

  public Result<A> headOptionViaFoldRight() {
    return foldRight(Result::empty, a -> ignore -> Result.success(a));
  }

  public Stream<A> takeWhile(Function<A, Boolean> f) {
    return foldRight(Stream::empty, a -> b -> f.apply(a)
        ? cons(() -> a, b)
        : empty());
  }

  /**
   * Not optimized because x._1.head() is called twice and
   * x._1.tail() is fine because the tail does not change after calling head().
   */
  public Stream<A> takeWhileViaUnfold(Function<A, Boolean> f) {
    return unfold(new Tuple<>(this, f), x -> x._1.isEmpty()
        ? Result.empty()
        : f.apply(x._1.head()._1)
            ? Result.success(new Tuple<>(x._1.head()._1, new Tuple<>(x._1.tail(), f)))
            : Result.empty());
  }

  public boolean exists(Function<A, Boolean> p) {
    return exists(this, p).eval();
  }

  private TailCall<Boolean> exists(Stream<A> s, Function<A, Boolean> p) {
    return s.isEmpty()
        ? ret(false)
        : p.apply(s.head()._1)
            ? ret(true)
            : sus(() -> exists(s.tail(), p));
  }

  public Stream<A> dropWhile(Function<A, Boolean> f) {
    return dropWhile(this, f).eval();
  }

  private TailCall<Stream<A>> dropWhile(Stream<A> acc, Function<A, Boolean> f) {
    return acc.isEmpty()
        ? ret(acc)
        : f.apply(acc.head()._1)
            ? sus(() -> dropWhile(acc.tail(), f))
            : ret(acc);
  }

  public List<A> toList() {
    return toList(this, List.list()).eval().reverse();
  }

  private TailCall<List<A>> toList(Stream<A> s, List<A> acc) {
    return s.isEmpty()
        ? ret(acc)
        : sus(() -> toList(s.tail(), List.cons(s.head()._1, acc)));
  }

  public boolean hasSubSequence(Stream<A> s) {
    return tails().exists(x -> x.startsWith(s));
  }

  public boolean startsWith(Stream<A> s) {
    return zipAll(s).takeWhile(x -> !x._2.isEmpty()).forAll(y -> y._1.equals(y._2));
  }

  public <B> Stream<Tuple<Result<A>, Result<B>>> zipAll(Stream<B> that) {
    return zipAllGeneric(that, x -> y -> new Tuple<>(x, y));
  }

  public <B, C> Stream<C> zipAllGeneric(Stream<B> that, Function<Result<A>, Function<Result<B>, C>> f) {
    Stream<Result<A>> a = infinite(this);
    Stream<Result<B>> b = infinite(that);
    return unfold(new Tuple<>(a, b), x -> x._1.isEmpty() && x._2.isEmpty()
        ? Result.empty()
        : x._1.head()._1.isEmpty() && x._2.head()._1.isEmpty()
            ? Result.empty()
            : Result.success(new Tuple<>(f.apply(x._1.head()._1).apply(x._2.head()._1), new Tuple<>(x._1.tail(), x._2.tail()))));
  }

  public static <A> Stream<Result<A>> infinite(Stream<A> s) {
    return s.map(Result::success).append(() -> repeatViaIterate(Result.empty()));
  }

  public Stream<Stream<A>> tails() {
    return cons(() -> this, () -> unfold(this, x -> x.isEmpty()
        ? Result.empty()
        : Result.success(new Tuple<>(x.tail(), x.tail()))));
  }

  private Stream() {}

  private static class Empty<A> extends Stream<A> {

    @Override
    public Stream<A> tail() {
      throw new IllegalStateException("tail called on empty");
    }

    @Override
    public Tuple<A, Stream<A>> head() {
      throw new IllegalStateException("head called on empty");
    }

    @Override
    public Boolean isEmpty() {
      return true;
    }

    @Override
    public Tuple<Result<A>, Stream<A>> headOption() {
      return new Tuple<>(Result.empty(), this);
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
    private final Result<A> h;
    private final Supplier<Stream<A>> tail;

    private Cons(Supplier<A> h, Supplier<Stream<A>> t) {
      head = h;
      tail = t;
      this.h = Result.empty();
    }

    private Cons(A h, Supplier<Stream<A>> t) {
      head = () -> h;
      tail = t;
      this.h = Result.success(h);
    }

    @Override
    public Tuple<Result<A>, Stream<A>> headOption() {
      Tuple<A, Stream<A>> t = head();
      return new Tuple<>(Result.success(t._1), t._2);
    }

    @Override
    public Stream<A> tail() {
      return tail.get();
    }

    @Override
    public Tuple<A, Stream<A>> head() {
      A a = h.getOrElse(head.get());
      return h.isEmpty()
          ? new Tuple<>(a, new Cons<>(a, tail))
          : new Tuple<>(a, this);
    }

    @Override
    public Boolean isEmpty() {
      return false;
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
      return f.apply(head()._1)
          ? cons(head, () -> tail().takeWhile_(f))
          : empty();
    }

    @Override
    public <B> B foldRight(Supplier<B> z, Function<A, Function<Supplier<B>, B>> f) {
      return f.apply(head()._1).apply(() -> tail().foldRight(z, f));
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

  @SafeVarargs
  public static <A> Stream<A> stream(A... a) {
    return stream(List.list(a));
  }

  public static <A> Stream<A> stream(List<A> list) {
    return list.foldLeft(empty(), sa -> a -> cons(() -> a, () -> sa));
  }

  public static Stream<Integer> from(int i) {
    return cons(() -> i, () -> from(i + 1));
  }

  public static Stream<Integer> from(Supplier<Integer> n) {
    return cons(n, () -> from(n.get() + 1));
  }

  public static Stream<Integer> fromViaIterate(int i) {
    return iterate(i, x -> x + 1);
  }

  public static Stream<Integer> fromViaUnfold(int n) {
    return unfold(n, x -> Result.success(new Tuple<>(x, x + 1)));
  }

  public static <A> Stream<A> repeat(A a) {
    return cons(() -> a, () -> repeat(a));
  }

  public static <A> Stream<A> repeatViaIterate(A a) {
    return iterate(a, x -> x);
  }

  public static <A> Stream<A> repeatViaUnfold(A a) {
    return unfold(a, x -> Result.success(new Tuple<>(a, a)));
  }

  public static <A> Stream<A> iterate(Supplier<A> seed, Function<A, A> f) {
    return cons(seed, () -> iterate(f.apply(seed.get()), f));
  }

  public static <A> Stream<A> iterate(A seed, Function<A, A> f) {
    return cons(() -> seed, () -> iterate(f.apply(seed), f));
  }

  public static <A, S> Stream<A> unfold(S z, Function<S, Result<Tuple<A, S>>> f) {
    return f.apply(z).map(x -> cons(() -> x._1, () -> unfold(x._2, f))).getOrElse(empty());
  }

  public static Stream<Integer> fibs_() {
    return iterate(new Tuple<>(0, 1), x -> new Tuple<>(x._2, x._1 + x._2)).map(x -> x._1);
  }

  public static Stream<Integer> fibs() {
    return unfold(new Tuple<>(1, 1), x -> Result.success(new Tuple<>(x._1, new Tuple<>(x._2, x._1 + x._2))));
  }
}
