package com.fpinjava.laziness.exercise09_12;

import com.fpinjava.common.List;
import org.junit.Test;

import static org.junit.Assert.*;


public class StreamTest {

  private List<Integer> evaluated = List.list();

  private int evaluate(int n) {
    evaluated = List.cons(n, evaluated);
    return n;
  }

  private Stream<Integer> stream1 =
      Stream.cons(() -> evaluate(1),
          Stream.cons(() -> evaluate(2),
              Stream.cons(() -> evaluate(3),
                  Stream.cons(() -> evaluate(4),
                      Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));

  private Stream<Integer> stream2 =
      Stream.cons(() -> evaluate(6),
          Stream.cons(() -> evaluate(7),
              Stream.cons(() -> evaluate(8),
                  Stream.cons(() -> evaluate(9),
                      Stream.cons(() -> evaluate(10), Stream.<Integer>empty())))));

  @Test
  public void testAppend() {
    evaluated = List.list();
    assertEquals("[NIL]", evaluated.toString());
    Stream<Integer> result = stream1.append(() -> stream2);
    assertEquals("[1, NIL]", evaluated.toString());
    assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, NIL]", result.toList().toString());
  }

  @Test
  public void testAppendEmpty() {
    evaluated = List.list();
    assertEquals("[NIL]", evaluated.toString());
    Stream<Integer> result = stream1.append(Stream::empty);
    assertEquals("[1, NIL]", evaluated.toString());
    assertEquals("[1, 2, 3, 4, 5, NIL]", result.toList().toString());
  }

  @Test
  public void testAppendEmpty2() {
    evaluated = List.list();
    assertEquals("[NIL]", evaluated.toString());
    Stream<Integer> result = Stream.<Integer>empty().append(() -> stream2);
    assertEquals("[NIL]", evaluated.toString());
    assertEquals("[6, 7, 8, 9, 10, NIL]", result.toList().toString());
  }

  @Test
  public void testLongStreamFilter() {
    Stream<Integer> stream1 = Stream.from(0).takeWhile(x -> x < 250_000);
    Stream<Integer> stream2 = Stream.from(250_000).takeWhile(x -> x < 500_000);
    Stream<Integer> result = stream1.append(() -> stream2);
    assertEquals(500_000, result.toList().length());
  }
}
