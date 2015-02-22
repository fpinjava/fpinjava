package com.fpinjava.laziness.exercise07_08;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;
import com.fpinjava.common.Option;
import com.fpinjava.laziness.exercise07_08.Stream;


public class StreamTest {
  /**
   * The following tests verify that no elements are evaluated by the methods.
   * Only the elements that are in the streams when converting to list are
   * evaluated.
   */
  List<Integer> evaluated;
  
  @Test
  public void testFind() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Option<Integer> option = stream.find(x -> x % 2 == 0);
    assertEquals("Some(2)", option.toString());
    assertEquals("[2, 1, NIL]", evaluated.toString());
  }
    
  public int evaluate(int n) {
    evaluated = List.cons(n, evaluated);
    return n;
  }


}
