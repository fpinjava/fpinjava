package com.fpinjava.laziness.exercise07_13;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.List;
import com.fpinjava.common.Option;
import com.fpinjava.common.Tuple;


public class StreamTest {
  
  /**
   * The following tests verify that no elements are evaluated by the methods.
   * Only the elements that are in the streams when converting to list are
   * evaluated.
   */
  List<Integer> evaluated;

  @Test
  public void testMapViaUnfold() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Stream<Integer> stream2 = stream.mapViaUnfold(x -> x * 2);
    assertEquals("[1, NIL]", evaluated.toString());
    assertEquals("[2, 4, 6, 8, 10, NIL]", stream2.toString());
  }
  
  @Test
  public void testMapViaUnfoldEmpty() {
    evaluated = List.list();
    Stream<Integer> stream = Stream.<Integer>empty();
    Stream<Integer> stream2 = stream.map(x -> x * 2);
    assertEquals("[NIL]", evaluated.toString());
    assertEquals("[NIL]", stream2.toString());
  }

  @Test
  public void testTakeViaUnfold() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty()))))).takeViaUnfold(3);
    assertEquals("[1, NIL]", evaluated.toString());
    assertEquals("[1, 2, 3, NIL]", stream.toString());
    assertEquals("[3, 2, 1, NIL]", evaluated.toString());
  }

  @Test
  public void testTakeViaUnfoldMore() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty()))))).takeViaUnfold(8);
    assertEquals("[1, NIL]", evaluated.toString());
    assertEquals("[1, 2, 3, 4, 5, NIL]", stream.toString());
    assertEquals("[5, 4, 3, 2, 1, NIL]", evaluated.toString());
  }

  @Test
  public void testTakeViaUnfoldEmpty() {
    assertEquals("[NIL]", Stream.cons().take(3).toString());
  }
  
  @Test
  public void testTakeWhileViaUnfold() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty()))))).takeWhileViaUnfold(x -> x < 4);
    assertEquals("[1, NIL]", evaluated.toString());
    assertEquals("[1, 2, 3, NIL]", stream.toString());
    assertEquals("[4, 3, 2, 1, NIL]", evaluated.toString());
  }

  @Test
  public void testTakeWhileViaUnfoldAll() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty()))))).takeWhileViaUnfold(x -> x < 8);
    assertEquals("[1, NIL]", evaluated.toString());
    assertEquals("[1, 2, 3, 4, 5, NIL]", stream.toString());
    assertEquals("[5, 4, 3, 2, 1, NIL]", evaluated.toString());
  }

  @Test
  public void testTakeWhileViaUnfoldNone() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty()))))).takeWhileViaUnfold(x -> x > 8);
    assertEquals("[1, NIL]", evaluated.toString());
    assertEquals("[NIL]", stream.toString());
    assertEquals("[1, NIL]", evaluated.toString());
  }

  @Test
  public void testTakeWhileViaUnfoldEmpty() {
    evaluated = List.list();
    Stream<Integer> stream = Stream.<Integer>empty().takeWhileViaUnfold(x -> x < 4);
    assertEquals("[NIL]", evaluated.toString());
    assertEquals("[NIL]", stream.toString());
    assertEquals("[NIL]", evaluated.toString());
  }

  @Test
  public void testZip() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Stream<Tuple<Integer, Integer>> stream2 = stream.zip(stream.map(x -> x + 1).take(4));
    assertEquals("[2, 1, NIL]", evaluated.toString());
    assertEquals("[(1,2), (2,3), (3,4), (4,5), NIL]", stream2.toString());
    assertEquals("[4, 3, 2, 1, NIL]", evaluated.toString());
  }

  @Test
  public void testZipWithViaUnfoldEmpty() {
    assertEquals("[NIL]", Stream.cons().take(3).toString());
  }
   
  @Test
  public void testZipAllWithShorter() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Stream<Tuple<Option<Integer>, Option<Integer>>> stream2 = stream.zipAll(stream.map(x -> x + 1).take(4));
    assertEquals("[2, 1, NIL]", evaluated.toString());
    assertEquals("[(Some(1),Some(2)), (Some(2),Some(3)), (Some(3),Some(4)), (Some(4),Some(5)), (Some(5),None), NIL]", stream2.toString());
    assertEquals("[5, 4, 3, 2, 1, NIL]", evaluated.toString());
  }

  @Test
  public void testZipAllWithEqual() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Stream<Tuple<Option<Integer>, Option<Integer>>> stream2 = stream.zipAll(stream.map(x -> x + 1));
    assertEquals("[2, 1, NIL]", evaluated.toString());
    assertEquals("[(Some(1),Some(2)), (Some(2),Some(3)), (Some(3),Some(4)), (Some(4),Some(5)), (Some(5),Some(6)), NIL]", stream2.toString());
    assertEquals("[5, 4, 3, 2, 1, NIL]", evaluated.toString());
  }

  @Test
  public void testZipAllWithLonger() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Stream<Tuple<Option<Integer>, Option<Integer>>> stream2 = stream.take(4).zipAll(stream.map(x -> x + 1));
    assertEquals("[2, 1, NIL]", evaluated.toString());
    assertEquals("[(Some(1),Some(2)), (Some(2),Some(3)), (Some(3),Some(4)), (Some(4),Some(5)), (None,Some(6)), NIL]", stream2.toString());
    assertEquals("[5, 4, 3, 2, 1, NIL]", evaluated.toString());
  }

  @Test
  public void testZipAllWithEmpty() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Stream<Tuple<Option<Integer>, Option<Integer>>> stream2 = stream.zipAll(Stream.cons());
    assertEquals("[1, NIL]", evaluated.toString());
    assertEquals("[(Some(1),None), (Some(2),None), (Some(3),None), (Some(4),None), (Some(5),None), NIL]", stream2.toString());
    assertEquals("[5, 4, 3, 2, 1, NIL]", evaluated.toString());
  }

  @Test
  public void testZipAllEmptyWith() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Stream<Tuple<Option<Integer>, Option<Integer>>> stream2 = Stream.<Integer>cons().zipAll(stream);
    assertEquals("[1, NIL]", evaluated.toString());
    assertEquals("[(None,Some(1)), (None,Some(2)), (None,Some(3)), (None,Some(4)), (None,Some(5)), NIL]", stream2.toString());
    assertEquals("[5, 4, 3, 2, 1, NIL]", evaluated.toString());
  }

  @Test
  public void testZipAllEmpty() {
    assertEquals("[NIL]", Stream.cons().zipAll(Stream.cons()).toString());
  }

  public int evaluate(int n) {
    evaluated = List.cons(n, evaluated);
    return n;
  }


}
