package com.fpinjava.laziness.exercise09_02;

import com.fpinjava.common.List;
import org.junit.Test;

import static org.junit.Assert.*;


public class StreamTest {

  private List<Integer> evaluated = List.list();

  private int evaluate(int n) {
    evaluated = List.cons(n, evaluated);
    return n;
  }

  @Test
  public void testToList() {
    Stream<Integer> stream =
        Stream.cons(() -> evaluate(1),
            Stream.cons(() -> evaluate(2),
                Stream.cons(() -> evaluate(3),
                    Stream.cons(() -> evaluate(4),
                        Stream.cons(() -> evaluate(5), Stream::<Integer>empty)))));
    assertEquals("[NIL]", evaluated.toString());
    assertEquals("[1, 2, 3, 4, 5, NIL]", stream.toList().toString());
  }

  @Test
  public void testToListEmpty() {
    assertEquals("[NIL]", Stream.empty().toList().toString());
  }
}
