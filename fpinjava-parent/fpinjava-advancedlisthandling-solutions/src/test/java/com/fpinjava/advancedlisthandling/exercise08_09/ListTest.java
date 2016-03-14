package com.fpinjava.advancedlisthandling.exercise08_09;

import com.fpinjava.common.Tuple;
import org.junit.Test;

import static org.junit.Assert.*;


public class ListTest {

  @Test
  public void testProduct() throws Exception {
    assertEquals("[12, 13, 14, 22, 23, 24, 32, 33, 34, NIL]", List.product(List.list("1", "2", "3"), List.list("2", "3", "4"), x -> y -> x + y).toString());
    assertEquals("[12, 13, 14, 22, 23, 24, 32, 33, 34, 42, 43, 44, NIL]", List.product(List.list("1", "2", "3", "4"), List.list("2", "3", "4"), x -> y -> x + y).toString());
    assertEquals("[12, 13, 14, 15, 22, 23, 24, 25, 32, 33, 34, 35, NIL]", List.product(List.list("1", "2", "3"), List.list("2", "3", "4", "5"), x -> y -> x + y).toString());
  }

  @Test
  public void testProductEmpty() throws Exception {
    assertEquals("[NIL]", List.product(List.list("1", "2", "3"), List.list(), x -> y -> x + y).toString());
    assertEquals("[NIL]", List.product(List.list(), List.list("2", "3", "4"), x -> y -> x + y).toString());
  }

  @Test
  public void testProductTuple() throws Exception {
    assertEquals("[(1,4), (1,5), (1,6), (2,4), (2,5), (2,6), (3,4), (3,5), (3,6), NIL]", List.product(List.list(1, 2, 3), List.list(4, 5, 6), x -> y -> new Tuple<>(x, y)).toString());
    assertEquals("[(1,4), (2,5), (3,6), NIL]", List.zipWith(List.list(1, 2, 3), List.list(4, 5, 6), x -> y -> new Tuple<>(x, y)).toString());
  }
}
