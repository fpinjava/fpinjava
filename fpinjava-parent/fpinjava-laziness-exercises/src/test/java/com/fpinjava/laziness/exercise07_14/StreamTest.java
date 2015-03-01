package com.fpinjava.laziness.exercise07_14;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;


public class StreamTest {

  /**
   * The following tests verify that no elements are evaluated by the methods.
   * Only the elements that are in the streams when converting to list are
   * evaluated.
   */
  List<Integer> evaluated;

  @Test
  public void testStartsWith() {
    evaluated = List.list();
    Stream<Integer> stream1 = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Stream<Integer> stream2 = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), Stream.<Integer>empty())));
    assertEquals("[NIL]", evaluated.toString());
    assertTrue(stream1.startsWith(stream2));
    assertFalse(stream2.startsWith(stream1));
    assertEquals("[4, 3, 3, 2, 2, 1, 1, NIL]", evaluated.toString());
  }

  public int evaluate(int n) {
    evaluated = List.cons(n, evaluated);
    return n;
  }
}
