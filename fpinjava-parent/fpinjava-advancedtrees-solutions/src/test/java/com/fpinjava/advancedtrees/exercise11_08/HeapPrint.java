package com.fpinjava.advancedtrees.exercise11_08;


import java.util.Comparator;


public class HeapPrint {

  public static void main(String... args) {
    Comparator<Number> comparator = (n1, n2) -> n1.value < n2.value ? -1 : n1.value > n2.value ? 1 : 0;
    Heap<Number> heap = Heap.empty(comparator)
        .add(number(1))
        .add(number(2))
        .add(number(2))
        .add(number(2))
        .add(number(6))
        .add(number(7))
        .add(number(5))
        .add(number(0))
        .add(number(5))
        .add(number(1));
    System.out.println(toString(heap));

    Heap<Integer> heap2 = Heap.<Integer>empty()
        .add(1)
        .add(2)
        .add(2)
        .add(2)
        .add(6)
        .add(7)
        .add(5)
        .add(0)
        .add(5)
        .add(1);
    System.out.println(toString(heap2));
  }

  private static Number number(int num) {
    return new Number(num);
  }

  private static class Number {
    public final int value;

    private Number(int value) {
      this.value = value;
    }

    public String toString() {
      return Integer.toString(value);
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
