package com.fpinjava.laziness.exercise09_14;

import com.fpinjava.common.List;
import com.fpinjava.common.Result;
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
  public void testFindTrue() {
    evaluated = List.list();
    assertEquals("[NIL]", evaluated.toString());
    Result<Integer> result = stream.find(x -> x > 3);
    assertEquals("[4, 3, 2, 1, NIL]", evaluated.toString());
    assertEquals("Success(4)", result.toString());
  }

  @Test
  public void testFindFalse() {
    evaluated = List.list();
    assertEquals("[NIL]", evaluated.toString());
    Result<Integer> result = stream.find(x -> x < 0);
    assertEquals("[5, 4, 3, 2, 1, NIL]", evaluated.toString());
    assertEquals("Empty()", result.toString());
  }

  @Test
  public void testFilterEmpty() {
    assertEquals("Empty()", Stream.<Integer>empty().find(x -> x > 3).toString());
  }

  @Test
  public void testLongStreamFilter() {
    Stream<Integer> stream = Stream.from(1).takeWhile(x -> x < 500_000);
    Result<Integer> result = stream.find(x -> x % 17 == 0 && x > 100);
    assertEquals("Success(102)", result.toString());
  }
}
