package com.fpinjava.laziness.exercise09_01;

import com.fpinjava.common.List;
import org.junit.Test;

import static org.junit.Assert.*;


public class StreamTest {

  /**
   * The following tests verify that no elements are evaluated by the methods.
   * Only the elements that are in the streams when converting to list are
   * evaluated.
   */
  private List<Integer> evaluated = List.list();

  private int evaluate(int n) {
    evaluated = List.cons(n, evaluated);
    return n;
  }

  private Stream<Integer> stream =
      Stream.cons(() -> evaluate(1),
          Stream.cons(() -> evaluate(2),
              Stream.cons(() -> evaluate(3),
                  Stream.cons(() -> evaluate(4),
                      Stream.cons(() -> evaluate(5), Stream::<Integer>empty)))));

  @Test
  public void testHeadOption() throws Exception {
    evaluated = List.list();
    assertEquals("Success(1)", stream.headOption().toString());
    assertEquals("[1, NIL]", evaluated.toString());
  }

  @Test
  public void testHeadOptionEmpty() throws Exception {
    Stream<Integer> stream = Stream.empty();
    assertEquals("Empty()", stream.headOption().toString());
  }
}
