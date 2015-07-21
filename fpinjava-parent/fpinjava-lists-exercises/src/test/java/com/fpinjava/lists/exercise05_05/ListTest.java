package com.fpinjava.lists.exercise05_05;

import static com.fpinjava.lists.exercise05_05.List.list;
import static org.junit.Assert.*;

import org.junit.Test;


public class ListTest {

  @Test
  public void testDropWhile() {
    // For the empty list, we have to annotate the method list() to help Java type inference
    assertEquals("[NIL]", List.<Integer>list().dropWhile(x -> x < 3).toString());
    assertEquals("[1, 2, 3, NIL]", list(1, 2, 3).dropWhile(x -> x > 3).toString());
    assertEquals("[3, NIL]", list(1, 2, 3).dropWhile(x -> x < 3).toString());
    assertEquals("[NIL]", list(1, 2, 3).dropWhile(x -> x < 5).toString());
  }

}
