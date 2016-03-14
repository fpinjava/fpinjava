package com.fpinjava.trees.listing10_01;


public abstract class Tree<A extends Comparable<A>> { // #A

  @SuppressWarnings("rawtypes")
  private static Tree EMPTY = new Empty(); // #B

  public abstract A value(); // #C

  abstract Tree<A> left(); // #D

  abstract Tree<A> right();

  private static class Empty<A extends Comparable<A>> extends Tree<A> { // #E

    @Override
    public A value() {
      throw new IllegalStateException("value() called on empty");
    }

    @Override
    Tree<A> left() {
      throw new IllegalStateException("left() called on empty");
    }

    @Override
    Tree<A> right() {
      throw new IllegalStateException("right() called on empty");
    }

    @Override
    public String toString() {
      return "E";
    }
  }

  private static class T<A extends Comparable<A>> extends Tree<A> { // #F

    private final Tree<A> left;
    private final Tree<A> right;
    private final A value;

    private T(Tree<A> left, A value, Tree<A> right) {
      this.left = left;
      this.right = right;
      this.value = value;
    }

    @Override
    public A value() {
      return value;
    }

    @Override
    Tree<A> left() {
      return left;
    }

    @Override
    Tree<A> right() {
      return right;
    }

    @Override
    public String toString() {
      return String.format("(T %s %s %s)", left, value, right);
    }
  }

  @SuppressWarnings("unchecked")
  public static <A extends Comparable<A>> Tree<A> empty() { // #G
    return EMPTY;
  }
}
