package com.fpinjava.laziness.exercise07_07;


import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Option;
import com.fpinjava.common.Supplier;
import com.fpinjava.common.TailCall;

import static com.fpinjava.common.TailCall.*;

/*
 * A slightly more complex version of stream. You may see the difference by running the corresponding
 * test for filter and remark that it will not evaluate teh first element of the stream. This is however
 * generally not worth the additional complexity.
 */
public abstract class LazierStream<T> {

  @SuppressWarnings("rawtypes")
  private static LazierStream EMPTY = new Empty();

  public abstract T head();
  public abstract Supplier<LazierStream<T>> tail();
  public abstract boolean isEmpty();
  public abstract Option<T> headOption();
  protected abstract LazyVal<T> headS();
  public abstract Boolean exists(Function<T, Boolean> p);
  public abstract <U> U foldRight(Supplier<U> z, Function<T, Function<Supplier<U>, U>> f);

  protected LazierStream() {}

  public String toString() {
    return toList().toString();
  }

  public List<T> toList() {
    return toListIterative();
  }

  @SuppressWarnings("unused")
  private TailCall<List<T>> toListRecursive(LazierStream<T> s, List<T> acc) {
    return s instanceof Empty
        ? ret(acc)
        : sus(() -> toListRecursive(s.tail().get(), List.cons(s.head(), acc)));
  }

  public List<T> toListIterative() {
    java.util.List<T> result = new java.util.ArrayList<>();
    LazierStream<T> ws = this;
    while (!ws.isEmpty()) {
      result.add(ws.head());
      ws = ws.tail().get();
    }
    return List.fromCollection(result);
  }

  public LazierStream<T> take(Integer n) {
    return n <= 0
        ? LazierStream.empty()
        : LazierStream.cons(headS(), () -> tail().get().take(n - 1));
  }

  public LazierStream<T> drop(int n) {
    return n <= 0
        ? this
        : tail().get().drop(n - 1);
  }

  public LazierStream<T> takeWhile(Function<T, Boolean> p) {
    return isEmpty()
        ? this
        : p.apply(head())
            ? cons(headS(), () -> tail().get().takeWhile(p))
            : empty();
  }

  public Boolean existsViaFoldRight(Function<T, Boolean> p) {
    return foldRight(() -> false, a -> b -> p.apply(a) || b.get());
  }

  public Boolean forAll(Function<T, Boolean> p) {
    return foldRight(() -> true, a -> b -> p.apply(a) && b.get());
  }

  public LazierStream<T> takeWhileViaFoldRight(Function<T, Boolean> p) {
    return foldRight(LazierStream::<T> empty, t -> st -> p.apply(t)
        ? cons(() -> t, () -> st.get())
        : LazierStream.<T> empty());
  }

  public Option<T> headOptionViaFoldRight() {
    return foldRight(() -> Option.<T>none(), t -> st -> Option.some(t));
  }

  /*
   * A special version of map
   */
  public <U> LazierStream<U> map(Function<T, U> f) {
    return flatten(this.mapH(lift(f)));
  }

  public <U> LazierStream<LazyVal<U>> mapH(Function<LazyVal<T>, LazyVal<U>> f) {
    return foldRightH(LazierStream::<LazyVal<U>> empty, ht -> su -> cons(() -> f.apply(ht), () -> su.get()));
  }

  /*
   * A special version of filter.
   */
  public LazierStream<LazyVal<T>> filter(Function<T, Boolean> p) {
    LazierStream<LazyVal<T>> stream = filterOption(filter_(this, p));
    return stream;
  }

  private LazierStream<LazyVal<T>> filterOption(LazierStream<Option<T>> stream) {
    Function<LazyVal<Option<T>>, LazyVal<T>> f = x -> x.getEvaluated().isSome() ? LazyVal.head(() -> x.getEvaluated().get()) : LazyVal.empty();
    LazierStream<LazyVal<T>> result = stream.mapH(f);
    return result;
  }

  private LazierStream<Option<T>> filter_(LazierStream<T> stream, Function<T, Boolean> p) {
    Supplier<Option<T>> supplierOt = () -> (p.apply(stream.headS().getEvaluated()) ? Option.some(stream.headS().getEvaluated()) : Option.none());
    return stream.isEmpty()
        ? LazierStream.empty()
        : cons(supplierOt, () -> filter_(stream.tail().get(), p));
  }

  public LazierStream<T> append(LazierStream<T> s) {
    return foldRight(() -> s, t -> st -> cons(() -> t, () -> st.get()));
  }

  public <U> LazierStream<U> flatMap(Function<T, LazierStream<U>> f) {
    return foldRight(LazierStream::<U> empty, t -> su -> f.apply(t).append(su.get()));
  }

  private <U> U foldRightH(Supplier<U> z, Function<LazyVal<T>, Function<Supplier<U>, U>> f) {
    return this.isEmpty()
        ? z.get()
        : f.apply(headS()).apply(() -> tail().get().foldRightH(z, f));
  }

  public static class Empty<T> extends LazierStream<T> {

    protected Empty() {
    }

    @Override
    public boolean isEmpty() {
      return true;
    }

    @Override
    public T head() {
      throw new IllegalStateException("head called on Empty stream");
    }

    @Override
    protected LazyVal<T> headS() {
      throw new IllegalStateException("headS called on Empty stream");
    }

    @Override
    public Supplier<LazierStream<T>> tail() {
      throw new IllegalStateException("tail called on Empty stream");
    }

    @Override
    public Option<T> headOption() {
      return Option.none();
    }

    @Override
    public Boolean exists(Function<T, Boolean> p) {
      return false;
    }

    @Override
    public <U> U foldRight(Supplier<U> z, Function<T, Function<Supplier<U>, U>> f) {
      return z.get();
    }
  }

  public static class Cons<T> extends LazierStream<T> {

    protected final LazyVal<T> head;

    protected final Supplier<LazierStream<T>> tail;

    protected T headM;

    protected Cons(LazyVal<T> head, Supplier<LazierStream<T>> tail) {
      this.head = head;
      this.tail = tail;
    }

    @Override
    public boolean isEmpty() {
      return false;
    }

    @Override
    public T head() {
      return this.head.getEvaluated();
    }

    @Override
    protected LazyVal<T> headS() {
      return this.head;
    }
    @Override
    public Supplier<LazierStream<T>> tail() {
      return this.tail;
    }

    @Override
    public Option<T> headOption() {
      return Option.some(this.head());
    }

    @Override
    public Boolean exists(Function<T, Boolean> p) {
      return p.apply(head()) || tail().get().exists(p);
    }

    public <U> U foldRight(Supplier<U> z, Function<T, Function<Supplier<U>, U>> f) {
      return f.apply(head()).apply(() -> tail().get().foldRight(z, f));
    }
  }

  private static <T> LazierStream<T> cons(LazyVal<T> hd, Supplier<LazierStream<T>> tl) {
    return new Cons<>(hd, tl);
  }

  private static <T> LazierStream<T> cons(Supplier<T> hd, Supplier<LazierStream<T>> tl) {
    return new Cons<>(LazyVal.head(hd), tl);
  }

  public static <T> LazierStream<T> cons(Supplier<T> hd, LazierStream<T> tl) {
    return new Cons<>(LazyVal.head(hd), () -> tl);
  }

  @SuppressWarnings("unchecked")
  public static <T> LazierStream<T> empty() {
    return EMPTY;
  }

  public static <T> LazierStream<T> cons(List<T> list) {
    return list.isEmpty()
        ? empty()
        : new Cons<>(LazyVal.head(list::head, list.head()), () -> cons(list.tail()));
  }

  @SafeVarargs
  public static <T> LazierStream<T> cons(T... t) {
    return cons(List.list(t));
  }

  public static <T, U> Function<LazyVal<T>, LazyVal<U>> lift(Function<T, U> f) {
    return t -> LazyVal.head(() -> f.apply(t.getEvaluated()));
  }

  public static <T> LazierStream<T> flatten(LazierStream<LazyVal<T>> s) {
    return s.isEmpty()
        ? empty()
        : s.head().isEmpty()
            ? flatten(s.tail().get())
            : cons(s.head().getNonEvaluated(), flatten(s.tail().get()));
  }

  /*
   * A more sophisticated version of the Head class
   */
  public static abstract class LazyVal<T> {

    public abstract Supplier<T> getNonEvaluated();

    public abstract T getEvaluated();

    public abstract boolean isEmpty();

    public static class Empty<T> extends LazyVal<T> {

      private Empty() {}

      @Override
      public Supplier<T> getNonEvaluated() {
        throw new IllegalStateException("getNonEvaluated on Head.Empty");
      }

      @Override
      public T getEvaluated() {
        throw new IllegalStateException("getEvaluated on Head.Empty");
      }

      @Override
      public boolean isEmpty() {
        return true;
      }
    }

    public static class NonEmpty<T> extends LazyVal<T> {

      private Supplier<T> nonEvaluated;
      private T evaluated;

      private NonEmpty(Supplier<T> nonEvaluated) {
        super();
        this.nonEvaluated = nonEvaluated;
      }

      private NonEmpty(Supplier<T> nonEvaluated, T evaluated) {
        super();
        this.nonEvaluated = nonEvaluated;
        this.evaluated = evaluated;
      }

      public Supplier<T> getNonEvaluated() {
        return nonEvaluated;
      }

      public T getEvaluated() {
        if (evaluated == null) {
          evaluated = nonEvaluated.get();
        }
        return evaluated;
      }

      @Override
      public boolean isEmpty() {
        return false;
      }
    }

    public static <T> LazyVal<T> head(Supplier<T> nonEvaluated) {
      return new NonEmpty<>(nonEvaluated);
    }

    public static <T> LazyVal<T> head(Supplier<T> nonEvaluated, T evaluated) {
      return new NonEmpty<>(nonEvaluated, evaluated);
    }

    public static <T> LazyVal<T> empty() {
      return new Empty<>();
    }
  }
}
