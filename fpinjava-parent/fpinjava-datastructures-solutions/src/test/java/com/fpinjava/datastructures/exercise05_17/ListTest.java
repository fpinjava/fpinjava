package com.fpinjava.datastructures.exercise05_17;

import org.junit.Test;

import static com.fpinjava.datastructures.exercise05_17.List.list;
import static org.junit.Assert.assertEquals;

public class ListTest {

  @Test
  public void testConcat() {
    assertEquals("[4, 5, 6, NIL]", List.concat(list(), list(4, 5, 6)).toString());
    assertEquals("[1, 2, 3, NIL]", List.concat(list(1, 2, 3), list()).toString());
    assertEquals("[1, 2, 3, 4, 5, 6, NIL]", List.concat(list(1, 2, 3), list(4, 5, 6)).toString());
  }

}
