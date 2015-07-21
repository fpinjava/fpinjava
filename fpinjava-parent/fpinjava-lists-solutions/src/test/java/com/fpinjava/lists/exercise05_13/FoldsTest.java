package com.fpinjava.lists.exercise05_13;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.Function;
import com.fpinjava.lists.exercise05_10.List;
import static com.fpinjava.lists.exercise05_10.List.*;
import static com.fpinjava.lists.exercise05_13.Folds.*;

public class FoldsTest {

  private static String addIS(Integer i, String s) {
    return "(" + i + " + " + s + ")";
  }

  private static String addSI(String s, Integer i) {
    return "(" + s + " + " + i + ")";
  }

  @Test
  public void testFoldRightViaFoldLeft() {
    List<Integer> list = list(1, 2, 3, 4, 5);
    String identity = "0";
    Function<Integer, Function<String, String>> f = x -> y -> addIS(x, y);
    assertEquals("(1 + (2 + (3 + (4 + (5 + 0)))))", foldRightViaFoldLeft(list, identity, f));
  }

  @Test
  public void testFoldLeftViaFoldRight() {
    List<Integer> list = list(1, 2, 3, 4, 5);
    String identity = "0";
    Function<String, Function<Integer, String>> f = x -> y -> addSI(x, y);
    assertEquals("(((((0 + 1) + 2) + 3) + 4) + 5)", foldLeftViaFoldRight(list, identity, f));
  }

  @Test
  public void testFoldRightViaFoldLeft2() {
    List<Integer> list = list(1, 2, 3, 4, 5);
    String identity = "0";
    Function<Integer, Function<String, String>> f = x -> y -> addIS(x, y);
    assertEquals("(1 + (2 + (3 + (4 + (5 + 0)))))", foldRightViaFoldLeft2(list, identity, f));
  }

  @Test
  public void testFoldLeftViaFoldRight2() {
    List<Integer> list = list(1, 2, 3, 4, 5);
    String identity = "0";
    Function<String, Function<Integer, String>> f = x -> y -> addSI(x, y);
    assertEquals("(((((0 + 1) + 2) + 3) + 4) + 5)", foldLeftViaFoldRight2(list, identity, f));
  }

}
