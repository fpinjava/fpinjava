package com.fpinjava.laziness.exercise07_04Old;

import static org.junit.Assert.*;

import org.junit.Test;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Supplier;
import com.fpinjava.laziness.exercise07_04Old.Stream;

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
    assertTrue(stream.existsViaFoldRight(x -> x >= 3));
    assertEquals("[3, 2, 1, NIL]", evaluated.toString());
  }

  @Test
  public void testExistsViaFoldRight2() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    assertTrue(stream.existsViaFoldRight(x -> x < 4));
    assertEquals("[1, NIL]", evaluated.toString());
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
    assertEquals("[3, 2, 1, NIL]", evaluated.toString());
    assertEquals(Integer.valueOf(15), stream.foldRight(() -> 0, a -> b -> a + b.get()));
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
    assertEquals("[1, NIL]", evaluated.toString());
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
    assertEquals("[3, 2, 1, NIL]", evaluated.toString());
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
    Function<Integer, Boolean> p = x -> x >= 3;
    assertTrue(stream.foldRightStackBased2(() -> false, a -> b -> p.apply(a) || b.get()));
    assertEquals("[3, 2, 1, NIL]", evaluated.toString());
  }

  @Test
  public void testFoldRight2StackBased() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Function<Integer, Boolean> p = x -> x < 4;
    assertTrue(stream.foldRightStackBased(() -> false, a -> b -> p.apply(a) || b.get()));
    assertEquals("[1, NIL]", evaluated.toString());
  }

  @Test
  public void testFoldLeft() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Function<Integer, Boolean> p = x -> x < 4;
    assertTrue(stream.foldLeft(() -> false, a -> b -> p.apply(b) || a.get()));
    assertEquals("[1, NIL]", evaluated.toString());
  }
  
  @Test
  public void testFoldLeftStackBased3() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Function<Integer, Boolean> p = x -> x < 4;
    assertTrue(stream.foldLeftStackBased(() -> false, a -> b -> p.apply(b) || a.get()));
    assertEquals("[1, NIL]", evaluated.toString());
  }
  
  @Test
  public void testFoldLeft0() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Function<Integer, Boolean> p = x -> x >= 3;
    assertTrue(stream.foldLeft(() -> false, a -> b -> p.apply(b) || a.get()));
    assertEquals("[1, NIL]", evaluated.toString());
  }

  @Test
  public void testFoldLeftStackBased0() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Function<Integer, Boolean> p = x -> x >= 3;
    assertTrue(stream.foldLeftStackBased2(() -> false, a -> b -> p.apply(b) || a.get()));
    assertEquals("[1, NIL]", evaluated.toString());
  }

  @Test
  public void testFoldLeft2() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Function<Supplier<Integer>, Function<Integer, Integer>> f = x -> y -> x.get() + y;
    assertEquals(Integer.valueOf(15), stream.foldLeft(() -> 0, f));
    assertEquals("[1, NIL]", evaluated.toString());
  }

  @Test
  public void testFoldLeftStackBased() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Function<Supplier<Integer>, Function<Integer, Integer>> f = x -> y -> x.get() + y;
    assertEquals(Integer.valueOf(15), stream.foldLeftStackBased(() -> 0, f));
    assertEquals("[1, 2, 3, 4, 5, NIL]", evaluated.toString());
  }

  @Test
  public void testFoldLeftStackBased2() {
    evaluated = List.list();
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    Function<Supplier<Integer>, Function<Integer, Integer>> f = x -> y -> x.get() + y;
    assertEquals(Integer.valueOf(15), stream.foldLeftStackBased2(() -> 0, f));
    assertEquals("[1, 2, 3, 4, 5, NIL]", evaluated.toString());
  }

  @Test
  public void testFoldLeft3() {
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    String z = "0";
    Function<Supplier<String>, Function<Integer, String>> f = x -> y -> addSI(x, y);
    assertEquals("(((((0 + 1) + 2) + 3) + 4) + 5)", stream.foldLeft(() -> z, f));
  }

  @Test
  public void testFoldLeft3StackBased() {
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    String z = "0";
    Function<Supplier<String>, Function<Integer, String>> f = x -> y -> addSI(x, y);
    assertEquals("(((((0 + 1) + 2) + 3) + 4) + 5)", stream.foldLeftStackBased(() -> z, f));
  }

  @Test
  public void testFoldLeft3StackBased2() {
    Stream<Integer> stream = 
        Stream.cons(() -> evaluate(1), 
        Stream.cons(() -> evaluate(2), 
        Stream.cons(() -> evaluate(3), 
        Stream.cons(() -> evaluate(4), 
        Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));
    String z = "0";
    Function<Supplier<String>, Function<Integer, String>> f = x -> y -> addSI(x, y);
    assertEquals("(((((0 + 1) + 2) + 3) + 4) + 5)", stream.foldLeftStackBased2(() -> z, f));
  }

  private static String addSI(Supplier<String> s, Integer i) {
    return "(" + s.get() + " + " + i + ")";
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
