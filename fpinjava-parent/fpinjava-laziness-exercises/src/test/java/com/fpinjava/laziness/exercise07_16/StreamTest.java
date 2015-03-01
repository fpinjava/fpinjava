package com.fpinjava.laziness.exercise07_16;

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
  public void testScanRight() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    assertEquals("[NIL]", evaluated.toString());
    assertEquals("[15, 14, 12, 9, 5, 0, NIL]", stream.scanRight(0, x -> y -> () -> x + y.get()).toString());
    assertEquals("[5, 4, 3, 2, 1, NIL]", evaluated.toString());
  }

  public int evaluate(int n) {
    evaluated = List.cons(n, evaluated);
    return n;
  }

}
