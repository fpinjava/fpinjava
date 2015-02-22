package com.fpinjava.laziness.exercise07_06;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;
import com.fpinjava.laziness.exercise07_06.Stream;

public class StreamTest {

  /**
   * The following tests verify which elements are evaluated by the methods.
   */
  List<Integer> evaluated;

  @Test
  public void testHeadOptionViaFoldRight() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    assertEquals("Some(1)", stream.headOptionViaFoldRight().toString());
    assertEquals("[1, NIL]", evaluated.toString());
  }

  @Test
  public void testHeadOptionViaFoldRightEmpty() {
    assertEquals("None", Stream.<Integer>cons().headOptionViaFoldRight().toString());
  }
  
  public int evaluate(int n) {
    evaluated = List.cons(n, evaluated);
    return n;
  }

}
