package com.fpinjava.recursion.exercise04_03;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.fpinjava.common.Function;
import static com.fpinjava.common.CollectionUtilities.*;

public class FoldLeftTest {

  private static String addSI(String s, Integer i) {
    return "(" + s + " + " + i + ")";
  }
  
  @Test
  public void testFoldLeft() {
    List<Integer> list = list(1, 2, 3, 4, 5);
    String identity = "0";
    Function<String, Function<Integer, String>> f = x -> y -> addSI(x, y);
    assertEquals("(((((0 + 1) + 2) + 3) + 4) + 5)", FoldLeft.foldLeft(list, identity, f));
  }

}
