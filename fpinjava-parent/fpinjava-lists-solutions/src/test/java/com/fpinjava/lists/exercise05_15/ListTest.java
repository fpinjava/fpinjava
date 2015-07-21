package com.fpinjava.lists.exercise05_15;

import org.junit.Test;

import static com.fpinjava.lists.exercise05_15.List.list;
import static org.junit.Assert.assertEquals;

public class ListTest {

  @Test
  public void testConcat() {
    assertEquals("[4, 5, 6, NIL]", List.concat(list(), list(4, 5, 6)).toString());
    assertEquals("[1, 2, 3, NIL]", List.concat(list(1, 2, 3), list()).toString());
    assertEquals("[1, 2, 3, 4, 5, 6, NIL]", List.concat(list(1, 2, 3), list(4, 5, 6)).toString());
  }

}
