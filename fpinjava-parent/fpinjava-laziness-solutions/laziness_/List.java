package com.fpinjava.laziness;

import java.util.Collection;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Map;
import com.fpinjava.common.Supplier;
import com.fpinjava.common.TailCall;
import com.fpinjava.common.Tuple;
import com.fpinjava.common.List.Cons;

import static com.fpinjava.common.TailCall.*;

public abstract class List<A> {

  public abstract A head();

  public abstract List<A> tail();

  public abstract boolean isEmpty();

  public abstract List<A> setHead(A h);

  public abstract List<A> drop(int n);

  public abstract List<A> dropWhile(Function<A, Boolean> f);

  public abstract List<A> reverse();

  public abstract List<A> init();

  public abstract int length();

  public abstract <B> B foldLeft(B identity, Function<B, Function<A, B>> f);

  public abstract <B> B foldRight(B identity, Function<A, Function<B, B>> f);

  public abstract <B> List<B> map(Function<A, B> f);

  public abstract List<A> filter(Function<A, Boolean> f);

  public abstract <B> List<B> flatMap(Function<A, List<B>> f);

  public abstract A reduce(Function<A, Function<A, A>> f);

  public List<A> cons(A a) {
    return new Cons<>(a, this);
  }

  public <B> Map<B, List<A>> groupBy(Function<A, B> f) {
    return groupBy(this, f);
  }

  @SuppressWarnings("rawtypes")
  public static final List NIL = new Nil();

  private List() {
  }

  private static class Nil<A> extends List<A> {

    private Nil() {
    }

    public A head() {
      throw new IllegalStateException("head called en empty list");
    }

    public List<A> tail() {
      throw new IllegalStateException("tail called en empty list");
    }

    public boolean isEmpty() {
      return true;
    }

    @Override
    public List<A> setHead(A h) {
      throw new IllegalStateException("setHead called en empty list");
    }

    public String toString() {
      return "[NIL]";
    }

    @Override
    public List<A> drop(int n) {
      return this;
    }

    @Override
    public List<A> dropWhile(Function<A, Boolean> f) {
      return this;
    }

    @Override
    public List<A> reverse() {
      return this;
    }

    @Override
    public List<A> init() {
      throw new IllegalStateException("init called on an empty list");
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
    public <B> B foldRight(B identity, Function<A, Function<B, B>> f) {
      return identity;
    }

    @Override
    public <B> List<B> map(Function<A, B> f) {
      return list();
    }

    @Override
    public List<A> filter(Function<A, Boolean> f) {
      return this;
    }

    @Override
    public <B> List<B> flatMap(Function<A, List<B>> f) {
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

    public A head() {
      return head;
    }

    public List<A> tail() {
      return tail;
    }

    public boolean isEmpty() {
      return false;
    }

    @Override
    public List<A> setHead(A h) {
      return new Cons<>(h, tail());
    }

    public String toString() {
      return String.format("[%sNIL]", toString(new StringBuilder(), this)
          .eval());
    }

    private TailCall<StringBuilder> toString(StringBuilder acc, List<A> list) {
      return list.isEmpty()
          ? ret(acc)
          : sus(() -> toString(acc.append(list.head()).append(", "),
              list.tail()));
    }

    @Override
    public List<A> drop(int n) {
      return n <= 0
          ? this
          : drop_(this, n).eval();
    }

    private TailCall<List<A>> drop_(List<A> list, int n) {
      return n <= 0 || list.isEmpty()
          ? ret(list)
          : sus(() -> drop_(list.tail(), n - 1));
    }

    @Override
    public List<A> dropWhile(Function<A, Boolean> f) {
      return dropWhile_(this, f).eval();
    }

    private TailCall<List<A>> dropWhile_(List<A> list, Function<A, Boolean> f) {
      return f.apply(list.head())
          ? sus(() -> dropWhile_(list.tail(), f))
          : ret(list);
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
    public List<A> init() {
      return reverse().tail().reverse();
    }

    @Override
    public int length() {
      return foldRight(this, 0, x -> y -> y + 1);
    }

    @Override
    public <B> B foldLeft(B identity, Function<B, Function<A, B>> f) {
      return foldLeft_(identity, this, identity, f).eval();
    }

    private <B> TailCall<B> foldLeft_(B acc, List<A> list, B identity, Function<B, Function<A, B>> f) {
      if (!list.isEmpty()) System.out.println("Processing " + list.head());
      return list.isEmpty()
          ? ret(acc)
          : sus(() -> foldLeft_(f.apply(acc).apply(list.head()), list.tail(),
              identity, f));
    }

    @Override
    public <B> B foldRight(B identity, Function<A, Function<B, B>> f) {
      return foldLeft(Function.<B> identity(),
          g -> a -> b -> g.apply(f.apply(a).apply(b))).apply(identity);
    }

    @Override
    public <B> List<B> map(Function<A, B> f) {
      return foldRight(list(), h -> t -> new Cons<>(f.apply(h), t));
    }

    @Override
    public List<A> filter(Function<A, Boolean> f) {
      return foldRight(list(), h -> t -> f.apply(h)
          ? new Cons<>(h, t)
          : t);
    }

    @Override
    public <B> List<B> flatMap(Function<A, List<B>> f) {
      return foldRight(list(), h -> t -> concat(f.apply(h), t));
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

  public static <A, B> B foldRight(List<A> list, B n, Function<A, Function<B, B>> f) {
    return list.isEmpty()
        ? n
        : f.apply(list.head()).apply(foldRight(list.tail(), n, f));
  }

  public static <A> List<A> concat(List<A> list1, List<A> list2) {
    return foldRight(list1, list2, x -> y -> new Cons<>(x, y));
  }

  public static <A> List<A> flatten(List<List<A>> list) {
    return foldRight(list, List.<A> list(), x -> y -> concat(x, y));
  }

  public static <A, B, C> List<C> zipWith(List<A> list1, List<B> list2, Function<A, Function<B, C>> f) {
    return zipWith_(list(), list1, list2, f).eval().reverse();
  }

  private static <A, B, C> TailCall<List<C>> zipWith_(List<C> acc, List<A> list1, List<B> list2, Function<A, Function<B, C>> f) {
    return list1.isEmpty() || list2.isEmpty()
        ? ret(acc)
        : sus(() -> zipWith_(
            new Cons<>(f.apply(list1.head()).apply(list2.head()), acc),
            list1.tail(), list2.tail(), f));
  }
  
  public static <A> boolean hasSubsequence(List<A> list, List<A> sub) {
    return hasSubsequence_(list, sub).eval();
  }

  public static <A> TailCall<Boolean> hasSubsequence_(List<A> list, 
                                                      List<A> sub) {
    return list.isEmpty()
        ? ret(Boolean.FALSE)
        : sub.isEmpty()
            ? ret(Boolean.TRUE)
            : list.head().equals(sub.head())
                ? ret(startsWith(list.tail(), sub.tail()))
                : sus(() -> hasSubsequence_(list.tail(), sub));
  }

  public static <A> Boolean startsWith(List<A> list, List<A> sub) {
    return startsWith_(list, sub).eval();
  }

  private static <A> TailCall<Boolean> startsWith_(List<A> list, 
                                                  List<A> sub) {
    return sub.isEmpty()
        ? ret(Boolean.TRUE)
        : list.isEmpty()
            ? ret(Boolean.FALSE)
            : list.head().equals(sub.head())
                ? sus(() -> startsWith_(list.tail(), sub.tail()))
                : ret(Boolean.FALSE);
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

  public static <T> List<T> fromCollection(Collection<T> ct) {
    List<T> lt = list();
    for (T t : ct) {
      lt = cons(t, lt);
    }
    return lt.reverse();
  }

  public static <T> List<T> cons(T t, List<T> list) {
    return new Cons<>(t, list);
  }

  public static <T> List<T> reverse(List<T> list) {
    return list.foldLeft(list(), x -> y -> new Cons<>(y, x));
  }

  public static List<Integer> range(int start, int end) {
    return range_(list(), start, end - 1).eval();
  }

  public static TailCall<List<Integer>> range_(List<Integer> acc, int start, int end) {
    return start >= end + 1
        ? ret(acc)
        : sus(() -> range_(new Cons<>(end, acc), start, end - 1));
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
}