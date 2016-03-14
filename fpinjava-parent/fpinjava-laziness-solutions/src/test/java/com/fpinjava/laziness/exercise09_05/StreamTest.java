package com.fpinjava.laziness.exercise09_05;

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
  public void testDropWhile() {
    evaluated = List.list();
    assertEquals("[NIL]", evaluated.toString());
    Stream<Integer> result = stream.dropWhile(x -> x < 3);
    assertEquals("[3, 2, 1, NIL]", evaluated.toString());
    assertEquals("[3, 4, 5, NIL]", result.toList().toString());
  }

  @Test
  public void testDropWhileEmpty() {
    assertEquals("[NIL]", Stream.<Integer>empty().dropWhile(x -> x < 4).toList().toString());
  }

  @Test
  public void testLongDropWhileTrue() {
    Stream<Integer> stream1 = Stream.from(0);
    Stream<Integer> result = stream1.dropWhile(x -> x < 200_000).take(5);
    assertEquals("[200000, 200001, 200002, 200003, 200004, NIL]", result.toList().toString());
  }

  @Test
  public void testLongDropWhileFalse() {
    Stream<Integer> stream1 = Stream.from(0);
    Stream<Integer> result = stream1.dropWhile(x -> x < 0).take(5);
    assertEquals("[0, 1, 2, 3, 4, NIL]", result.toList().toString());
  }

}
