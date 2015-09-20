package com.fpinjava.laziness.exercise09_10;

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
    Stream<Integer> result = stream.map(x -> x * 3);
    assertEquals(Integer.valueOf(3), result.head());
    assertEquals(Integer.valueOf(6), result.tail().head());
    assertEquals(Integer.valueOf(9), result.tail().tail().head());
    assertEquals("[3, 2, 1, NIL]", evaluated.toString());
    assertEquals("[3, 6, 9, 12, 15, NIL]", result.toList().toString());
  }

  @Test
  public void testMapEmpty() {
    assertEquals("[NIL]", Stream.<Integer>empty().map(x -> x * 3).toList().toString());
  }

  @Test
  public void testLongMap() {
    Stream<Integer> stream1 = Stream.from(0);
    Stream<Integer> result = stream1.map(x -> x * 2).drop(200_000).take(5);
    assertEquals("[400000, 400002, 400004, 400006, 400008, NIL]", result.toList().toString());
  }

}
