package com.fpinjava.lists.exercise05_16;

import static com.fpinjava.lists.exercise05_16.List.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class ListTest {

  @Test
  public void testFlatten() {
    assertEquals("[4, 5, 6, NIL]", List.flatten(list(list(), list(4, 5, 6))).toString());
    assertEquals("[1, 2, 3, NIL]", List.flatten(list(list(1, 2, 3), list())).toString());
    assertEquals("[1, 2, 3, 4, 5, 6, NIL]", List.flatten(list(list(1, 2, 3), list(4, 5, 6))).toString());
  }

}
