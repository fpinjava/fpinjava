package com.fpinjava.laziness.exercise07_07;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;


public class StreamTest {

  /**
   * The following tests verify that no elements are evaluated by the methods.
   * Only the elements that are in the streams when converting to list are
   * evaluated.
   */
  List<Integer> evaluated;
    
  @Test
  public void testMap() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Stream<Integer> stream2 = stream.map(x -> x * 2);
    assertEquals("[1, NIL]", evaluated.toString());
    assertEquals("[2, 4, 6, 8, 10, NIL]", stream2.toString());
  }
    
  @Test
  public void testMapEmpty() {
    evaluated = List.list();
    Stream<Integer> stream = Stream.<Integer>empty();
    Stream<Integer> stream2 = stream.map(x -> x * 2);
    assertEquals("[NIL]", evaluated.toString());
    assertEquals("[NIL]", stream2.toString());
  }
  
  @Test
  public void testFilter() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Stream<Integer> stream2 = stream.filter(x -> x % 2 != 0);
    assertEquals("[1, NIL]", evaluated.toString());
    assertEquals("[1, 3, 5, NIL]", stream2.toString());
  }
  
  @Test
  public void testAppend() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Stream<Integer> stream2 = 
        Stream.cons(() -> evaluate(6), 
        Stream.cons(() -> evaluate(7), 
        Stream.cons(() -> evaluate(8), 
        Stream.cons(() -> evaluate(9), 
        Stream.cons(() -> evaluate(10), Stream.<Integer>empty())))));
    Stream<Integer> stream3 = stream.append(stream2);
    assertEquals("[1, NIL]", evaluated.toString());
    assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, NIL]", stream3.toString());
  }
  
  @Test
  public void testFlatMap() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Stream<Integer> stream2 = stream.flatMap(x -> Stream.cons(() -> x, Stream.cons(() -> x * 2, Stream.<Integer>empty())));
    assertEquals("[5, 4, 3, 2, 1, NIL]", evaluated.toString());
    assertEquals("[1, 2, 2, 4, 3, 6, 4, 8, 5, 10, NIL]", stream2.toString());
  }
  
  public int evaluate(int n) {
    evaluated = List.cons(n, evaluated);
    return n;
  }

}
