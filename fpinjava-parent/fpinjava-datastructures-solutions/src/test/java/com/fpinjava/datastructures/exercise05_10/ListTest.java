package com.fpinjava.datastructures.exercise05_10;

import static org.junit.Assert.*;

import org.junit.Test;

import static com.fpinjava.datastructures.exercise05_10.List.list;
import static com.fpinjava.datastructures.exercise05_10.List.foldRight;

public class ListTest {

  @Test
  public void testFoldRight() {
    assertEquals("[1, 2, 3, NIL]", foldRight(list(1, 2, 3), list(), x -> y -> y.cons(x)).toString());
  }

  /*
   * A program to print a trace of what is happening
   */
  public static void main(String... args) {
    List<Integer> list = list(1, 2, 3);
    print(list, list());
    assertEquals("[1, 2, 3, NIL]", foldRight(list, list(), x -> y -> trace(x, y)).toString());
  }

  private static List<Integer> list = list(1, 2, 3);
  
  private static List<Object> trace(Integer x, List<Object> y) {
    List<Object> result = y.cons(x);
    list = list.init();
    print(list, result);
    return result;
  }
  
  private static void print(List<?> list1, List<?> list2) {
    System.out.println("foldRight(" + toString(list1) + ", " + toString(list2) + ", x -> y -> y.cons(x)");
  }

  private static String toString(List<?> list) {
    String result = List.foldRight(list, "", x -> y -> x + ", " + y);
    return "list(" + (result.length() >= 2 ? result.substring(0, (result.length() - 2)) : result) + ")";
  }
  
}
