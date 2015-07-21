package com.fpinjava.lists.exercise05_14;

import static com.fpinjava.lists.exercise05_14.List.list;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fpinjava.common.Function;

public class ListTest {

  private static String addIS(Integer i, String s) {
    return "(" + i + " + " + s + ")";
  }

  @Test
  public void testFoldRight() {
    List<Integer> list = list(1, 2, 3, 4, 5);
    String identity = "0";
    Function<Integer, Function<String, String>> f = x -> y -> addIS(x, y);
    assertEquals("(1 + (2 + (3 + (4 + (5 + 0)))))", list.foldRight(identity, f));
  }

}
