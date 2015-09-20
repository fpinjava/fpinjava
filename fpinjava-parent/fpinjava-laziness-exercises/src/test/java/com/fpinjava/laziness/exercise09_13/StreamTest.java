package com.fpinjava.laziness.exercise09_13;

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
  public void testMap() {
    evaluated = List.list();
    assertEquals("[NIL]", evaluated.toString());
    Stream<Integer> result = stream.flatMap(x -> Stream.from(x).take(x));
    assertEquals(Integer.valueOf(1), result.head());
    assertEquals(Integer.valueOf(2), result.tail().head());
    assertEquals(Integer.valueOf(3), result.tail().tail().head());
    assertEquals("[2, 1, NIL]", evaluated.toString());
    assertEquals("[1, 2, 3, 3, 4, 5, 4, 5, 6, 7, 5, 6, 7, 8, 9, NIL]", result.toList().toString());
  }

  @Test
  public void testMapEmpty() {
    assertEquals("[NIL]", Stream.<Integer>empty().flatMap(x -> Stream.from(x).take(x)).toList().toString());
  }

  @Test
  public void testLongStreamMap() {
    Stream<Integer> stream = Stream.from(1).takeWhile(x -> x < 50_000);
    Stream<Integer> result = stream.flatMap(x -> Stream.from(x).take(3));
    assertEquals(Integer.valueOf(1), result.head());
    assertEquals(Integer.valueOf(2), result.drop(3).head());
    assertEquals(Integer.valueOf(3), result.drop(6).head());
  }
}
