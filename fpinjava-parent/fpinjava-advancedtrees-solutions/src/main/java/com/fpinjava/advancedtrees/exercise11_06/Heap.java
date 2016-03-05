package com.fpinjava.advancedtrees.exercise11_06;


import com.fpinjava.common.Result;

import java.util.NoSuchElementException;

public abstract class Heap<A extends Comparable<A>> {

  @SuppressWarnings("rawtypes")
  protected static final Heap EMPTY = new Empty();

  protected abstract Result<Heap<A>> left();
  protected abstract Result<Heap<A>> right();
  protected abstract int rank();

  public abstract Result<A> get(int index);
  public abstract Result<A> head();
  public abstract Result<Heap<A>> tail();
  public abstract int length();
  public abstract boolean isEmpty();

  public Heap<A> add(A element) {
    return merge(this, heap(element));
  }

  public static class Empty<A extends Comparable<A>> extends Heap<A> {

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
    protected Result<Heap<A>> left() {
      return Result.success(empty());
    }

    @Override
    protected Result<Heap<A>> right() {
      return Result.success(empty());
    }

    @Override
    public boolean isEmpty() {
      return true;
    }
  }

  public static class H<A extends Comparable<A>> extends Heap<A> {

    private final int length;
    private final int rank;
    private final A head;
    private final Heap<A> left;
    private final Heap<A> right;

    private H(int length, int rank, Heap<A> left, A head, Heap<A> right) {
      this.length = length;
      this.rank = rank;
      this.head = head;
      this.left = left;
      this.right = right;
    }

    @Override
    protected int rank() {
      return this.rank;
    }

    @Override
    public Result<A> get(int index) {
      throw new IllegalStateException("To be implemented");
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
  }

  @SuppressWarnings("unchecked")
  public static <A extends Comparable<A>> Heap<A> empty() {
    return EMPTY;
  }

  public static <A extends Comparable<A>> Heap<A> heap(A element) {
    return new H<>(1, 1, empty(), element, empty());
  }

  protected static <A extends Comparable<A>> Heap<A> heap(A head, Heap<A> first, Heap<A> second) {
    return first.rank() >= second.rank()
        ? new H<>(first.length() + second.length() + 1, second.rank() + 1, first, head, second)
        : new H<>(first.length() + second.length() + 1, first.rank() + 1, second, head, first);
  }

  public static <A extends Comparable<A>> Heap<A> merge(Heap<A> first, Heap<A> second) {
    return first.head().flatMap(fh -> second.head().flatMap(sh -> fh.compareTo(sh) <= 0
        ? first.left().flatMap(fl -> first.right().map(fr -> heap(fh, fl, merge(fr, second))))
        : second.left().flatMap(sl -> second.right().map(sr -> heap(sh, sl, merge(first, sr))))))
        .getOrElse(first.isEmpty()
            ? second
            : first);
  }
}
