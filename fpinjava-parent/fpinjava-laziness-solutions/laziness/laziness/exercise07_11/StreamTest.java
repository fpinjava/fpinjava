package com.fpinjava.laziness.exercise07_11;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.Option;
import com.fpinjava.common.Tuple;


public class StreamTest {

  @Test
  public void testUnfold() {
    Stream<Integer> stream = Stream.unfold(2, x -> Option.some(new Tuple<>(x, x + 3)));
    assertEquals("[2, 5, 8, 11, 14, 17, NIL]", stream.take(6).toString());
  }

}
