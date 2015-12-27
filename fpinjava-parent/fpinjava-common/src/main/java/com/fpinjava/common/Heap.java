package com.fpinjava.common;


import java.util.Comparator;
import java.util.NoSuchElementException;

public abstract class Heap<A> {

  protected abstract Result<Heap<A>> left();
  protected abstract Result<Heap<A>> right();
  protected abstract int rank();
  protected abstract Result<Comparator<A>> comparator();
  protected abstract A headOrThrow();
  protected abstract Heap<A> tailOrThrow();

  public abstract Result<A> get(int index);
  public abstract Result<A> head();
  public abstract Result<Heap<A>> tail();
  public abstract int length();
  public abstract boolean isEmpty();
  public abstract int height();

  public abstract Heap<A> insert(A a);

  public Heap<A> add(A element) {
    return merge(this, heap(element, this.comparator()));
  }

  public List<A> toList() {
    return toList(List.list(), this).eval();
  }

  public static <A> List<A> toList(Heap<A> heap) {
    return heap.tail().flatMap(tail -> heap.head().map(head -> toList(tail).cons(head))).getOrElse(List.list());
  }

  public TailCall<List<A>> toList(List<A> acc, Heap<A> heap) {
    return heap.isEmpty()
        ? TailCall.ret(acc)
        : TailCall.sus(() -> toList(acc.cons(heap.headOrThrow()), heap.tailOrThrow()));
  }

  public static class Empty<A> extends Heap<A> {

    private final Result<Comparator<A>> comparator;

    private Empty(Result<Comparator<A>> comparator) {
      this.comparator = comparator;
    }

    @Override
    protected Result<Comparator<A>> comparator() {
      return this.comparator;
    }

    @Override
    protected A headOrThrow() {
      throw new IllegalStateException("headOrThrow called in Empty");
    }

    @Override
    protected Heap<A> tailOrThrow() {
      throw new IllegalStateException("tailOrThrow called in Empty");
    }

    @Override
    protected int rank() {
      return 0;
    }

    @Override
    public Result<A> get(int index) {
      throw new IllegalStateException("To be implemented");
    }

    @Override
    public Result<A> head() {
      return Result.failure(new NoSuchElementException("Method head() called on empty heap"));
    }

    @Override
    public Result<Heap<A>> tail() {
      return Result.failure(new NoSuchElementException("Method tail() called on empty heap"));
    }

    @Override
    public int length() {
      return 0;
    }

    @Override
    public Result<Heap<A>> left() {
      return Result.success(empty(this.comparator));
    }

    @Override
    protected Result<Heap<A>> right() {
      return Result.success(empty(this.comparator));
    }

    @Override
    public boolean isEmpty() {
      return true;
    }

    @Override
    public int height() {
      return 0;
    }

    @Override
    public Heap<A> insert(A a) {
      return heap(a, this, this);
    }

    @Override
    public String toString() {
      return "E";
    }
  }

  public static class H<A> extends Heap<A> {

    private final int length;
    private final int rank;
    private final A head;
    private final Heap<A> left;
    private final Heap<A> right;
    private final Result<Comparator<A>> comparator;

    private H(int length, int rank, A head, Heap<A> left, Heap<A> right, Result<Comparator<A>> comparator) {
      this.length = length;
      this.rank = rank;
      this.head = head;
      this.left = left;
      this.right = right;
      this.comparator = comparator;
    }

    protected Result<Comparator<A>> comparator() {
      return this.comparator;
    }

    @Override
    protected A headOrThrow() {
      return head;
    }

    @Override
    protected Heap<A> tailOrThrow() {
      return Heap.merge(left, right);
    }

    @Override
    protected int rank() {
      return this.rank;
    }

    @Override
    public Result<A> get(int index) {
      return index == 0
          ? head()
          : tail().flatMap(x -> x.get(index - 1));
    }

    @Override
    public Result<A> head() {
      return Result.success(this.head);
    }

    @Override
    public Result<Heap<A>> tail() {
      return Result.success(Heap.merge(left, right));
    }

    @Override
    public int length() {
      return this.length;
    }

    @Override
    protected Result<Heap<A>> left() {
      return Result.success(this.left);
    }

    @Override
    protected Result<Heap<A>> right() {
      return Result.success(this.right);
    }

    @Override
    public boolean isEmpty() {
      return false;
    }

    @Override
    public int height() {
      return 1 + Math.max(left.height(), right.height());
    }

    @Override
    public Heap<A> insert(A a) {
      int comp = compare(head, a, comparator);
      return heap(comp < 0
          ? head
          : a, left, right.insert(comp >= 0
          ? head
          : a));
    }

    @Override
    public String toString() {
      return String.format("(H, %s, %s, %s)", left, head, right);
    }
  }

  public static <A extends Comparable<A>> Heap<A> empty() {
    return empty(Result.empty());
  }

  public static <A> Heap<A> empty(Comparator<A> comparator) {
    return empty(Result.success(comparator));
  }

  public static <A> Heap<A> empty(Result<Comparator<A>> comparator) {
    return new Empty<>(comparator);
  }

  public static <A extends Comparable<A>> Heap<A> heap(A element) {
    return heap(element, Result.empty());
  }

  public static <A> Heap<A> heap(A element, Result<Comparator<A>> comparator) {
    Heap<A> empty = empty(comparator);
    return new H<>(1, 1, element, empty, empty, comparator);
  }

  public static <A> Heap<A> heap(A element, Comparator<A> comparator) {
    Heap<A> empty = empty(comparator);
    return new H<>(1, 1, element, empty, empty, Result.success(comparator));
  }

  public static <A> Heap<A> heap(A head, Heap<A> first, Heap<A> second) {
    Result<Comparator<A>> comparator = first.comparator().orElse(second::comparator);
    return first.rank() >= second.rank()
        ? new H<>(first.length() + second.length() + 1, second.rank() + 1, head, first, second, comparator)
        : new H<>(first.length() + second.length() + 1, first.rank() + 1, head, second, first, comparator);
  }

  public static <A> Heap<A> merge(Heap<A> first, Heap<A> second) {
    Result<Comparator<A>> comparator = first.comparator().orElse(second::comparator);
    return merge(first, second, comparator);
  }

  public static <A> Heap<A> merge(Heap<A> first, Heap<A> second, Result<Comparator<A>> comparator) {
    return first.head().flatMap(fh -> second.head().flatMap(sh -> compare(fh, sh, comparator) <= 0
        ? first.left().flatMap(fl -> first.right().map(fr -> heap(fh, fl, merge(fr, second, comparator))))
        : second.left().flatMap(sl -> second.right().map(sr -> heap(sh, sl, merge(first, sr, comparator))))))
                .getOrElse(first.isEmpty()
                    ? second
                    : first);
  }

  @SuppressWarnings("unchecked")
  public static <A> int compare(A first, A second, Result<Comparator<A>> comparator) {
    return comparator.map(comp -> comp.compare(first, second)).getOrElse(() -> ((Comparable<A>) first).compareTo(second));
  }

  public static <A> A min(A a1, A a2, Result<Comparator<A>> comparator) {
    return compare(a1, a2, comparator) < 0
        ? a1
        : a2;
  }
}
