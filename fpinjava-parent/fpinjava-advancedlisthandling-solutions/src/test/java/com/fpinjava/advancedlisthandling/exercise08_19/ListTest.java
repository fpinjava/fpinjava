package com.fpinjava.advancedlisthandling.exercise08_19;

import com.fpinjava.common.Function;
import org.junit.Test;

import static org.junit.Assert.*;

public class ListTest {

  @Test
  public void testRangeEmpty() throws Exception {
    assertEquals("[NIL]", List.range(10, 0).toString());
  }

  @Test
  public void testRange() throws Exception {
    assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, NIL]", List.range(0, 10).toString());
  }

  @Test
  public void testHugeFlatMap() throws Exception {
    assertEquals(10000, List.range(0, 10000).foldLeft(List.list(), t -> h -> t.cons(h * 2)).length());
    assertEquals(20000, List.range(0, 10000).flatMap(x -> List.list(x, x)).length());
  }
}
