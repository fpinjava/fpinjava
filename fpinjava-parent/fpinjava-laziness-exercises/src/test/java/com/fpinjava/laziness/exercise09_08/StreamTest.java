package com.fpinjava.laziness.exercise09_08;

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
  public void testTakeWhile() {
    evaluated = List.list();
    assertEquals("[NIL]", evaluated.toString());
    Stream<Integer> result = stream.takeWhile(x -> x < 3);
    assertEquals("[1, NIL]", evaluated.toString());
    assertEquals("[1, 2, NIL]", result.toList().toString());
  }

  @Test
  public void testTakeWhileEmpty() {
    assertEquals("[NIL]", Stream.<Integer>empty().takeWhile(x -> x < 4).toList().toString());
  }

  @Test
  public void testLongTakeWhileViaFoldRightTrue() {
    Stream<Integer> stream1 = Stream.from(0);
    Stream<Integer> result = stream1.takeWhile(x -> x < 500_000).drop(200_000).take(5);
    assertEquals("[200000, 200001, 200002, 200003, 200004, NIL]", result.toList().toString());
  }

  @Test
  public void testLongTakeWhileViaFoldRightFalse() {
    Stream<Integer> stream1 = Stream.from(0);
    Stream<Integer> result = stream1.takeWhile(x -> x < 0).drop(200_000).take(5);
    assertEquals("[NIL]", result.toList().toString());
  }
}
