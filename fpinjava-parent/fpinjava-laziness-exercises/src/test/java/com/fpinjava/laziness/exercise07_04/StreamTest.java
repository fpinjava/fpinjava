package com.fpinjava.laziness.exercise07_04;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.laziness.exercise07_04.Stream;

public class StreamTest {

  /**
   * The following tests verify that no elements are evaluated by the methods.
   * Only the elements that are in the streams when converting to list are
   * evaluated.
   */
  List<Integer> evaluated;

  @Test
  public void testExists() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    assertTrue(stream.exists(x -> x >= 3));
    assertEquals("[3, 2, 1, NIL]", evaluated.toString());
  }

  @Test
  public void testExistsViaFoldRight() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    assertTrue(stream.existsViaFoldRight(x -> x < 4));
    assertEquals("[3, 4, 5, NIL]", evaluated.toString());
  }

  @Test
  public void testFoldRight() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Function<Integer, Boolean> p = x -> x >= 3;
    assertTrue(stream.foldRight(() -> false, a -> b -> p.apply(a) || b.get()));
    /*
     * Remember foldRight handle the elements from right to left, so it first tries
     * 5 and immedialtely stop since the predicate is satisfied
     */
    assertEquals("[5, NIL]", evaluated.toString());
  }

  @Test
  public void testFoldRight2() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Function<Integer, Boolean> p = x -> x < 4;
    assertTrue(stream.foldRight(() -> false, a -> b -> p.apply(a) || b.get()));
    /*
     * Here, foldRight has to evaluate element 5, 4, 3 to find one satisfying
     * the predicate. As the test use cons to put the evaluated element into
     * a list, they appear in inverse order.
     */
    assertEquals("[3, 4, 5, NIL]", evaluated.toString());
  }

  @Test
  public void testFoldRightStackBased() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Function<Integer, Boolean> p = x -> x >= 3;
    assertTrue(stream.foldRightStackBased(() -> false, a -> b -> p.apply(a) || b.get()));
    assertEquals("[5, NIL]", evaluated.toString());
  }

  @Test
  public void testFoldRightStackBased2() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Function<Integer, Boolean> p = x -> x < 4;
    assertTrue(stream.foldRightStackBased(() -> false, a -> b -> p.apply(a) || b.get()));
    assertEquals("[3, 4, 5, NIL]", evaluated.toString());
  }

  @Test
  public void testExistsEmpty() {
    assertFalse(Stream.<Integer>cons().exists(x -> x >= 3));
  }
  
  public int evaluate(int n) {
    evaluated = List.cons(n, evaluated);
    return n;
  }

}
