package com.fpinjava.laziness.exercise09_07;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Supplier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class StreamTest {

  private List<Integer> evaluated = List.list();;

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

  private Supplier<String> id = () -> "0";

  private static String addIS(Integer i, Supplier<String> s) {
    return "(" + i + " + " + s.get() + ")";
  }

  @Test
  public void testFoldRight() {
    Function<Integer, Function<Supplier<String>, String>> f = x -> y -> addIS(x, y);
    assertEquals("(1 + (2 + (3 + (4 + (5 + 0)))))", stream.foldRight(id, f));
  }
}
