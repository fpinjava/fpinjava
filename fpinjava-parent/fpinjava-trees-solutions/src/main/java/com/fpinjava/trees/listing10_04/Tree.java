package com.fpinjava.trees.listing10_04;


/*
 * see http://www.cs.cmu.edu/~rwh/theses/okasaki.pdf
 * see http://matt.might.net/papers/germane2014deletion.pdf
 * see http://matt.might.net/articles/red-black-delete/
 */
public abstract class Tree<A extends Comparable<A>> {

  private static Tree E = new E();
  private static Tree EE = new E();
  private static Color R = new Red();
  private static Color B = new Black();
  private static Color BB = new DoubleBlack();
  private static Color NB = new NegativeBlack();

  protected abstract boolean isE();

  protected abstract boolean isT();
  protected abstract boolean isB();
  protected abstract boolean isBB();
  protected abstract boolean isR();
  protected abstract boolean isTB();
  protected abstract boolean isTR();

  public abstract boolean isEmpty();

  protected abstract Tree<A> right();
  protected abstract Tree<A> left();
  protected abstract A value();
  public abstract int size();
  public abstract int height();

  protected abstract Tree<A> ins(A value);

  protected abstract Tree<A> blacken(Tree<A> t);

  public Tree<A> insert(A value) {
    return blacken(ins(value));
  }

  private static abstract class Empty<A extends Comparable<A>> extends Tree<A> {

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
    protected Tree<A> ins(A value) {
      return new T<>(R, empty(), value, empty());
    }

    @Override
    protected Tree<A> blacken(Tree<A> t) {
      return empty();
    }

    @Override
    public boolean isR() {
      return false;
    }

    @Override
    protected boolean isT() {
      return false;
    }

    @Override
    protected boolean isTB() {
      return false;
    }

    @Override
    protected boolean isTR() {
      return false;
    }

    @Override
    public boolean isEmpty() {
      return true;
    }
  }

  private static class E<A extends Comparable<A>> extends Empty<A> {

    @Override
    public boolean isE() {
      return true;
    }

    @Override
    protected boolean isB() {
      return true;
    }

    @Override
    protected boolean isBB() {
      return false;
    }

    @Override
    public String toString() {
      return "E";
    }
  }

  private static class EE<A extends Comparable<A>> extends Empty<A> {

    @Override
    protected boolean isE() {
      return true;
    }

    @Override
    protected boolean isB() {
      return false;
    }

    @Override
    protected boolean isBB() {
      return true;
    }

    @Override
    public String toString() {
      return "EE";
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
      // balance (T B (T R (T R a x b) y c) z d) = (T R (T B a x b ) y (T B c z d))
      if (color.isB() && left.isTR() && left.left().isTR()) {
        return new T<>(R, new T<>(B, left.left().left(), left.left().value(), left.left().right()), left.value(), new T<>(B, left.right(), value, right));
      }
      // balance (T B (T R a x (T R b y c)) z d) = (T R (T B a x b) y (T B c z d))
      if (color.isB() && left.isTR() && left.right().isTR()) {
        return new T<>(R, new T<>(B, left.left(), left.value(), left.right().left()), left.right().value(), new T<>(B, left.right().right(), value, right));
      }
      // balance (T B a x (T R (T R b y c) z d)) = (T R (T B a x b) y (T B c z d))
      if (color.isB() && right.isTR() && right.left().isTR()) {
        return new T<>(R, new T<>(B, left, value, right.left().left()), right.left().value(), new T<>(B, right.left().right(), right.value(), right.right()));
      }
      // balance (T B a x (T R b y (T R c z d))) = (T R (T B a x b) y (T B c z d))
      if (color.isB() && right.isTR() && right.right().isTR()) {
        return new T<>(R, new T<>(B, left, value, right.left()), right.value(), new T<>(B, right.right().left(), right.right().value(), right.right().right()));
      }
      // balance (T color a x b) = (T color a x b)
      return new T<>(color, left, value, right);
    }

    public boolean isR() {
      return this.color.isR();
    }

    public boolean isB() {
      return this.color.isB();
    }

    @Override
    protected boolean isBB() {
      return color.isBB();
    }

    @Override
    protected boolean isTB() {
      return this.color.isB() || this.color.isBB();
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
    public Tree<A> ins(A value) {
      return value.compareTo(this.value) < 0
          ? balance(this.color, this.left.ins(value), this.value, this.right)
          : value.compareTo(this.value) > 0
              ? balance(this.color, this.left, this.value, this.right.ins(value))
              : this;
    }

    @Override
    protected Tree<A> blacken(Tree<A> t) {
      return new T<>(B, t.left(), t.value(), t.right());
    }


    @Override
    public String toString() {
      return String.format("(T %s %s %s %s)", color, left, value, right);
    }
  }

  private static abstract class Color {
    abstract boolean isR();
    abstract boolean isB();
    abstract boolean isBB();
    abstract boolean isNB();
    abstract Color blacker();
    abstract Color redder();
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
    boolean isBB() {
      return false;
    }

    @Override
    boolean isNB() {
      return false;
    }

    @Override
    Color blacker() {
      return B;
    }

    @Override
    Color redder() {
      return NB;
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
    boolean isBB() {
      return false;
    }

    @Override
    boolean isNB() {
      return false;
    }

    @Override
    Color blacker() {
      return BB;
    }

    @Override
    Color redder() {
      return R;
    }

    @Override
    public String toString() {
      return "B";
    }
  }

  private static class DoubleBlack extends Color {
    @Override
    boolean isR() {
      return false;
    }

    @Override
    boolean isB() {
      return false;
    }

    @Override
    boolean isBB() {
      return true;
    }

    @Override
    boolean isNB() {
      return false;
    }

    @Override
    Color blacker() {
      throw new IllegalStateException("Can't make DoubleBlack blacker");
    }

    @Override
    Color redder() {
      return B;
    }

    @Override
    public String toString() {
      return "BB";
    }
  }

  private static class NegativeBlack extends Color {
    @Override
    boolean isR() {
      return false;
    }

    @Override
    boolean isB() {
      return false;
    }

    @Override
    boolean isBB() {
      return false;
    }

    @Override
    boolean isNB() {
      return true;
    }

    @Override
    Color blacker() {
      return R;
    }

    @Override
    Color redder() {
      throw new IllegalStateException("Can't make NegativeBlack redder");
    }

    @Override
    public String toString() {
      return "NB";
    }
  }

  @SuppressWarnings("unchecked")
  public static <A extends Comparable<A>> Tree<A> empty() {
    return E;
  }

  @SuppressWarnings("unchecked")
  private static <A extends Comparable<A>> Tree<A> e() {
    return E;
  }

  @SuppressWarnings("unchecked")
  private static <A extends Comparable<A>> Tree<A> ee() {
    return EE;
  }


  public static int log2nlz(int n) {
    return n == 0
        ? 0
        : 31 - Integer.numberOfLeadingZeros(n);
  }
}
