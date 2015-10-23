package com.fpinjava.advancedlisthandling.exercise08_19;

import com.fpinjava.common.Function;
import com.fpinjava.common.Map;
import com.fpinjava.common.Result;
import com.fpinjava.common.TailCall;
import com.fpinjava.common.Tuple;
import com.fpinjava.common.Tuple3;

import java.util.Objects;

import static com.fpinjava.common.TailCall.ret;
import static com.fpinjava.common.TailCall.sus;


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
  public abstract <B> Tuple<B, List<A>> foldLeft(B identity, B zero, Function<B, Function<A, B>> f);
  public abstract <B> B foldRight(B identity, Function<A, Function<B, B>> f);
  public abstract <B> List<B> map(Function<A, B> f);
  public abstract List<A> filter(Function<A, Boolean> f);
  public abstract <B> List<B> flatMap(Function<A, List<B>> f);
  public abstract Result<A> headOption();

  public Result<A> lastOption() {
    return foldLeft(Result.empty(), x -> Result::success);
  }

  public List<A> cons(A a) {
    return new Cons<>(a, this);
  }

  public <A1, A2> Tuple<List<A1>, List<A2>> unzip(Function<A, Tuple<A1, A2>> f) {
    return this.foldRight(new Tuple<>(list(), list()), a -> tl -> {
      Tuple<A1, A2> t = f.apply(a);
      return new Tuple<>(tl._1.cons(t._1), tl._2.cons(t._2));
    });
  }

  public Result<A> getAt_(int index) {
    return index < 0 || index >= length()
        ? Result.failure("Index out of bound")
        : getAt(this, index).eval();
  }

  private static <A> TailCall<Result<A>> getAt(List<A> list, int index) {
      return index == 0
                ? TailCall.ret(Result.success(list.head()))
                : TailCall.sus(() -> getAt(list.tail(), index - 1));
  }

  public Result<A> getAt(int index) {
    Tuple<Result<A>, Integer> identity = new Tuple<>(Result.failure("Index out of bound"), index);
    Tuple<Result<A>, Integer> rt = index < 0 || index >= length()
        ? identity
        : foldLeft(identity, ta -> a -> ta._2 < 0 ? ta : new Tuple<>(Result.success(a), ta._2 - 1));
    return rt._1;
  }

  public Result<A> getAt__(int index) {
    class Tuple<T, U> {

      public final T _1;
      public final U _2;

      public Tuple(T t, U u) {
        this._1 = Objects.requireNonNull(t);
        this._2 = Objects.requireNonNull(u);
      }

      @Override
      public String toString() {
        return String.format("(%s,%s)", _1,  _2);
      }

      @Override
      public boolean equals(Object o) {
        if (!(o.getClass() == this.getClass()))
          return false;
        else {
          @SuppressWarnings("rawtypes")
          Tuple that = (Tuple) o;
          return _2.equals(that._2);
        }
      }

      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + _1.hashCode();
        result = prime * result + _2.hashCode();
        return result;
      }
    }
    Tuple<Result<A>, Integer> zero = new Tuple<>(Result.failure("Index out of bound"), -1);
    Tuple<Result<A>, Integer> identity = new Tuple<>(Result.failure("Index out of bound"), index);
    Tuple<Result<A>, Integer> rt = index < 0 || index >= length()
        ? identity
        : foldLeft(identity, zero, ta -> a -> ta._2 < 0 ? ta : new Tuple<>(Result.success(a), ta._2 - 1))._1;
    return rt._1;
  }

  public Tuple<List<A>, List<A>> splitAt(int index) {
    return index < 0
        ? splitAt(0)
        : index > length()
            ? splitAt(length())
            : splitAt(list(), this.reverse(), this.length() - index).eval();
  }

  private TailCall<Tuple<List<A>, List<A>>> splitAt(List<A> acc, List<A> list, int i) {
    return i == 0 || list.isEmpty()
        ? ret(new Tuple<>(list.reverse(), acc))
        : sus(() -> splitAt(acc.cons(list.head()), list.tail(), i - 1));
  }

  public Tuple<List<A>, List<A>> splitAt_(int index) {
    int ii = index < 0 ? 0 : index >= length() ? length() : index;
    Tuple3<List<A>, List<A>, Integer> identity = new Tuple3<>(List.list(), List.list(), ii);
    Tuple3<List<A>, List<A>, Integer> rt = foldLeft(identity, ta -> a -> ta._3 == 0 ? new Tuple3<>(ta._1, ta._2.cons(a), ta._3) : new Tuple3<>(ta._1.cons(a), ta._2, ta._3 - 1));
    return new Tuple<>(rt._1.reverse(), rt._2.reverse());
  }

  public Tuple<List<A>, List<A>> splitAt__(int index) {
    class Tuple3<T, U, V> {

      public final T _1;
      public final U _2;
      public final V _3;

      public Tuple3(T t, U u, V v) {
        this._1 = Objects.requireNonNull(t);
        this._2 = Objects.requireNonNull(u);
        this._3 = Objects.requireNonNull(v);
      }

      @Override
      public String toString() {
        return String.format("(%s,%s,%s)", _1,  _2, _3);
      }

      @Override
      public boolean equals(Object o) {
        if (!(o.getClass() == this.getClass()))
          return false;
        else {
          @SuppressWarnings("rawtypes")
          Tuple3 that = (Tuple3) o;
          return _3.equals(that._3);
        }
      }

      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + _1.hashCode();
        result = prime * result + _2.hashCode();
        result = prime * result + _3.hashCode();
        return result;
      }
    }
    Tuple3<List<A>, List<A>, Integer> zero = new Tuple3<>(list(), list(), 0);
    Tuple3<List<A>, List<A>, Integer> identity = new Tuple3<>(list(), list(), index);

    Tuple<Tuple3<List<A>, List<A>, Integer>, List<A>> rt = index <= 0
        ? new Tuple<>(identity, this)
        : foldLeft(identity, zero, ta -> a ->
            ta._3 < 0
                ? ta
                : new Tuple3<>(ta._1.cons(a), ta._2, ta._3 - 1));
    return new Tuple<>(rt._1._1.reverse(), rt._2);
  }

  public <B> Map<B, List<A>> groupBy_(Function<A, B> f) {
    List<A> workList = this;
    Map<B, List<A>> m = Map.empty();
    while (!workList.isEmpty()) {
      final B k = f.apply(workList.head());
      List<A> rt = m.get(k).getOrElse(list()).cons(workList.head());
      m = m.put(k, rt);
      workList = workList.tail();
    }
    return m;
  }

  public <B> Map<B, List<A>> groupBy(Function<A, B> f) {
    return foldRight(Map.empty(), t -> mt -> {
      final B k = f.apply(t);
      return mt.put(k, mt.get(k).getOrElse(list()).cons(t));
    });
  }

  @SuppressWarnings("rawtypes")
  public static final List NIL = new Nil();

  private List() {}

  private static class Nil<A> extends List<A> {

    private Nil() {}

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
    public <B> Tuple<B, List<A>> foldLeft(B identity, B zero, Function<B, Function<A, B>> f) {
      return new Tuple<>(identity, list());
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
    public Result<A> headOption() {
      return Result.empty();
    }
  }

  private static class Cons<A> extends List<A> {

    private final A head;
    private final List<A> tail;
    private final int length;

    private Cons(A head, List<A> tail) {
      this.head = head;
      this.tail = tail;
      this.length = tail.length() + 1;
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
      return String.format("[%sNIL]", toString(new StringBuilder(), this).eval());
    }

    private TailCall<StringBuilder> toString(StringBuilder acc, List<A> list) {
      return list.isEmpty()
          ? ret(acc)
          : sus(() -> toString(acc.append(list.head()).append(", "), list.tail()));
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
      return !list.isEmpty() && f.apply(list.head())
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
      return length;
    }

    @Override
    public <B> B foldLeft(B identity, Function<B, Function<A, B>> f) {
      return foldLeft(identity, this, f).eval();
    }

    private <B> TailCall<B> foldLeft(B acc, List<A> list, Function<B, Function<A, B>> f) {
      return list.isEmpty()
          ? ret(acc)
          : sus(() -> foldLeft(f.apply(acc).apply(list.head()), list.tail(), f));
    }

    @Override
    public <B> Tuple<B, List<A>> foldLeft(B identity, B zero, Function<B, Function<A, B>> f) {
      return foldLeft(identity, zero, this, f).eval();
    }

    private <B> TailCall<Tuple<B, List<A>>> foldLeft(B acc, B zero, List<A> list, Function<B, Function<A, B>> f) {
      return list.isEmpty() || acc.equals(zero)
          ? ret(new Tuple<>(acc, list))
          : sus(() -> foldLeft(f.apply(acc).apply(list.head()), zero, list.tail(), f));
    }

    @Override
    public <B> B foldRight(B identity, Function<A, Function<B, B>> f) {
      return reverse().foldLeft(identity, x -> y -> f.apply(y).apply(x));
    }

    @Override
    public <B> List<B> map(Function<A, B> f) {
      return foldRight(list(), h -> t -> new Cons<>(f.apply(h),t));
    }

    @Override
    public List<A> filter(Function<A, Boolean> f) {
      return foldRight(list(), h -> t -> f.apply(h) ? new Cons<>(h,t) : t);
    }

    @Override
    public <B> List<B> flatMap(Function<A, List<B>> f) {
      /* Java is unable to infer type of the second parameter for the second function */
      /* return foldRight(list(), h -> t -> f.apply(h).foldRight(t, x -> (List<B> y) -> new Cons<>(x, y))); */
      return foldRight(list(), h -> t -> concat(f.apply(h), t));
    }

    @Override
    public Result<A> headOption() {
      return Result.success(head);
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

  public static <A, B> B foldRight(List<A> list, B n, Function<A, Function<B, B>> f ) {
    return list.foldRight(n, f);
  }

  public static <A> List<A> concat(List<A> list1, List<A> list2) {
    return foldRight(list1, list2, x -> y -> new Cons<>(x, y));
  }

  public static <A> List<A> flatten(List<List<A>> list) {
    return foldRight(list, List.<A>list(), x -> y -> concat(x, y));
  }

  public static <A> List<A> flattenResult(List<Result<A>> list) {
    return flatten(list.foldRight(list(), x -> y -> y.cons(x.map(List::list).getOrElse(list()))));
  }

  public static <A, B> Result<List<B>> traverse(List<A> list, Function<A, Result<B>> f) {
    return list.foldRight(Result.success(List.list()), x -> y -> Result.map2(f.apply(x), y, a -> b -> b.cons(a)));
  }

  public static <A> Result<List<A>> sequence(List<Result<A>> list) {
    return traverse(list, x -> x);
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

  public static <A, B, C> List<C> product(List<A> list1, List<B> list2, Function<A, Function<B, C>> f) {
    return list1.flatMap(a -> list2.map(b -> f.apply(a).apply(b)));
  }

  public static <A1, A2> Tuple<List<A1>, List<A2>> unzip(List<Tuple<A1, A2>> list) {
    return list.foldRight(new Tuple<>(list(), list()), t -> tl -> new Tuple<>(tl._1.cons(t._1), tl._2.cons(t._2)));
  }

  public static <A> boolean hasSubList(List<A> list, List<A> sub) {
    return hasSubList_(list, sub).eval();
  }

  public static <A> TailCall<Boolean> hasSubList_(List<A> list, List<A> sub) {
    return list.isEmpty()
        ? ret(sub.isEmpty())
        : startsWith(list, sub)
            ? ret(true)
            : sus(() -> hasSubList_(list.tail(), sub));
  }

  public static <A> Boolean startsWith(List<A> list, List<A> sub) {
    return startsWith_(list, sub).eval();
  }

  private static <A> TailCall<Boolean> startsWith_(List<A> list, List<A> sub) {
    return sub.isEmpty()
        ? ret(Boolean.TRUE)
        : list.isEmpty()
            ? ret(Boolean.FALSE)
            : list.head().equals(sub.head())
                ? sus(() -> startsWith_(list.tail(), sub.tail()))
                : ret(Boolean.FALSE);
  }

  /**
   * Caution: not stack safe
   */
  public static <A, S> List<A> unfold_(S z, Function<S, Result<Tuple<A, S>>> f) {
    return f.apply(z).map(x -> unfold_(x._2, f).cons(x._1)).getOrElse(list());
  }

  public static <A, S> List<A> unfold(S z, Function<S, Result<Tuple<A, S>>> f) {
    return unfold(list(), z, f).eval().reverse();
  }

  private static <A, S> TailCall<List<A>> unfold(List<A> acc, S z, Function<S, Result<Tuple<A, S>>> f) {
    Result<Tuple<A, S>> r = f.apply(z);
    Result<TailCall<List<A>>> result = r.map(rt -> sus(() -> unfold(acc.cons(rt._1), rt._2, f)));
    return result.getOrElse(ret(acc));
  }

  public static List<Integer> range(int start, int end) {
    return List.unfold(start, i -> i < end
        ? Result.success(new Tuple<>(i, i + 1))
        : Result.empty());
  }

}
