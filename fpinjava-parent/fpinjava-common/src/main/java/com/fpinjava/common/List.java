package com.fpinjava.common;


import java.util.Collection;


import static com.fpinjava.common.TailCall.ret;
import static com.fpinjava.common.TailCall.sus;

public abstract class List<A> {

  public abstract A head();
  public abstract List<A> tail();
  public abstract boolean isEmpty();
  public abstract List<A> reverse();
  public abstract int length();
  public abstract <B> B foldLeft(B identity, Function<B, Function<A, B>> f);
  public abstract A reduce(Function<A, Function<A, A>> f);
  public abstract <B> List<B> map(Function<A, B> f);

  private List() {}

  public List<A> cons(A a) {
    return new Cons<>(a, this);
  }

  @SuppressWarnings("rawtypes")
  public static final List NIL = new Nil();

  private static class Nil<A> extends List<A> {

    private Nil() {}

    @Override
    public A head() {
      throw new IllegalStateException("head called en empty list");
    }

    @Override
    public List<A> tail() {
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
    public List<A> reverse() {
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
    public <B> List<B> map(Function<A, B> f) {
      return list();
    }

    @Override
    public A reduce(Function<A, Function<A, A>> f) {
      throw new IllegalStateException("Can't reduce and empty list without a zero");
    }
  }

  private static class Cons<A> extends List<A> {

    private final A head;
    private final List<A> tail;

    private Cons(A head, List<A> tail) {
      this.head = head;
      this.tail = tail;
    }

    @Override
    public A head() {
      return head;
    }

    @Override
    public List<A> tail() {
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

    private TailCall<StringBuilder> toString(StringBuilder acc, List<A> list) {
      return list.isEmpty()
          ? ret(acc)
          : sus(() -> toString(acc.append(list.head()).append(", "), list.tail()));
    }

    @Override
    public List<A> reverse() {
      return reverse_(list(), this).eval();
    }

    private TailCall<List<A>> reverse_(List<A> acc, List<A> list) {
      return list.isEmpty()
          ? ret(acc)
          : sus(() -> reverse_(new Cons<>(list.head(), acc), list.tail()));
    }

    @Override
    public <B> B foldLeft(B identity, Function<B, Function<A, B>> f) {
      return foldLeft_(identity, this, identity, f).eval();
    }

    private <B> TailCall<B> foldLeft_(B acc, List<A> list, B identity, Function<B, Function<A, B>> f) {
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
    public <B> List<B> map(Function<A, B> f) {
      return foldLeft(list(), t -> h -> new Cons<>(f.apply(h),t));
    }

    @Override
    public A reduce(Function<A, Function<A, A>> f) {
      return this.tail().foldLeft(this.head(), f);
    }
  }

  @SuppressWarnings("unchecked")
  public static <A> List<A> list() {
    return NIL;
  }

  @SafeVarargs
  public static <A> List<A> list(A... a) {
    List<A> n = list();
    for (int i = a.length - 1; i >= 0; i--) {
      n = new Cons<>(a[i], n);
    }
    return n;
  }

  public static List<Integer> range(int start, int end) {
    return range_(list(), start, end).eval();
  }

  public static TailCall<List<Integer>> range_(List<Integer> acc, int start, int end) {
    return start == end
        ? ret(acc)
        : sus(() -> range_(new Cons<>(start, acc), start + 1, end));
  }

  public static <A> List<A> fill(int n, Supplier<A> s) {
    return range(0, n).map(ignore -> s.get());
  }

  public <A1, A2> Tuple<List<A1>, List<A2>> unzip(Function<A, Tuple<A1, A2>> f) {
    return unzip(this.map(f));
  }

  public static <T, U> Tuple<List<T>, List<U>> unzip(List<Tuple<T, U>> list) {
    List<T> listT = list();
    List<U> listU = list();
    List<Tuple<T, U>> workList = list;
    while (!workList.isEmpty()) {
      listT = new Cons<>(workList.head()._1, listT);
      listU = new Cons<>(workList.head()._2, listU);
      workList = workList.tail();
    }
    return new Tuple<>(listT, listU);
  }

  public static <T, U> Map<U, List<T>> groupBy(List<T> list, Function<T, U> f) {
    if (list.isEmpty()) {
      return Map.empty();
    } else {
      List<T> workList = list;
      Map<U, List<T>> m = Map.empty();
      while (!workList.isEmpty()) {
        final U k = f.apply(workList.head());
        List<T> rt = m.get(k).getOrElse(list()).cons(workList.head());
        m = m.put(k, rt);
        workList = workList.tail();
      }
      return m;
    }
  }

  public <B> Map<B, List<A>> groupBy(Function<A, B> f) {
    return groupBy(this, f);
  }

  public static <T> List<T> fromCollection(Collection<T> ct) {
    List<T> lt = list();
    for (T t : ct) {
      lt = cons(t, lt);
    }
    return reverse(lt);
  }

  public static <T> List<T> cons(T t, List<T> list) {
    return new Cons<>(t, list);
  }

  public static <T> List<T> reverse(List<T> list) {
    return list.foldLeft(list(), x -> y -> new Cons<>(y, x));
  }
}
