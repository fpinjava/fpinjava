package com.fpinjava.laziness.exercise07_02;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;
import com.fpinjava.laziness.exercise07_02.Stream;

public class StreamTest {

  /**
   * The following tests verify that no elements are evaluated by the methods.
   * Only the elements that are in the streams when converting to list are
   * evaluated.
   */
  List<Integer> evaluated;

  @Test
  public void testTake() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty()))))).take(3);
    assertEquals("[NIL]", evaluated.toString());
    assertEquals("[1, 2, 3, NIL]", stream.toString());
    assertEquals("[3, 2, 1, NIL]", evaluated.toString());
  }

  @Test
  public void testTakeMore() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty()))))).take(8);
    assertEquals("[NIL]", evaluated.toString());
    assertEquals("[1, 2, 3, 4, 5, NIL]", stream.toString());
    assertEquals("[5, 4, 3, 2, 1, NIL]", evaluated.toString());
  }

  @Test
  public void testTakeEmpty() {
    assertEquals("[NIL]", Stream.cons().take(3).toString());
  }

  @Test
  public void testDrop() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty()))))).drop(3);
    assertEquals("[NIL]", evaluated.toString());
    assertEquals("[4, 5, NIL]", stream.toString());
    assertEquals("[5, 4, NIL]", evaluated.toString());
  }

  @Test(expected=IllegalStateException.class)
  public void testDropEmpty() {
    Stream.cons().drop(3).toString();
  }
  
  public int evaluate(int n) {
    evaluated = List.cons(n, evaluated);
    return n;
  }

}
