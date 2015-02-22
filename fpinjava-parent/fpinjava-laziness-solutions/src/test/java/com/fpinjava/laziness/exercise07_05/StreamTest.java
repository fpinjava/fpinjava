package com.fpinjava.laziness.exercise07_05;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;
import com.fpinjava.laziness.exercise07_05.Stream;

public class StreamTest {

  /**
   * The following tests verify that no elements are evaluated by the methods.
   * Only the elements that are in the streams when converting to list are
   * evaluated.
   */
  List<Integer> evaluated;

  @Test
  public void testTakeWhileViaFoldRight() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty()))))).takeWhileViaFoldRight(x -> x < 3);
    assertEquals("[1, 2, NIL]", stream.toString());
    assertEquals("[3, 2, 1, NIL]", evaluated.toString());
  }

  @Test
  public void testTakeWhileViaFoldRightEmpty() {
    assertEquals("[NIL]", Stream.<Integer>cons().takeWhileViaFoldRight(x -> x < 4).toString());
  }
  
  public int evaluate(int n) {
    evaluated = List.cons(n, evaluated);
    return n;
  }

}
