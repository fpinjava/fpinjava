package com.fpinjava.common;


/*
 * see http://www.cs.cmu.edu/~rwh/theses/okasaki.pdf
 * see http://matt.might.net/papers/germane2014deletion.pdf
 * see http://matt.might.net/articles/red-black-delete/
 */
public abstract class Tree<A extends Comparable<A>> {

  private static Tree E = new E();
  private static Tree EE = new EE();
  protected static Color R = new Red();
  protected static Color B = new Black();
  private static Color BB = new DoubleBlack();
  private static Color NB = new NegativeBlack();

  public abstract boolean member(A elt);
  public abstract A max();
  public abstract A min();
  protected abstract Tree<A> removeMax();
  public abstract List<Integer> pathLengths(int currentDepth, List<Integer> depths);
  public abstract List<List<Color>> pathColors(List<Color> currentColorList, List<List<Color>> paths);

  public List<Integer> pathLengths() {
    return pathLengths(0, List.list());
  }

  public List<List<Color>> pathColors() {
    return pathColors(List.list(), List.list());
  }

  public abstract Result<A> get(A elt);
  protected abstract Result<T<A>> getT(A elt);

  abstract boolean isE();

  abstract boolean isT();
  abstract boolean isB();
  abstract boolean isBB();
  abstract boolean isTB();
  abstract boolean isTR();

  abstract boolean isTNB();

  public abstract int size();
  public abstract int height();
  public abstract boolean isEmpty();

  public abstract <B> B foldLeft(B identity, Function<B, Function<A, B>> f, Function<B, Function<B, B>> g);
  public abstract <B> B foldRight(B identity, Function<A, Function<B, B>> f, Function<B, Function<B, B>> g);
  public abstract <B> B foldInOrder(B identity, Function<B, Function<A, Function<B, B>>> f);
  public abstract <B> B foldInReverseOrder(B identity, Function<B, Function<A, Function<B, B>>> f);
  public abstract <B> B foldPreOrder(B identity, Function<A, Function<B, Function<B, B>>> f);
  public abstract <B> B foldPostOrder(B identity, Function<B, Function<B, Function<A, B>>> f);

  abstract Tree<A> right();
  abstract Tree<A> left();
  abstract A value();
  abstract Color color();
  abstract Tree<A> redder();

  abstract Tree<A> ins(A value);

  public Tree<A> insert(A value) {
    return blacken(ins(value));
  }

  public Tree<A> delete(A value) {
    return blacken(del(value));
  }

  abstract Tree<A> del(A value);

  protected Tree<A> blacken(Tree<A> t) {
    return t.isEmpty()
        ? E
        : new T<>(B, t.left(), t.value(), t.right());
  }

  protected Tree<A> redden(Tree<A> t) {
    if (t.isEmpty()) throw new IllegalStateException("Empty trees may not be reddened");
    return new T<>(R, t.left(), t.value(), t.right());
  }

  private Function<List<A>, Function<List<A>, List<A>>> g = a -> b -> List.concat(a, b);
  private Function<List<A>, Function<A, List<A>>> f = l -> l::cons;

  public List<A> toList() {
    return foldLeft(List.list(), f, g);
  }

  private static abstract class Empty<A extends Comparable<A>> extends Tree<A> {

    @Override
    protected Tree<A> removeMax() {
      throw new IllegalStateException("removeMax called on Empty");
    }

    @Override
    public A max() {
      throw new IllegalStateException("max called on Empty");
    }

    @Override
    public A min() {
      throw new IllegalStateException("min called on Empty");
    }

    @Override
    public boolean member(A value) {
      return false;
    }

    @Override
    public List<Integer> pathLengths(int currentDepth, List<Integer> depths) {
      return List.list();
    }

    @Override
    public Result<A> get(A value) {
      return Result.empty();
    }

    @Override
    protected Result<T<A>> getT(A value) {
      return Result.empty();
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
    public <B> B foldLeft(B identity, Function<B, Function<A, B>> f, Function<B, Function<B, B>> g) {
      return identity;
    }

    @Override
    public <B> B foldRight(B identity, Function<A, Function<B, B>> f, Function<B, Function<B, B>> g) {
      return identity;
    }

    @Override
    public <B> B foldInOrder(B identity, Function<B, Function<A, Function<B, B>>> f) {
      return identity;
    }

    @Override
    public <B> B foldInReverseOrder(B identity, Function<B, Function<A, Function<B, B>>> f) {
      return identity;
    }

    @Override
    public <B> B foldPreOrder(B identity, Function<A, Function<B, Function<B, B>>> f) {
      return identity;
    }

    @Override
    public <B> B foldPostOrder(B identity, Function<B, Function<B, Function<A, B>>> f) {
      return identity;
    }

    @Override
    public Tree<A> right() {
      return e();
    }

    @Override
    public Tree<A> left() {
      return e();
    }

    @Override
    A value() {
      throw new IllegalStateException("value called on Empty");
    }

    @Override
    Tree<A> ins(A value) {
      return new T<>(R, empty(), value, empty());
    }

    @Override
    Tree<A> del(A a) {
      return e();
    }

    @Override
    boolean isT() {
      return false;
    }

    @Override
    boolean isTB() {
      return false;
    }

    @Override
    boolean isTR() {
      return false;
    }

    @Override
    public boolean isEmpty() {
      return true;
    }

    @Override
    boolean isTNB() {
      return false;
    }

    @Override
    public List<List<Color>> pathColors(List<Color> currentColorList, List<List<Color>> paths) {
      return paths.cons(currentColorList);
    }
  }

  private static class E<A extends Comparable<A>> extends Empty<A> {

    @Override
    public boolean isE() {
      return true;
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
    Color color() {
      return R;
    }

    @Override
    Tree<A> redder() {
      return this;
    }

    @Override
    public String toString() {
      return "E";
    }
  }

  private static class EE<A extends Comparable<A>> extends Empty<A> {

    @Override
    public boolean isE() {
      return true;
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
    Color color() {
      return BB;
    }

    @Override
    Tree<A> redder() {
      return e();
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
    private final int depth;

    private T(Color color, Tree<A> left, A value, Tree<A> right) {
      this.color = color;
      this.left = left;
      this.right = right;
      this.value = value;
      this.length = left.size() + 1 + right.size();
      this.depth = Math.max(left.height(), right.height()) + 1;
    }

    private Tree<A> balance(Color color, Tree<A> left, A value, Tree<A> right) {
      // balance B (T R (T R a x b) y c) z d = T R (T B a x b) y (T B c z d)
      if (color.isB() && left.isTR() && left.left().isTR()) {
        return new T<>(R, blacken(left.left()), left.value(), new T<>(B, left.right(), value, right));
      }
      // balance B (T R a x (T R b y c)) z d = T R (T B a x b) y (T B c z d)
      if (color.isB() && left.isTR() && left.right().isTR()) {
        return new T<>(R, new T<>(B, left.left(), left.value(), left.right().left()), left.right().value(), new T<>(B, left.right().right(), value, right));
      }
      // balance B a x (T R (T R b y c) z d) = T R (T B a x b) y (T B c z d)
      if (color.isB() && right.isTR() && right.left().isTR()) {
        return new T<>(R, new T<>(B, left, value, right.left().left()), right.left().value(), new T<>(B, right.left().right(), right.value(), right.right()));
      }
      // balance B a x (T R b y (T R c z d)) = T R (T B a x b) y (T B c z d)
      if (color.isB() && right.isTR() && right.right().isTR()) {
        return new T<>(R, new T<>(B, left, value, right.left()), right.value(), blacken(right.right()));
      }


      // balance BB (T R (T R a x b) y c) z d = T B (T B a x b) y (T B c z d)
      if (color.isBB() && left.isTR() && left.left().isTR()) {
        return new T<>(B, blacken(left.left()), left.value(), new T<>(B, left.right(), value, right));
      }
      // balance BB (T R a x (T R b y c)) z d = T B (T B a x b) y (T B c z d)
      if (color.isBB() && left.isTR() && left.right().isTR()) {
        return new T<>(B, new T<>(B, left.left(), left.value(), left.right().left()), left.right().value(), new T<>(B, left.right().right(), value, right));
      }
      // balance BB a x (T R (T R b y c) z d) = T B (T B a x b) y (T B c z d)
      if (color.isBB() && right.isTR() && right.left().isTR()) {
        return new T<>(B, new T<>(B, left, value, right.left().left()), right.left().value(), new T<>(B, right.left().right(), right.value(), right.right()));
      }
      // balance BB a x (T R b y (T R c z d)) = T B (T B a x b) y (T B c z d)
      if (color.isBB() && right.isTR() && right.right().isTR()) {
        return new T<>(B, new T<>(B, left, value, right.left()), right.value(), blacken(right.right()));
      }
      // balance BB a x (T NB (T B b y c) z d@(T B _ _ _)) = T B (T B a x b) y (balance B c z (redden d))
      if (color.isBB() && right.isTNB() && right.left().isTB() && right.right().isTB()) {
        return new T<>(B, new T<>(B, left, value, right.left().left()), right.left().value(), balance(B, right.left().right(), right.value(), redden(right.right())));
      }
      // balance BB (T NB a@(T B _ _ _) x (T B b y c)) z d = T B (balance B (redden a) x b) y (T B c z d)
      if (color.isBB() && left.isTNB() && left.left().isTB() && left.right().isTB()) {
        return new T<>(B, balance(B, redden(left.left()), left.value(), left.right().left()), left.right().value(), new T<>(B, left.right().right(), value, right));
      }

      // balance color a x b = T color a x b
      return new T<>(color, left, value, right);
    }

    private Tree<A> bubble(Color color, Tree<A> left, A value, Tree<A> right) {
      return left.isBB() || right.isBB()
          ? balance(color.blacker(), left.redder(), value, right.redder())
          : balance(color, left, value, right);
    }

    @Override
    public boolean member(A value) {
      return value.compareTo(this.value) < 0
          ? left.member(value)
          : value.compareTo(this.value) <= 0 || right.member(value);
    }

    @Override
    public A max() {
      return right.isEmpty()
          ? value
          : right.max();
    }

    @Override
    public A min() {
      return left.isEmpty()
          ? value
          : left.min();
    }

    @Override
    public List<Integer> pathLengths(int currentDepth, List<Integer> depths) {
      return right.isEmpty() && left.isEmpty()
          ? depths.cons(currentDepth)
          : List.concat(left.pathLengths(currentDepth + 1, depths), right.pathLengths(currentDepth + 1, depths));
    }

    @Override
    public List<List<Color>> pathColors(List<Color> currentColorList, List<List<Color>> paths) {
      return right.isEmpty() && left.isEmpty()
          ? paths.cons(currentColorList.cons(color))
          : List.concat(left.pathColors(currentColorList.cons(color), paths), right.pathColors(currentColorList.cons(color), paths));
    }

    @Override
    public Result<A> get(A value) {
      return value.compareTo(this.value) < 0
          ? left.get(value)
          : value.compareTo(this.value) > 0
              ? right.get(value)
              : Result.success(this.value);
    }

    @Override
    public Result<T<A>> getT(A value) {
      return value.compareTo(this.value) < 0
          ? left.getT(value)
          : value.compareTo(this.value) > 0
              ? right.getT(value)
              : Result.success(this);
    }

    @Override
    public boolean isB() {
      return this.color.isB();
    }

    @Override
    public boolean isBB() {
      return this.color.isBB();
    }

    @Override
    boolean isTB() {
      return this.color.isB() || this.color.isBB();
    }

    @Override
    boolean isTR() {
      return this.color.isR();
    }

    @Override
    boolean isTNB() {
      return this.color.isNB();
    }

    private Tree<A> remove() {
      if (isTR() && left.isEmpty() && right.isEmpty()) {
        return e();
      }
      if (isTB() && left.isEmpty() && right.isEmpty()) {
        return ee();
      }
      if (isTB() && left.isEmpty() && right.isTR()) {
        return new T<>(B, right.left(), right.value(), right.right());
      }
      if (isTB() && left.isTR() && right.isEmpty()) {
        return new T<>(B, left.left(), left.value(), left.right());
      }
      if (left.isEmpty()) {
        return bubble(right.color(), right.left(), right.value(), right.right());
      }
      return bubble(color, left.removeMax(), left.max(), right);
    }

    @Override
    protected Tree<A> removeMax() {
      if (right.isEmpty()) {
        return remove();
      }
      return bubble(color, left, value, right.removeMax());
    }

    @Override
    public boolean isE() {
      return false;
    }

    @Override
    boolean isT() {
      return true;
    }

    @Override
    public int size() {
      return length;
    }

    @Override
    public int height() {
      return depth;
    }

    @Override
    public boolean isEmpty() {
      return false;
    }

    @Override
    public <B> B foldLeft(B identity, Function<B, Function<A, B>> f, Function<B, Function<B, B>> g) {
      // Post order right:
      return g.apply(right.foldLeft(identity, f, g)).apply(f.apply(left.foldLeft(identity, f, g)).apply(this.value));
    }

    @Override
    public <B> B foldRight(B identity, Function<A, Function<B, B>> f, Function<B, Function<B, B>> g) {
      // Pre order left
      return g.apply(f.apply(this.value).apply(left.foldRight(identity, f, g))).apply(right.foldRight(identity, f, g));
    }

    @Override
    public <B> B foldInOrder(B identity, Function<B, Function<A, Function<B, B>>> f) {
      return f.apply(left.foldInOrder(identity, f)).apply(value).apply(right.foldInOrder(identity, f));
    }

    @Override
    public <B> B foldInReverseOrder(B identity, Function<B, Function<A, Function<B, B>>> f) {
      return f.apply(right.foldInReverseOrder(identity, f)).apply(value).apply(left.foldInReverseOrder(identity, f));
    }

    @Override
    public <B> B foldPreOrder(B identity, Function<A, Function<B, Function<B, B>>> f) {
      return f.apply(value).apply(left.foldPreOrder(identity, f)).apply(right.foldPreOrder(identity, f));
    }

    @Override
    public <B> B foldPostOrder(B identity, Function<B, Function<B, Function<A, B>>> f) {
      return f.apply(left.foldPostOrder(identity, f)).apply(right.foldPostOrder(identity, f)).apply(value);
    }

    @Override
    public Tree<A> right() {
      return right;
    }

    @Override
    public Tree<A> left() {
      return left;
    }

    @Override
    A value() {
      return value;
    }

    @Override
    Color color() {
      return color;
    }

    @Override
    Tree<A> redder() {
      return new T<>(color.redder(), left, value, right);
    }

    @Override
    Tree<A> ins(A value) {
      return value.compareTo(this.value) < 0
          ? balance(this.color, this.left.ins(value), this.value, this.right)
          : value.compareTo(this.value) > 0
              ? balance(this.color, this.left, this.value, this.right.ins(value))
              : new T<>(this.color, this.left, value, this.right);
    }

    @Override
    Tree<A> del(A value) {
      return value.compareTo(this.value) < 0
          ? bubble(this.color, this.left.del(value), this.value, this.right)
          : value.compareTo(this.value) > 0
              ? bubble(this.color, this.left, this.value, this.right.del(value))
              : remove();
    }

    @Override
    public String toString() {
      return String.format("(T %s %s %s %s)", color, left, value, right);
    }
  }

  protected static abstract class Color {
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

  public static <A extends Comparable<A>> String toString(Tree<A> t) {
    int tableHeight = t.height() + 1;
    int tableWidth = (int) Math.pow(2, t.height() + 1) - 1;
    String[][] table = new String[tableHeight][tableWidth];
    int hPosition = tableWidth / 2;
    int vPosition = t.height();
    String[][] result = makeTable(table, t, hPosition, vPosition);
    StringBuilder sb = new StringBuilder();
    for (int l = result.length; l > 0; l--) {
      for (int c = 0; c < result[0].length; c++) {
        sb.append(makeCell(result[l - 1][c]));
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  private static String makeCell(String s) {
    if (s == null) return "    ";
    switch (s.length()) {
      case 0:
        return "    ";
      case 1:
        return " " + s + "  ";
      case 2:
        return " " + s + " ";
      case 3:
        return s + " ";
      default:
        return s;
    }
  }

  private static <A extends Comparable<A>> String[][] makeTable(String[][] table, Tree<A> t, int hPosition, int vPosition) {
    if (t.isEmpty()) return table;
    int shift = (int) Math.pow(2, t.height() - 1);
    int lhPosition = hPosition - shift;
    int rhPosition = hPosition + shift;
    table[vPosition][hPosition] = t.color() + "" + t.value();
    String[][] t2 = makeTable(table, t.left(), lhPosition, vPosition - 1);
    return makeTable(t2, t.right(), rhPosition, vPosition - 1);
  }
}
