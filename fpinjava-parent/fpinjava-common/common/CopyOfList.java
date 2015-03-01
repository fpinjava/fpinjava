package com.fpinjava.common;


import java.util.Collection;


import static com.fpinjava.common.TailCall.ret;
import static com.fpinjava.common.TailCall.sus;

public abstract class CopyOfList<A> {

  public abstract A head();
  public abstract CopyOfList<A> tail();
  public abstract boolean isEmpty();
  public abstract CopyOfList<A> reverse();
  public abstract int length();
  public abstract <B> B foldLeft(B identity, Function<B, Function<A, B>> f);
  public abstract A reduce(Function<A, Function<A, A>> f);
  public abstract <B> CopyOfList<B> map(Function<A, B> f);

  private CopyOfList() {}

  public CopyOfList<A> cons(A a) {
    return new Cons<>(a, this);
  }

  @SuppressWarnings("rawtypes")
  public static final CopyOfList NIL = new Nil();

  private static class Nil<A> extends CopyOfList<A> {

    private Nil() {}

    @Override
    public A head() {
      throw new IllegalStateException("head called en empty list");
    }

    @Override
    public CopyOfList<A> tail() {
      throw new IllegalStateException("tail called en empty list");
    }

    @Override
    public String toString() {
      return "[NIL]";
    }

    @Override
    public boolean isEmpty() {
      return true;
    }

     @Override
    public CopyOfList<A> reverse() {
      return this;
    }

    @Override
    public int length() {
      return 0;
    }

    @Override
    public <B> B foldLeft(B identity, Function<B, Function<A, B>> f) {
      return identity;
    }

    @Override
    public <B> CopyOfList<B> map(Function<A, B> f) {
      return list();
    }

    @Override
    public A reduce(Function<A, Function<A, A>> f) {
      throw new IllegalStateException("Can't reduce and empty list without a zero");
    }
  }

  private static class Cons<A> extends CopyOfList<A> {

    private final A head;
    private final CopyOfList<A> tail;

    private Cons(A head, CopyOfList<A> tail) {
      this.head = head;
      this.tail = tail;
    }

    @Override
    public A head() {
      return head;
    }

    @Override
    public CopyOfList<A> tail() {
      return tail;
    }

    @Override
    public boolean isEmpty() {
      return false;
    }

    @Override
    public String toString() {
      return String.format("[%sNIL]", toString(new StringBuilder(), this).eval());
    }

    private TailCall<StringBuilder> toString(StringBuilder acc, CopyOfList<A> list) {
      return list.isEmpty()
          ? ret(acc)
          : sus(() -> toString(acc.append(list.head()).append(", "), list.tail()));
    }

    @Override
    public CopyOfList<A> reverse() {
      return reverse_(list(), this).eval();
    }

    private TailCall<CopyOfList<A>> reverse_(CopyOfList<A> acc, CopyOfList<A> list) {
      return list.isEmpty()
          ? ret(acc)
          : sus(() -> reverse_(new Cons<>(list.head(), acc), list.tail()));
    }

    @Override
    public <B> B foldLeft(B identity, Function<B, Function<A, B>> f) {
      return foldLeft_(identity, this, identity, f).eval();
    }

    private <B> TailCall<B> foldLeft_(B acc, CopyOfList<A> list, B identity, Function<B, Function<A, B>> f) {
      return list.isEmpty()
          ? ret(acc)
          : sus(() -> foldLeft_(f.apply(acc).apply(list.head()), list.tail(), identity, f));
    }

    /**
     * Very bad implementation of length (O(n))!
     */
    @Override
    public int length() {
      return foldLeft(0, x -> ignore -> x + 1);
    }

    @Override
    public <B> CopyOfList<B> map(Function<A, B> f) {
      return foldLeft(list(), t -> h -> new Cons<>(f.apply(h),t));
    }

    @Override
    public A reduce(Function<A, Function<A, A>> f) {
      return this.tail().foldLeft(this.head(), f);
    }
  }

  @SuppressWarnings("unchecked")
  public static <A> CopyOfList<A> list() {
    return NIL;
  }

  @SafeVarargs
  public static <A> CopyOfList<A> list(A... a) {
    CopyOfList<A> n = list();
    for (int i = a.length - 1; i >= 0; i--) {
      n = new Cons<>(a[i], n);
    }
    return n;
  }

  public static CopyOfList<Integer> range(int start, int end) {
    return range_(list(), start, end).eval();
  }

  public static TailCall<CopyOfList<Integer>> range_(CopyOfList<Integer> acc, int start, int end) {
    return start == end
        ? ret(acc)
        : sus(() -> range_(new Cons<>(start, acc), start + 1, end));
  }

  public static <A> CopyOfList<A> fill(int n, Supplier<A> s) {
    return range(0, n).map(ignore -> s.get());
  }

  public <A1, A2> Tuple<CopyOfList<A1>, CopyOfList<A2>> unzip(Function<A, Tuple<A1, A2>> f) {
    return unzip(this.map(f));
  }

  public static <T, U> Tuple<CopyOfList<T>, CopyOfList<U>> unzip(CopyOfList<Tuple<T, U>> list) {
    CopyOfList<T> listT = list();
    CopyOfList<U> listU = list();
    CopyOfList<Tuple<T, U>> workList = list;
    while (!workList.isEmpty()) {
      listT = new Cons<>(workList.head()._1, listT);
      listU = new Cons<>(workList.head()._2, listU);
      workList = workList.tail();
    }
    return new Tuple<>(listT, listU);
  }

  public static <T, U> Map<U, CopyOfList<T>> groupBy(CopyOfList<T> list, Function<T, U> f) {
    if (list.isEmpty()) {
      return Map.empty();
    } else {
      CopyOfList<T> workList = list;
      Map<U, CopyOfList<T>> m = Map.empty();
      while (!workList.isEmpty()) {
        final U k = f.apply(workList.head());
        CopyOfList<T> rt = m.get(k).getOrElse(list()).cons(workList.head());
        m = m.put(k, rt);
        workList = workList.tail();
      }
      return m;
    }
  }

  public <B> Map<B, CopyOfList<A>> groupBy(Function<A, B> f) {
    return groupBy(this, f);
  }

  public static <T> CopyOfList<T> fromCollection(Collection<T> ct) {
    CopyOfList<T> lt = list();
    for (T t : ct) {
      lt = cons(t, lt);
    }
    return reverse(lt);
  }

  public static <T> CopyOfList<T> cons(T t, CopyOfList<T> list) {
    return new Cons<>(t, list);
  }

  public static <T> CopyOfList<T> reverse(CopyOfList<T> list) {
    return list.foldLeft(list(), x -> y -> new Cons<>(y, x));
  }
}
