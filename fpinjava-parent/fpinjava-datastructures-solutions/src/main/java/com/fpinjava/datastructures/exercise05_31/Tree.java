package com.fpinjava.datastructures.exercise05_31;

import com.fpinjava.common.Function;

public abstract class Tree<A> {

  public abstract Tree<A> left();
  public abstract Tree<A> right();
  public abstract A value();
  public abstract boolean isLeaf();
  public abstract int size();
  public abstract <B> Tree<B> map(Function<A, B> f);
  
  private Tree() {}

  private static class Leaf<A> extends Tree<A> {

    private final A value;

    private Leaf(A value) {
      this.value = value;
    }

    public Tree<A> left() {
      throw new IllegalStateException("left called on Leaf");
    }

    public Tree<A> right() {
      throw new IllegalStateException("right called on Leaf");
    }

    public A value() {
      return this.value;
    }

    @Override
    public int size() {
      return 1;
    }

    @Override
    public boolean isLeaf() {
      return true;
    }

    @Override
    public <B> Tree<B> map(Function<A, B> f) {
      return new Leaf<>(f.apply(this.value));
    }
  }

  private static class Branch<A> extends Tree<A> {

    private final Tree<A> left;
    private final Tree<A> right;
    
    private Branch(Tree<A> left, Tree<A> right) {
      this.left = left;
      this.right = right;
    }

    public Tree<A> left() {
      return this.left;
    }

    @Override
    public Tree<A> right() {
      return this.right;
    }

    @Override
    public A value() {
      throw new IllegalStateException("value called on Branch");
    }

    @Override
    public int size() {
      return 1 + left.size() + right.size();
    }

    @Override
    public boolean isLeaf() {
      return false;
    }

    @Override
    public <B> Tree<B> map(Function<A, B> f) {
      return new Branch<>(this.left.map(f), this.right.map(f));
    }
  }

  public static <A> Tree<A> tree(A a) {
    return new Leaf<>(a);
  }

  public static <A> Tree<A> tree(Tree<A> left, Tree<A> right) {
    return new Branch<>(left, right);
  }
  
  public static int maximum(Tree<Integer> t) {
    return t.isLeaf()
        ? t.value()
        : Math.max(maximum(t.left()), maximum(t.right()));
  }
  
  public static int depth(Tree<Integer> t) {
    return t.isLeaf()
        ? 0
        : 1 + Math.max(depth(t.left()), depth(t.right()));
  }
}