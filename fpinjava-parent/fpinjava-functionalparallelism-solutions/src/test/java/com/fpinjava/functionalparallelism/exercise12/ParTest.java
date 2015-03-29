package com.fpinjava.functionalparallelism.exercise12;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Map;

public class ParTest {

  @Test
  public void testChoiceMap() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 1;
    Par<Integer> pb = Par.unit(() -> x);
    Par<List<Integer>> p1 = Par.parMap(List.range(1, 10), f2);
    Par<List<Integer>> p2 = Par.parMap(List.range(1, 10), f3);
    Par<List<Integer>> p3 = Par.parMap(List.range(1, 10), f4);
    Par<List<Integer>> pl = Par.choiceMap(pb, Map.<Integer, Par<List<Integer>>>empty().put(1, p1).put(2, p2).put(3, p3));
    try {
      List<Integer> result = Par.run(es,  pl).get();
      assertEquals("[1, 4, 9, 16, 25, 36, 49, 64, 81, NIL]", result.toString());
    } catch (InterruptedException | ExecutionException e) {
      fail();
    }
  }

  @Test
  public void testChoiceMap2() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 3;
    Par<Integer> pb = Par.unit(() -> x);
    Par<List<Integer>> p1 = Par.parMap(List.range(1, 10), f2);
    Par<List<Integer>> p2 = Par.parMap(List.range(1, 10), f3);
    Par<List<Integer>> p3 = Par.parMap(List.range(1, 10), f4);
    Par<List<Integer>> pl = Par.choiceMap(pb, Map.<Integer, Par<List<Integer>>>empty().put(1, p1).put(2, p2).put(3, p3));
    try {
      List<Integer> result = Par.run(es,  pl).get();
      assertEquals("[1, 16, 81, 256, 625, 1296, 2401, 4096, 6561, NIL]", result.toString());
    } catch (InterruptedException | ExecutionException e) {
      fail();
    }
  }

  @Test(expected=IllegalStateException.class)
  public void testChoiceMap3() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 4;
    Par<Integer> pb = Par.unit(() -> x);
    Par<List<Integer>> p1 = Par.parMap(List.range(1, 10), f2);
    Par<List<Integer>> p2 = Par.parMap(List.range(1, 10), f3);
    Par<List<Integer>> p3 = Par.parMap(List.range(1, 10), f4);
    Par<List<Integer>> pl = Par.choiceMap(pb, Map.<Integer, Par<List<Integer>>>empty().put(1, p1).put(2, p2).put(3, p3));
    try {
      List<Integer> result = Par.run(es,  pl).get();
      assertEquals("[1, 4, 9, 16, 25, 36, 49, 64, 81, NIL]", result.toString());
    } catch (InterruptedException | ExecutionException e) {
      fail();
    }
  }

  @Test
  public void testChoiceMap4() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 3;
    Par<Integer> pb = Par.unit(() -> x);
    Par<List<Integer>> p1 = Par.parMap(List.range(1, 10), f2);
    Par<List<Integer>> p2 = Par.parMap(List.range(1, 10), g);
    Par<List<Integer>> p3 = Par.parMap(List.range(1, 10), f4);
    Par<List<Integer>> pl = Par.choiceMap(pb, Map.<Integer, Par<List<Integer>>>empty().put(1, p1).put(2, p2).put(3, p3));
    try {
      List<Integer> result = Par.run(es,  pl).get();
      assertEquals("[1, 16, 81, 256, 625, 1296, 2401, 4096, 6561, NIL]", result.toString());
    } catch (InterruptedException | ExecutionException e) {
      fail();
    }
  }


  @Test
  public void testChoiceMap5() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 2;
    Par<Integer> pb = Par.unit(() -> x);
    Par<List<Integer>> p1 = Par.parMap(List.range(1, 10), f2);
    Par<List<Integer>> p2 = Par.parMap(List.range(1, 10), g);
    Par<List<Integer>> p3 = Par.parMap(List.range(1, 10), f4);
    Par<List<Integer>> pl = Par.choiceMap(pb, Map.<Integer, Par<List<Integer>>>empty().put(1, p1).put(2, p2).put(3, p3));
    try {
      List<Integer> result = Par.run(es,  pl).get();
      assertEquals("[1, 4, 9, 16, 25, 36, 49, 64, 81, NIL]", result.toString());
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


}
