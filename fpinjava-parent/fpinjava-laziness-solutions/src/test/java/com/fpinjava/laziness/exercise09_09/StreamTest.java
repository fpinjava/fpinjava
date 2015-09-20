package com.fpinjava.laziness.exercise09_09;

import com.fpinjava.common.List;
import org.junit.Test;

import static org.junit.Assert.*;


public class StreamTest {

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
    assertEquals("[NIL]", evaluated.toString());
    assertEquals("Success(1)", stream.headOptionViaFoldRight().toString());
    assertEquals("[1, NIL]", evaluated.toString());
  }

  @Test
  public void testHeadOptionEmpty() throws Exception {
    Stream<Integer> stream = Stream.empty();
    assertEquals("Empty()", stream.headOptionViaFoldRight().toString());
  }

  @Test
  public void testLongStreamFalse() {
    Stream<Integer> stream = Stream.from(0).take(500_000);
    assertEquals("Success(0)", stream.headOptionViaFoldRight().toString());
  }
}
