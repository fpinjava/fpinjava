package com.fpinjava.laziness.exercise09_03;

import com.fpinjava.common.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


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
  public void testTake() {
    evaluated = List.list();
    assertEquals("[NIL]", evaluated.toString());
    Stream<Integer> result = stream.take(3);
    assertEquals("[NIL]", evaluated.toString());
    assertEquals("[1, 2, 3, NIL]", result.toList().toString());
  }

  @Test
  public void testTakeMore() {
    evaluated = List.list();
    assertEquals("[NIL]", evaluated.toString());
    Stream<Integer> result = stream.take(3);
    assertEquals("[NIL]", evaluated.toString());
    assertEquals("[1, 2, 3, NIL]", result.toList().toString());
  }

  @Test
  public void testTakeEmpty() {
    assertEquals("[NIL]", Stream.empty().take(3).toList().toString());
  }

  @Test
  public void testDrop() {
    evaluated = List.list();
    assertEquals("[NIL]", evaluated.toString());
    Stream<Integer> result = stream.drop(3);
    assertEquals("[NIL]", evaluated.toString());
    assertEquals("[4, 5, NIL]", result.toList().toString());
  }

  @Test
  public void testDropEmpty() {
    assertEquals("[NIL]", Stream.empty().drop(3).toList().toString());
  }

  @Test
  public void testTakeFromLongStream() {
    Stream<Integer> stream = Stream.from(0);
    Stream<Integer> result = stream.take(100_000);
    List<Integer> list = result.toList();
    assertEquals(100_000, list.length());
    assertTrue(list.toString().startsWith("[0, 1, 2, 3, 4, 5"));
  }

  @Test
  public void testDropFromLongStream() {
    Stream<Integer> stream = Stream.from(0);
    Stream<Integer> result = stream.drop(100_000);
    assertEquals(Integer.valueOf(100_000), result.head());
  }
}
