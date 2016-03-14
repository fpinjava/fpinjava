package com.fpinjava.laziness.exercise09_11;

import com.fpinjava.common.List;
import org.junit.Test;

import static org.junit.Assert.*;


public class StreamTest {

  private List<Integer> evaluated;

  private int evaluate(int n) {
    evaluated = List.cons(n, evaluated);
    return n;
  }

  private Stream<Integer> stream =
      Stream.cons(() -> evaluate(1),
          Stream.cons(() -> evaluate(2),
              Stream.cons(() -> evaluate(3),
                  Stream.cons(() -> evaluate(4),
                      Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));

  @Test
  public void testFilter() {
    evaluated = List.list();
    assertEquals("[NIL]", evaluated.toString());
    Stream<Integer> result = stream.filter(x -> x % 2 == 0);
    assertEquals("[2, 1, NIL]", evaluated.toString());
    assertEquals(Integer.valueOf(2), result.head());
    assertEquals(Integer.valueOf(4), result.tail().head());
    assertEquals("[4, 3, 2, 1, NIL]", evaluated.toString());
    assertEquals("[2, 4, NIL]", result.toList().toString());
  }

  @Test
  public void testFilter2() {
    evaluated = List.list();
    assertEquals("[NIL]", evaluated.toString());
    Stream<Integer> result = stream.filter(x -> x % 2 != 0);
    assertEquals("[1, NIL]", evaluated.toString());
    assertEquals(Integer.valueOf(1), result.head());
    assertEquals(Integer.valueOf(3), result.tail().head());
    assertEquals("[3, 2, 1, NIL]", evaluated.toString());
    assertEquals("[1, 3, 5, NIL]", result.toList().toString());
  }

  @Test
  public void testFilterEmpty() {
    assertEquals("[NIL]", Stream.<Integer>empty().filter(x -> x % 2 != 0).toList().toString());
  }

  @Test
  public void testLongStreamFilter() {
    Stream<Integer> stream = Stream.from(1).takeWhile(x -> x < 500_000);
    Stream<Integer> result = stream.filter(x -> x % 2 == 0);
    assertEquals(Integer.valueOf(2), result.head());
    assertEquals(Integer.valueOf(4), result.tail().head());
    assertEquals(Integer.valueOf(6), result.tail().tail().head());
  }
}
