package com.fpinjava.advancedtrees.listing11_03;



import com.fpinjava.common.Result;

import java.util.NoSuchElementException;

public abstract class Heap<A extends Comparable<A>> {

  @SuppressWarnings("rawtypes")
  protected static final Heap EMPTY = new Empty();

  protected abstract Result<Heap<A>> left();
  protected abstract Result<Heap<A>> right();
  protected abstract int rank();

  public abstract Result<A> head();
  public abstract int length();
  public abstract boolean isEmpty();

  public static class Empty<A extends Comparable<A>> extends Heap<A> {

    private Empty() {}

    @Override
    protected int rank() {
      return 0;
    }

    @Override
    public Result<A> head() {
      return Result.failure(new NoSuchElementException("head() called on empty heap"));
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
    public Result<A> head() {
      return Result.success(this.head);
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
}
