package com.fpinjava.lists.exercise05_06;

import static com.fpinjava.lists.exercise05_06.List.list;
import static org.junit.Assert.*;

import org.junit.Test;


public class ListTest {

  @Test
  public void testReverse() {
    assertEquals("[NIL]", list().reverse().toString());
    assertEquals("[3, 2, 1, NIL]", list(1, 2, 3).reverse().toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testInitEmpty() {
    assertEquals("[NIL]", list().init().toString());
  }

  @Test
  public void testInit() {
    assertEquals("[1, 2, NIL]", list(1, 2, 3).init().toString());
  }

}
