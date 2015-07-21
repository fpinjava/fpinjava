package com.fpinjava.lists.exercise05_04;

import static com.fpinjava.lists.exercise05_04.List.list;
import static org.junit.Assert.*;

import org.junit.Test;

public class ListTest {

  @Test
  public void testDrop() {
    assertEquals("[NIL]", list().drop(3).toString());
    assertEquals("[NIL]", list(1, 2, 3).drop(3).toString());
    assertEquals("[NIL]", list(1, 2, 3).drop(4).toString());
    assertEquals("[3, NIL]", list(1, 2, 3).drop(2).toString());
  }

}
