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

}
