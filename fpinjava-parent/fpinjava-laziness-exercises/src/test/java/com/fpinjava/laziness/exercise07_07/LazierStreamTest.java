package com.fpinjava.laziness.exercise07_07;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;
import com.fpinjava.laziness.exercise07_07.LazierStream.LazyVal;


public class LazierStreamTest {

  /**
   * The following tests verify that no elements are evaluated by the methods.
   * Only the elements that are in the streams when converting to list are
   * evaluated.
   */
  List<Integer> evaluated;

  @Test
  public void testMap() {
    evaluated = List.list();
    LazierStream<Integer> stream = 
    LazierStream.cons(() -> evaluate(1), 
    LazierStream.cons(() -> evaluate(2), 
    LazierStream.cons(() -> evaluate(3), 
    LazierStream.cons(() -> evaluate(4), 
    LazierStream.cons(() -> evaluate(5), LazierStream.<Integer>empty())))));
    LazierStream<Integer> stream2 = stream.map(x -> x * 2);
    assertEquals("[NIL]", evaluated.toString());
    assertEquals("[2, 4, 6, 8, 10, NIL]", stream2.toString());
  }
  
  @Test
  public void testMapEmpty() {
    evaluated = List.list();
    LazierStream<Integer> stream = LazierStream.<Integer>empty();
    LazierStream<Integer> stream2 = stream.map(x -> x * 2);
    assertEquals("[NIL]", evaluated.toString());
    assertEquals("[NIL]", stream2.toString());
  }
 
  /*
   * The filter method does not cause evaluation of the first element.
   */
  @Test
  public void testFilter() {
    evaluated = List.list();
    LazierStream<Integer> stream = 
    LazierStream.cons(() -> evaluate(1), 
    LazierStream.cons(() -> evaluate(2), 
    LazierStream.cons(() -> evaluate(3), 
    LazierStream.cons(() -> evaluate(4), 
    LazierStream.cons(() -> evaluate(5), LazierStream.<Integer>empty())))));
    LazierStream<LazyVal<Integer>> stream2 = stream.filter(x -> x % 2 != 0);
    assertEquals("[NIL]", evaluated.toString());
    assertEquals("[1, 3, 5, NIL]", LazierStream.flatten(stream2).toString());
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
    Stream<Integer> stream2 = stream.append(stream.filter(x -> x % 2 != 0));
    assertEquals("[1, NIL]", evaluated.toString());
    assertEquals("[1, 2, 3, 4, 5, 1, 3, 5, NIL]", stream2.toString());
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
