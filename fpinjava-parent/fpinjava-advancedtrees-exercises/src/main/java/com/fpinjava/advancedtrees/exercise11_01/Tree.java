package com.fpinjava.advancedtrees.exercise11_01;


/*
 * see http://www.cs.cmu.edu/~rwh/theses/okasaki.pdf
 */
public abstract class Tree<A extends Comparable<A>> {

  private static Tree E = new E();
  private static Color R = new Red();
  private static Color B = new Black();

  protected abstract boolean isE();

  protected abstract boolean isT();
  protected abstract boolean isB();
  protected abstract boolean isTR();

  public abstract boolean isEmpty();

  protected abstract Tree<A> right();
  protected abstract Tree<A> left();
  protected abstract A value();
  public abstract int size();
  public abstract int height();

  protected static <A extends Comparable<A>> Tree<A> blacken(Tree<A> t) {
    throw new IllegalStateException("To be implemented");
  }

  public Tree<A> insert(A value) {
    throw new IllegalStateException("To be implemented");
  }

  private static class E<A extends Comparable<A>> extends Tree<A> {

    @Override
    protected boolean isE() {
      return true;
    }

    @Override
    public int size() {
      return 0;
    }

    @Override
    public int height() {
      return -1;
    }

    @Override
    public Tree<A> right() {
      return empty();
    }

    @Override
    public Tree<A> left() {
      return empty();
    }

    @Override
    protected A value() {
      throw new IllegalStateException("value called on Empty");
    }

    @Override
    protected boolean isT() {
      return false;
    }

    @Override
    protected boolean isB() {
      return true;
    }

    @Override
    protected boolean isTR() {
      return false;
    }

    @Override
    public boolean isEmpty() {
      return true;
    }

    @Override
    public String toString() {
      return "E";
    }
  }

  private static class T<A extends Comparable<A>> extends Tree<A> {
    private final Tree<A> left;
    private final Tree<A> right;
    private final A value;
    private final Color color;
    private final int length;
    private final int height;

    private T(Color color, Tree<A> left, A value, Tree<A> right) {
      this.color = color;
      this.left = left;
      this.right = right;
      this.value = value;
      this.length = left.size() + 1 + right.size();
      this.height = Math.max(left.height(), right.height()) + 1;
    }

    private Tree<A> balance(Color color, Tree<A> left, A value, Tree<A> right) {
      throw new IllegalStateException("To be implemented");
    }

    @Override
    public boolean isB() {
      return this.color.isB();
    }

    @Override
    protected boolean isTR() {
      return this.color.isR();
    }

    @Override
    protected boolean isE() {
      return false;
    }

    @Override
    protected boolean isT() {
      return true;
    }

    @Override
    public int size() {
      return length;
    }

    @Override
    public int height() {
      return height;
    }

    @Override
    public boolean isEmpty() {
      return false;
    }

    @Override
    protected Tree<A> right() {
      return right;
    }

    @Override
    protected Tree<A> left() {
      return left;
    }

    @Override
    protected A value() {
      return value;
    }

    @Override
    public String toString() {
      return String.format("(T %s %s %s %s)", color, left, value, right);
    }
  }

  private static abstract class Color {
    abstract boolean isR();
    abstract boolean isB();
  }

  private static class Red extends Color {

    @Override
    boolean isR() {
      return true;
    }

    @Override
    boolean isB() {
      return false;
    }

    @Override
    public String toString() {
      return "R";
    }
  }

  private static class Black extends Color {

    @Override
    boolean isR() {
      return false;
    }

    @Override
    boolean isB() {
      return true;
    }

    @Override
    public String toString() {
      return "B";
    }
  }

  @SuppressWarnings("unchecked")
  public static <A extends Comparable<A>> Tree<A> empty() {
    return E;
  }
}
