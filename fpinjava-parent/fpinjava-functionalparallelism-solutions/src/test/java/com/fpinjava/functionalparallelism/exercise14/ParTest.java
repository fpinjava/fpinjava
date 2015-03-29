package com.fpinjava.functionalparallelism.exercise14;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;

public class ParTest {

  @Test
  public void testChoiceViaFlatMap() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 2;
    Par<Boolean> pb = Par.unit(() -> x % 2 == 0);
    Par<List<Integer>> p1 = Par.parMap(List.range(1, 10), f2);
    Par<List<Integer>> p2 = Par.parMap(List.range(1, 10), f3);
    Par<List<Integer>> pl = choiceViaFlatMapViaJoin(pb, p1, p2);
    try {
      List<Integer> result = Par.run(es,  pl).get();
      assertEquals("[1, 4, 9, 16, 25, 36, 49, 64, 81, NIL]", result.toString());
    } catch (InterruptedException | ExecutionException e) {
      fail();
    }
  }

  @Test
  public void testChoiceViaFlatMap2() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 3;
    Par<Boolean> pb = Par.unit(() -> x % 2 == 0);
    Par<List<Integer>> p1 = Par.parMap(List.range(1, 10), f2);
    Par<List<Integer>> p2 = Par.parMap(List.range(1, 10), f3);
    Par<List<Integer>> pl = choiceViaFlatMapViaJoin(pb, p1, p2);
    try {
      List<Integer> result = Par.run(es,  pl).get();
      assertEquals("[1, 8, 27, 64, 125, 216, 343, 512, 729, NIL]", result.toString());
    } catch (InterruptedException | ExecutionException e) {
      fail();
    }
  }

  @Test
  public void testChoiceViaFlatMap3() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 3;
    Par<Boolean> pb = Par.unit(() -> x % 2 == 0);
    Par<List<Integer>> p1 = Par.parMap(List.range(1, 10), f2);
    Par<List<Integer>> p2 = Par.parMap(List.range(1, 10), g);
    Par<List<Integer>> pl = choiceViaFlatMapViaJoin(pb, p1, p2);
    try {
      List<Integer> result = Par.run(es,  pl).get();
      assertEquals("[1, 8, 27, 64, 125, 216, 343, 512, 729, NIL]", result.toString());
    } catch (InterruptedException | ExecutionException e) {
      assertEquals("java.lang.IllegalStateException: x == 5", e.getMessage());
    }
  }

  @Test
  public void testChoiceNViaFlatMap1() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 0;
    Par<Integer> pb = Par.unit(() -> x);
    Par<List<Integer>> p1 = Par.parMap(List.range(1, 10), f2);
    Par<List<Integer>> p2 = Par.parMap(List.range(1, 10), f3);
    Par<List<Integer>> p3 = Par.parMap(List.range(1, 10), f4);
    Par<List<Integer>> pl = choiceNViaFlatMapViaJoin(pb, List.list(p1, p2, p3));
    try {
      List<Integer> result = Par.run(es,  pl).get();
      assertEquals("[1, 4, 9, 16, 25, 36, 49, 64, 81, NIL]", result.toString());
    } catch (InterruptedException | ExecutionException e) {
      fail();
    }
  }

  @Test
  public void testChoiceNViaFlatMap2() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 1;
    Par<Integer> pb = Par.unit(() -> x);
    Par<List<Integer>> p1 = Par.parMap(List.range(1, 10), f2);
    Par<List<Integer>> p2 = Par.parMap(List.range(1, 10), f3);
    Par<List<Integer>> p3 = Par.parMap(List.range(1, 10), f4);
    Par<List<Integer>> pl = choiceNViaFlatMapViaJoin(pb, List.list(p1, p2, p3));
    try {
      List<Integer> result = Par.run(es,  pl).get();
      assertEquals("[1, 8, 27, 64, 125, 216, 343, 512, 729, NIL]", result.toString());
    } catch (InterruptedException | ExecutionException e) {
      fail();
    }
  }

  @Test
  public void testChoiceNViaFlatMap3() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 2;
    Par<Integer> pb = Par.unit(() -> x);
    Par<List<Integer>> p1 = Par.parMap(List.range(1, 10), f2);
    Par<List<Integer>> p2 = Par.parMap(List.range(1, 10), f3);
    Par<List<Integer>> p3 = Par.parMap(List.range(1, 10), f4);
    Par<List<Integer>> pl = choiceNViaFlatMapViaJoin(pb, List.list(p1, p2, p3));
    try {
      List<Integer> result = Par.run(es,  pl).get();
      assertEquals("[1, 16, 81, 256, 625, 1296, 2401, 4096, 6561, NIL]", result.toString());
    } catch (InterruptedException | ExecutionException e) {
      fail();
    }
  }

  @Test(expected=IllegalStateException.class)
  public void testChoiceNViaFlatMap4() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 3;
    Par<Integer> pb = Par.unit(() -> x);
    Par<List<Integer>> p1 = Par.parMap(List.range(1, 10), f2);
    Par<List<Integer>> p2 = Par.parMap(List.range(1, 10), f3);
    Par<List<Integer>> p3 = Par.parMap(List.range(1, 10), f4);
    Par<List<Integer>> pl = choiceNViaFlatMapViaJoin(pb, List.list(p1, p2, p3));
    try {
      List<Integer> result = Par.run(es,  pl).get();
      assertEquals("[1, 16, 81, 256, 625, 1296, 2401, 4096, 6561, NIL]", result.toString());
    } catch (InterruptedException | ExecutionException e) {
      fail();
    }
  }

  @Test
  public void testChoiceNViaFlatMap5() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 0;
    Par<Integer> pb = Par.unit(() -> x);
    Par<List<Integer>> p1 = Par.parMap(List.range(1, 10), g);
    Par<List<Integer>> p2 = Par.parMap(List.range(1, 10), f3);
    Par<List<Integer>> p3 = Par.parMap(List.range(1, 10), f4);
    Par<List<Integer>> pl = choiceNViaFlatMapViaJoin(pb, List.list(p1, p2, p3));
    try {
      List<Integer> result = Par.run(es,  pl).get();
      assertEquals("[1, 16, 81, 256, 625, 1296, 2401, 4096, 6561, NIL]", result.toString());
    } catch (InterruptedException | ExecutionException e) {
      assertEquals("java.lang.IllegalStateException: x == 5", e.getMessage());
    }
  }

  private static Function<Integer, Integer> f2 = x -> {
    return x * x;
  };

  private static Function<Integer, Integer> f3 = x -> {
    return x * x * x;
  };

  private static Function<Integer, Integer> f4 = x -> {
    return x * x * x * x;
  };

  private static Function<Integer, Integer> g = x -> {
    if (x == 5) {
      throw new IllegalStateException("x == 5");
    }
    return x * x;
  };

  public static <A> Par<A> choiceViaFlatMapViaJoin(Par<Boolean> p, Par<A> t, Par<A> f) {
    return Par.flatMapViaJoin(p, b -> b ? t : f);
  }

  public static <A> Par<A> choiceNViaFlatMapViaJoin(Par<Integer> p, List<Par<A>> choices) {
    return Par.flatMapViaJoin(p, i -> choices.getAt(i).get());
  }

}
