package com.fpinjava.laziness.exercise07_12;

import static org.junit.Assert.*;

import org.junit.Test;


public class StreamTest {

  @Test
  public void testOnes() {
    Stream<Integer> stream = Stream.onesViaUnfold;
    Stream<Integer> stream2 = stream.take(5).map(x -> x * 2);
    Stream<Integer> stream3 = stream.map(x -> x * 2).take(5);
    assertEquals("[2, 2, 2, 2, 2, NIL]", stream2.toString());
    assertEquals("[2, 2, 2, 2, 2, NIL]", stream3.toString());
    assertEquals("[1, 1, 1, 1, 1, NIL]", Stream.onesViaUnfold.take(5).toString());
  }

  @Test
  public void testConstantViaUnfold() {
    Stream<Integer> stream = Stream.constantViaUnfold(5);
    Stream<Integer> stream2 = stream.take(5).map(x -> x * 2);
    Stream<Integer> stream3 = stream.map(x -> x + 2).take(5);
    assertEquals("[10, 10, 10, 10, 10, NIL]", stream2.toString());
    assertEquals("[7, 7, 7, 7, 7, NIL]", stream3.toString());
    assertEquals("[5, 5, 5, 5, 5, NIL]", Stream.constantViaUnfold(5).take(5).toString());
  }

  @Test
  public void testFromViaUnfold() {
    Stream<Integer> stream = Stream.fromViaUnfold(5);
    Stream<Integer> stream2 = stream.take(5).map(x -> x * 2);
    Stream<Integer> stream3 = stream.map(x -> x + 2).take(5);
    assertEquals("[10, 12, 14, 16, 18, NIL]", stream2.toString());
    assertEquals("[7, 8, 9, 10, 11, NIL]", stream3.toString());
    assertEquals("[5, 6, 7, 8, 9, NIL]", Stream.fromViaUnfold(5).take(5).toString());
  }

  @Test
  public void testFibsViaUnfold() {
    assertEquals("[0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, NIL]", Stream.fibsViaUnfold().take(20).toString());
  }

}
