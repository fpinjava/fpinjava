package com.fpinjava.laziness.exercise09_18;

import com.fpinjava.common.List;
import org.junit.Test;

import static org.junit.Assert.*;


public class StreamTest {

  private List<Integer> evaluated;

  private int evaluate(int n) {
    evaluated = List.cons(n, evaluated);
    return n;
  }

  private Stream<Integer> stream =
      Stream.cons(() -> evaluate(1),
          Stream.cons(() -> evaluate(2),
              Stream.cons(() -> evaluate(3),
                  Stream.cons(() -> evaluate(4),
                      Stream.cons(() -> evaluate(5), Stream.<Integer>empty())))));

  @Test
  public void testFilter() {
    evaluated = List.list();
    assertEquals("[NIL]", evaluated.toString());
    Stream<Integer> result = stream.filter(x -> x % 2 == 0);
    assertEquals("[2, 2, 1, NIL]", evaluated.toString());
    assertEquals(Integer.valueOf(2), result.head()._1);
    assertEquals(Integer.valueOf(4), result.tail().head()._1);
    assertEquals("[4, 4, 3, 2, 2, 1, NIL]", evaluated.toString());
    assertEquals("[2, 4, NIL]", result.toList().toString());
  }

  @Test
  public void testFilter2() {
    evaluated = List.list();
    assertEquals("[NIL]", evaluated.toString());
    Stream<Integer> result = stream.filter2(x -> x % 2 == 0);
    assertEquals("[2, 1, NIL]", evaluated.toString());
    assertEquals(Integer.valueOf(2), result.head()._1);
    assertEquals(Integer.valueOf(4), result.tail().head()._1);
    assertEquals("[4, 4, 3, 2, 2, 1, NIL]", evaluated.toString());
    assertEquals("[2, 4, NIL]", result.toList().toString());
  }

  @Test
  public void testFilter3() {
    evaluated = List.list();
    assertEquals("[NIL]", evaluated.toString());
    Stream<Integer> result = stream.filter(x -> x % 2 != 0);
    assertEquals("[1, 1, NIL]", evaluated.toString());
    assertEquals(Integer.valueOf(1), result.head()._1);
    assertEquals(Integer.valueOf(3), result.tail().head()._1);
    assertEquals("[3, 3, 2, 1, 1, NIL]", evaluated.toString());
    assertEquals("[1, 3, 5, NIL]", result.toList().toString());
  }

  @Test
  public void testFilter4() {
    evaluated = List.list();
    assertEquals("[NIL]", evaluated.toString());
    Stream<Integer> result = stream.filter2(x -> x % 2 != 0);
    assertEquals("[1, NIL]", evaluated.toString());
    assertEquals(Integer.valueOf(1), result.head()._1);
    assertEquals(Integer.valueOf(3), result.tail().head()._1);
    assertEquals("[3, 3, 2, 1, 1, NIL]", evaluated.toString());
    assertEquals("[1, 3, 5, NIL]", result.toList().toString());
  }

  @Test
  public void testFilterEmpty() {
    assertEquals("[NIL]", Stream.<Integer>empty().filter(x -> x % 2 != 0).toList().toString());
  }

  @Test
  public void testLongStreamFilter() {
    Stream<Integer> stream = Stream.from(1).takeWhile(x -> x < 500_000);
    int min = 2000;
    Stream<Integer> result = stream.filter(x -> x > min);
    assertEquals(Integer.valueOf(min + 1), result.head()._1);
    assertEquals(Integer.valueOf(min + 2), result.tail().head()._1);
    assertEquals(Integer.valueOf(min + 3), result.tail().tail().head()._1);
  }

  @Test
  public void testLongFilter() {
    Stream<Integer> stream1 = Stream.from(0);
    Stream<Integer> result = stream1.filter(x -> x > 200_000).take(5);
    assertEquals("[200001, 200002, 200003, 200004, 200005, NIL]", result.toList().toString());
  }

  @Test
  public void testLongFilter2() {
    Stream<Integer> stream1 = Stream.from(0);
    Stream<Integer> result = stream1.filter2(x -> x > 200_000).take(5);
    assertEquals("[200001, 200002, 200003, 200004, 200005, NIL]", result.toList().toString());
  }
}
