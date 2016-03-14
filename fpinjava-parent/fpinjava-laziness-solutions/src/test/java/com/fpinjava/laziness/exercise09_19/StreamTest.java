package com.fpinjava.laziness.exercise09_19;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import com.fpinjava.common.Supplier;
import com.fpinjava.common.Tuple;
import org.junit.Test;

import static org.junit.Assert.*;


public class StreamTest {

  private List<Integer> evaluated;

  private int evaluate(int n) {
    evaluated = List.cons(n, evaluated);
    return n;
  }

  @Test
  public void testFilter() {
    evaluated = List.list();
    assertEquals("[NIL]", evaluated.toString());
    Stream<Integer> result = getStream(1, 5).filter(x -> x % 2 == 0);
    assertEquals("[2, 1, NIL]", evaluated.toString());
    assertEquals(Integer.valueOf(2), result.head()._1);
    assertEquals(Integer.valueOf(4), result.tail().head()._1);
    assertEquals("[4, 3, 2, 1, NIL]", evaluated.toString());
    assertEquals("[2, 4, NIL]", result.toList().toString());
  }

  @Test
  public void testFilter2() {
    evaluated = List.list();
    assertEquals("[NIL]", evaluated.toString());
    Stream<Integer> result = getStream(1, 6).filter_(x -> x % 2 == 0);
    assertEquals("[2, 1, NIL]", evaluated.toString());
    assertEquals("[2, 4, NIL]", result.toList().toString());
  }

  @Test
  public void testFilter3() {
    evaluated = List.list();
    assertEquals("[NIL]", evaluated.toString());
    Stream<Integer> result = getStream(1, 6).filter(x -> x % 2 != 0);
    assertEquals("[1, NIL]", evaluated.toString());
    assertEquals(Integer.valueOf(1), result.head()._1);
    assertEquals(Integer.valueOf(3), result.tail().head()._1);
    assertEquals("[3, 2, 1, NIL]", evaluated.toString());
    assertEquals("[1, 3, 5, NIL]", result.toList().toString());
  }

  @Test
  public void testFilter4() {
    evaluated = List.list();
    assertEquals("[NIL]", evaluated.toString());
    Stream<Integer> result = getStream(1, 6).filter_(x -> x % 2 != 0);
    assertEquals("[1, NIL]", evaluated.toString());
    assertEquals(Integer.valueOf(1), result.head()._1);
    assertEquals(Integer.valueOf(3), result.tail().head()._1);
    assertEquals("[3, 2, 1, NIL]", evaluated.toString());
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
    Stream<Integer> result = stream1.filter_(x -> x > 200_000).take(5);
    assertEquals("[200001, 200002, 200003, 200004, 200005, NIL]", result.toList().toString());
  }

  @Test
  public void testLongFilterEval1() {
    evaluated = List.list();
    Stream<Integer> result = getStream(1, 20).filter(x -> x > 10);
    assertEquals("[11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, NIL]", evaluated.toString());
    assertEquals("[11, 12, 13, 14, 15, 16, 17, 18, 19, NIL]", result.toList().toString());
  }

  @Test
  public void testLongFilterEval2() {
    evaluated = List.list();
    Stream<Integer> result = getStream(1, 20).filter_(x -> x > 10);
    assertEquals("[11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, NIL]", evaluated.toString());
    assertEquals("[11, 12, 13, 14, 15, 16, 17, 18, 19, NIL]", result.toList().toString());
  }

  private Stream<Integer> getStream(int start, int end) {
    return unfold(start - 1, (Integer e) -> {
      int val = evaluate(e + 1);
      return val < end ? Result.success(new Tuple<>(val, val)) : Result.<Tuple<Integer,Integer>>empty();
    });
  }

  public static <A, S> Stream<A> unfold(S z, Function<S, Result<Tuple<A, S>>> f) {
    return f.apply(z).map(x -> Stream.cons(() -> x._1, () -> unfold(x._2, f))).getOrElse(Stream.empty());
  }

   @Test
   public void testFrom() {
     evaluated = List.list();
     Stream<Integer> stream = from(() -> evaluate(1)).take(2);
     assertEquals("[NIL]", evaluated.toString());
     assertEquals("[1, 2, NIL]", stream.toList().toString());
   }

  public Stream<Integer> from(Supplier<Integer> n) {
    return Stream.cons(n, () -> from(() -> evaluate(n.get() + 1)));
  }

}
