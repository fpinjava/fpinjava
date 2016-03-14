package com.fpinjava.laziness.exercise09_18;

import com.fpinjava.common.Result;
import com.fpinjava.common.Tuple;
import org.junit.Test;

import static org.junit.Assert.*;


public class StreamTest {

  @Test
  public void testUnfold() throws Exception {
    Stream<Integer> stream = Stream.unfold(new Tuple<>(0, 1),
        x -> Result.success(new Tuple<>(x._1, new Tuple<>(x._2, x._1 + x._2)))).take(25);
    assertEquals("[0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, NIL]", stream.toList().toString());

  }
}
