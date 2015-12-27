package com.fpinjava.advancedtrees.exercise11_09;

import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import com.fpinjava.common.Tuple;
import com.fpinjava.state.SimpleRNG;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;


public class HeapTest {

  static int testLimit = 20_000;

  static final List<Integer> randomTestList = SimpleRNG.doubles(testLimit, new SimpleRNG.Simple(3))._1.map(x -> (int) (x * 30));
  static final List<Integer> orderedTestList = List.range(0, testLimit);

  static final Heap<Integer> orderedHeap = orderedTestList.foldLeft(Heap.empty(), h -> h::insert);
  static final Heap<Integer> randomHeap = randomTestList.foldLeft(Heap.empty(), h -> h::insert);

  private static <T> boolean isBalanced(Heap<T> heap) {
    return rightSpine(heap) <= log2nlz(heap.length() + 1);
  }

  private static <T> int rightSpine(Heap<T> heap) {
    return 1 + rightSpine_(heap.right());
  }
  private static <T> int rightSpine_(Result<Heap<T>> heap) {
    return heap.map(t -> t.isEmpty() ? -1 : 1 + rightSpine_(t.right())).getOrElse(-1);
  }

  private static <T> boolean isValueOrdered(Heap<T> heap) {
    return isValueOrdered_(heap) && heap.tail().map(HeapTest::isValueOrdered_).getOrElse(true);
  }

  private static <T> boolean isValueOrdered_(Heap<T> heap) {
    return heap.head().flatMap(t1 -> heap.tail().flatMap(tail -> tail.head().flatMap(t2 -> heap.comparator().map(comp -> comp.compare(t1, t2) <= 0)))).getOrElse(true);
  }

  @Test
  public void testInsertOrderedAscending7() {
    int limit = 7;
    List<Integer> list = List.range(1, limit + 1);
    Heap<Integer> heap = list.foldLeft(Heap.<Integer>empty(), h -> h::insert);
    assertEquals(limit, heap.length());
    assertTrue(isBalanced(heap));
    assertTrue(isValueOrdered(heap));
  }

  @Test
  public void testInsertOrderedDescending7() {
    int limit = 7;
    List<Integer> list = List.iterate(limit, x -> x - 1, limit);
    Heap<Integer> heap = list.foldLeft(Heap.<Integer>empty(), h -> h::insert);
    assertEquals(list.length(), heap.length());
    assertTrue(isBalanced(heap));
    assertTrue(isValueOrdered(heap));
  }

  @Test
  public void testInsertRandom10() {
    List<Integer> list = List.list(2, 5, 2, 7, 3, 1, 6, 1, 4, 1);
    Heap<Integer> heap = list.foldLeft(Heap.<Integer>empty(), h -> h::insert);
    assertEquals(list.length(), heap.length());
    assertTrue(isBalanced(heap));
    assertTrue(isValueOrdered(heap));
  }

  @Test
  public void testInsertOrderedAscending() {
    int limit = 20_000;
    List<Integer> list = List.range(1, limit + 1);
    Heap<Integer> heap = list.foldLeft(Heap.<Integer>empty(), h -> h::insert);
    assertEquals(limit, heap.length());
    assertTrue(isBalanced(heap));
    assertTrue(isValueOrdered(heap));
    Heap<Integer> heap2 = list.foldLeft(heap, t -> e -> t.tail().getOrElse(t));
    assertTrue(heap2.isEmpty());
  }

  @Test
  public void testInsertOrderedDescending() {
    int limit = 20_000;
    List<Integer> list = List.iterate(limit, x -> x - 1, limit);
    Heap<Integer> heap = list.foldLeft(Heap.<Integer>empty(), h -> h::insert);
    assertEquals(limit, heap.length());
    assertTrue(isBalanced(heap));
    assertTrue(isValueOrdered(heap));
    Heap<Integer> heap2 = list.foldLeft(heap, t -> e -> t.tail().getOrElse(t));
    assertTrue(heap2.isEmpty());
  }

  @Test
  public void testInsertRandom() {
    int limit = 20_000;
    List<Integer> list = SimpleRNG.doubles(limit, new SimpleRNG.Simple(3))._1.map(x -> (int) (x * limit * 3));
    Heap<Integer> heap = list.foldLeft(Heap.<Integer>empty(), h -> h::insert);
    assertEquals(limit, heap.length());
    assertTrue(isBalanced(heap));
    assertTrue(isValueOrdered(heap));
    Heap<Integer> heap2 = list.foldLeft(heap, t -> e -> t.tail().getOrElse(t));
    assertTrue(heap2.isEmpty());
  }

  /**
   * Test whether the test data is correct
   */
  @Test
  public void testAddOrdered() {
    assertEquals(orderedTestList.length(), orderedHeap.length());
    assertTrue(isBalanced(orderedHeap));
    assertTrue(isValueOrdered(orderedHeap));
  }

  /**
   * Test whether the test data is correct
   */
  @Test
  public void testAddRandom() {
    assertTrue(isBalanced(randomHeap));
    assertTrue(isValueOrdered(randomHeap));
  }

  /**
   * After removal of all inserted values, the map should be empty;
   */
  @Test
  public void testRemoveAllOrdered() {
    assertTrue(orderedTestList.foldLeft(orderedHeap, t -> e -> t.tail().getOrElse(t)).isEmpty());
  }

  @Test
  public void testRemoveAllRandom() {
    assertTrue(randomTestList.foldLeft(randomHeap, t -> e -> t.tail().getOrElse(t)).isEmpty());
  }

  @Test
  public void testRemoveHalfRandom() {
    assertTrue(randomTestList.foldLeft(randomHeap, t -> e -> t.tail().getOrElse(t)).isEmpty());
  }

  /**
   * Test for 100 random number list computed from a random seed whether inserting 'testLimit' entries
   * and then removing them results in an empty tree.
   */
  @Test
  public void testRemoveAllRandomX10() {
    for (int j = 0; j < 10; j++) {
      long seed = System.nanoTime();
      List<Integer> list = SimpleRNG.doubles(10_000, new SimpleRNG.Simple(seed))._1.map(x -> (int) (x * 30));
      Heap<Integer> heap = list.foldLeft(Heap.<Integer>empty(), h -> h::insert);
      Heap<Integer> heap2 = list.foldLeft(heap, t -> e -> t.tail().getOrElse(t));
      assertTrue(heap2.isEmpty());
    }
  }

  @Test
  public void testRemoveAllRandomX10comparator() {
    Comparator<Number> comparator = (n1, n2) -> n1.value < n2.value ? -1 : n1.value > n2.value ? 1 : 0;
    for (int j = 0; j < 10; j++) {
      long seed = System.nanoTime();
      List<Integer> list = SimpleRNG.doubles(10_000, new SimpleRNG.Simple(seed))._1.map(x -> (int) (x * 30));
      Heap<Number> heap = list.foldLeft(Heap.empty(comparator), h -> i -> h.insert(new Number(i)));
      Heap<Number> heap2 = list.foldLeft(heap, t -> e -> t.tail().getOrElse(t));
      assertTrue(heap2.isEmpty());
    }
  }

  @Test
  public void testInsertRandomNumberComparator() {
    Comparator<Number> comparator = (n1, n2) -> n1.value < n2.value ? -1 : n1.value > n2.value ? 1 : 0;
    int limit = 20_000;
    List<Integer> list = SimpleRNG.doubles(limit, new SimpleRNG.Simple(3))._1.map(x -> (int) (x * limit * 3));
    Heap<Number> heap = list.foldLeft(Heap.empty(comparator), h -> i -> h.insert(new Number(i)));
    assertEquals(limit, heap.length());
    assertTrue(isBalanced(heap));
    assertTrue(isValueOrdered(heap));
    Heap<Number> heap2 = list.foldLeft(heap, t -> e -> t.tail().getOrElse(t));
    assertTrue(heap2.isEmpty());
  }

  @Test
  public void testInsertRandomPoint() {
    int limit = 20_000;
    SimpleRNG.doubles(limit, new SimpleRNG.Simple(3))._1.map(x -> (int) (x * limit * 3)).zipWithPositionResult().forEachOrThrow(points -> {
      Heap<Point> heap = points.foldLeft(Heap.empty(), h -> t -> h.insert(new Point(t._1, t._2)));
      assertEquals(limit, heap.length());
      assertTrue(isBalanced(heap));
      assertTrue(isValueOrdered(heap));
      Heap<Point> heap2 = List.range(0, points.length()).foldLeft(heap, t -> e -> t.tail().getOrElse(t));
      assertTrue(heap2.isEmpty());
    });
  }

  public static int log2nlz(int n) {
    return n == 0
        ? 0
        : 31 - Integer.numberOfLeadingZeros(n);
  }

  class Number {
    public final int value;

    private Number(int value) {
      this.value = value;
    }

    public String toString() {
      return Integer.toString(value);
    }
  }

  class Point implements Comparable<Point> {
    public final int x;
    public final int y;

    private Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public String toString() {
      return "(" + x + "," + y + ")";
    }

    @Override
    public int compareTo(Point that) {
      return this.x < that.x ? -1 : this.x > that.x ? 1 : 0;
    }
  }


  public static <A> String toString(Heap<A> t) {
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
    if (s == null) return "  ";
    switch (s.length()) {
      case 0:
        return "  ";
      case 1:
        return " " + s;
      default:
        return s;
    }
  }

  private static <A> String[][] makeTable(String[][] table, Heap<A> t, int hPosition, int vPosition) {
    if (t.isEmpty()) return table;
    int shift = (int) Math.pow(2, t.height() - 1);
    int lhPosition = hPosition - shift;
    int rhPosition = hPosition + shift;
    table[vPosition][hPosition] = t.head().map(Object::toString).getOrElse("A") + "(" + t.rank() + ")";
    String[][] t2 = makeTable(table, t.left().successValue(), lhPosition, vPosition - 1);
    return makeTable(t2, t.right().successValue(), rhPosition, vPosition - 1);
  }
}
