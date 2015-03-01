package com.fpinjava.laziness.exercise07_08;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;
import com.fpinjava.common.Option;
import com.fpinjava.laziness.exercise07_08.Stream;

public class StreamTest {

  /**
   * The following tests verify that no elements are evaluated by the methods.
   * Only the elements that are in the streams when converting to list are
   * evaluated.
   */
  List<Integer> evaluated;

  @Test
  public void testFind() {
    evaluated = List.list();
    Stream<Integer> stream =
        Stream.cons(() -> evaluate(1),
        Stream.cons(() -> evaluate(2),
        Stream.cons(() -> evaluate(3),
        Stream.cons(() -> evaluate(4),
        Stream.cons(() -> evaluate(5), Stream.<Integer> empty())))));
    Option<Integer> option = stream.find(x -> x % 2 == 0);
    assertEquals("Some(2)", option.toString());
    assertEquals("[2, 1, NIL]", evaluated.toString());
  }

  @Test
  public void testOnes() {
    Stream<Integer> stream = Stream.ones;
    Stream<Integer> stream2 = stream.take(5).map(x -> x * 2);
    Stream<Integer> stream3 = stream.map(x -> x * 2).take(5);
    assertEquals("[2, 2, 2, 2, 2, NIL]", stream2.toString());
    assertEquals("[2, 2, 2, 2, 2, NIL]", stream3.toString());
    assertEquals("[1, 1, 1, 1, 1, NIL]", Stream.ones.take(5).toString());
  }

  @Test
  public void testConstant() {
    Stream<Integer> stream = Stream.constant(5);
    Stream<Integer> stream2 = stream.take(5).map(x -> x * 2);
    Stream<Integer> stream3 = stream.map(x -> x + 2).take(5);
    assertEquals("[10, 10, 10, 10, 10, NIL]", stream2.toString());
    assertEquals("[7, 7, 7, 7, 7, NIL]", stream3.toString());
    assertEquals("[5, 5, 5, 5, 5, NIL]", Stream.constant(5).take(5).toString());
  }

  public int evaluate(int n) {
    evaluated = List.cons(n, evaluated);
    return n;
  }

}
