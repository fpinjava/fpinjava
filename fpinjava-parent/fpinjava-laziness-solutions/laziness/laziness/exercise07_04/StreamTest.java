package com.fpinjava.laziness.exercise07_04;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.laziness.exercise07_04.Stream;

public class StreamTest {

  /**
   * The following tests verify which elements are evaluated by the methods.
   * Only the necessary elements should be evaluated.
   */
  List<Integer> evaluated;

  @Test
  public void testForAll() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    assertFalse(stream.forAll(x -> x >= 3));
    assertEquals("[1, NIL]", evaluated.toString());
  }

  @Test
  public void testForAll2() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Function<Integer, Boolean> p = x -> x < 4;
    assertFalse(stream.forAll(p));
    assertEquals("[4, 3, 2, 1, NIL]", evaluated.toString());
  }

  @Test
  public void testExistsEmpty() {
    assertTrue(Stream.<Integer>cons().forAll(x -> x >= 3));
  }
  
  public int evaluate(int n) {
    evaluated = List.cons(n, evaluated);
    return n;
  }

}
