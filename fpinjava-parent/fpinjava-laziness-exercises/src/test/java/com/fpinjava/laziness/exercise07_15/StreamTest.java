package com.fpinjava.laziness.exercise07_15;

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
  public void testTails() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    assertEquals("[NIL]", evaluated.toString());
    assertEquals("[[1, 2, 3, 4, 5, NIL], [2, 3, 4, 5, NIL], [3, 4, 5, NIL], [4, 5, NIL], [5, NIL], NIL]", stream.tails().toString());
    assertEquals("[5, 4, 3, 2, 1, NIL]", evaluated.toString());
  }

  @Test
  public void testHasSubsequenceTrue() {
    evaluated = List.list();
    Stream<Integer> stream1 = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Stream<Integer> stream2 = 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), Stream.<Integer>empty())));
    assertEquals("[NIL]", evaluated.toString());
    assertTrue(stream1.hasSubsequence(stream2));
    assertEquals("[5, 4, 4, 3, 3, 2, 2, 1, NIL]", evaluated.toString());
  }

  @Test
  public void testHasSubsequenceFalse() {
    evaluated = List.list();
    Stream<Integer> stream1 = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Stream<Integer> stream2 = 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), Stream.<Integer>empty())));
    assertEquals("[NIL]", evaluated.toString());
    assertFalse(stream2.hasSubsequence(stream1));
    assertEquals("[4, 3, 1, 2, NIL]", evaluated.toString());
  }

  @Test
  public void testHasSubsequenceFalse2() {
    evaluated = List.list();
    Stream<Integer> stream1 = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Stream<Integer> stream2 = 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())));
    assertEquals("[NIL]", evaluated.toString());
    assertFalse(stream1.hasSubsequence(stream2));
    assertEquals("[5, 4, 4, 3, 2, 2, 1, NIL]", evaluated.toString());
  }

  public int evaluate(int n) {
    evaluated = List.cons(n, evaluated);
    return n;
  }
}
