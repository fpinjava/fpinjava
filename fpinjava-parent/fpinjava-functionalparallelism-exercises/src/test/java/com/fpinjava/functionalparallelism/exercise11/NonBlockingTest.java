package com.fpinjava.functionalparallelism.exercise11;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;

public class NonBlockingTest {

  @Test
  public void testChoice() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 2;
    NonBlocking.Par<Boolean> pb = NonBlocking.unit(x % 2 == 0);
    NonBlocking.Par<List<Integer>> p1 = NonBlocking.parMap(List.range(1, 10), f2);
    NonBlocking.Par<List<Integer>> p2 = NonBlocking.parMap(List.range(1, 10), f3);
    NonBlocking.Par<List<Integer>> pl = NonBlocking.choice(pb, p1, p2);
    List<Integer> result = NonBlocking.run(es,  pl).getOrThrow();
    assertEquals("[1, 4, 9, 16, 25, 36, 49, 64, 81, NIL]", result.toString());
  }

  @Test
  public void testChoice2() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 3;
    NonBlocking.Par<Boolean> pb = NonBlocking.unit(x % 2 == 0);
    NonBlocking.Par<List<Integer>> p1 = NonBlocking.parMap(List.range(1, 10), f2);
    NonBlocking.Par<List<Integer>> p2 = NonBlocking.parMap(List.range(1, 10), f3);
    NonBlocking.Par<List<Integer>> pl = NonBlocking.choice(pb, p1, p2);
    List<Integer> result = NonBlocking.run(es,  pl).getOrThrow();
    assertEquals("[1, 8, 27, 64, 125, 216, 343, 512, 729, NIL]", result.toString());
  }

  @Test(expected=IllegalStateException.class)
  public void testChoice3() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 3;
    NonBlocking.Par<Boolean> pb = NonBlocking.unit(x % 2 == 0);
    NonBlocking.Par<List<Integer>> p1 = NonBlocking.parMap(List.range(1, 10), f2);
    NonBlocking.Par<List<Integer>> p2 = NonBlocking.parMap(List.range(1, 10), g);
    NonBlocking.Par<List<Integer>> pl = NonBlocking.choice(pb, p1, p2);
    List<Integer> result = NonBlocking.run(es,  pl).getOrThrow();
    assertEquals("[1, 8, 27, 64, 125, 216, 343, 512, 729, NIL]", result.toString());
  }

  @Test
  public void testChoiceN1() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 0;
    NonBlocking.Par<Integer> pb = NonBlocking.unit(x);
    NonBlocking.Par<List<Integer>> p1 = NonBlocking.parMap(List.range(1, 10), f2);
    NonBlocking.Par<List<Integer>> p2 = NonBlocking.parMap(List.range(1, 10), f3);
    NonBlocking.Par<List<Integer>> p3 = NonBlocking.parMap(List.range(1, 10), f4);
    NonBlocking.Par<List<Integer>> pl = NonBlocking.choiceN(pb, List.list(p1, p2, p3));
    List<Integer> result = NonBlocking.run(es,  pl).getOrThrow();
    assertEquals("[1, 4, 9, 16, 25, 36, 49, 64, 81, NIL]", result.toString());
  }

  @Test
  public void testChoiceN2() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 1;
    NonBlocking.Par<Integer> pb = NonBlocking.unit(x);
    NonBlocking.Par<List<Integer>> p1 = NonBlocking.parMap(List.range(1, 10), f2);
    NonBlocking.Par<List<Integer>> p2 = NonBlocking.parMap(List.range(1, 10), f3);
    NonBlocking.Par<List<Integer>> p3 = NonBlocking.parMap(List.range(1, 10), f4);
    NonBlocking.Par<List<Integer>> pl = NonBlocking.choiceN(pb, List.list(p1, p2, p3));
    List<Integer> result = NonBlocking.run(es,  pl).getOrThrow();
    assertEquals("[1, 8, 27, 64, 125, 216, 343, 512, 729, NIL]", result.toString());
  }

  @Test
  public void testChoiceN3() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 2;
    NonBlocking.Par<Integer> pb = NonBlocking.unit(x);
    NonBlocking.Par<List<Integer>> p1 = NonBlocking.parMap(List.range(1, 10), f2);
    NonBlocking.Par<List<Integer>> p2 = NonBlocking.parMap(List.range(1, 10), f3);
    NonBlocking.Par<List<Integer>> p3 = NonBlocking.parMap(List.range(1, 10), f4);
    NonBlocking.Par<List<Integer>> pl = NonBlocking.choiceN(pb, List.list(p1, p2, p3));
    List<Integer> result = NonBlocking.run(es,  pl).getOrThrow();
    assertEquals("[1, 16, 81, 256, 625, 1296, 2401, 4096, 6561, NIL]", result.toString());
  }

  @Test(expected=IllegalStateException.class)
  public void testChoiceN4() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 3;
    NonBlocking.Par<Integer> pb = NonBlocking.unit(x);
    NonBlocking.Par<List<Integer>> p1 = NonBlocking.parMap(List.range(1, 10), f2);
    NonBlocking.Par<List<Integer>> p2 = NonBlocking.parMap(List.range(1, 10), f3);
    NonBlocking.Par<List<Integer>> p3 = NonBlocking.parMap(List.range(1, 10), f4);
    NonBlocking.Par<List<Integer>> pl = NonBlocking.choiceN(pb, List.list(p1, p2, p3));
    List<Integer> result = NonBlocking.run(es,  pl).getOrThrow();
    assertEquals("[1, 16, 81, 256, 625, 1296, 2401, 4096, 6561, NIL]", result.toString());
  }

  @Test(expected=IllegalStateException.class)
  public void testChoiceN5() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 0;
    NonBlocking.Par<Integer> pb = NonBlocking.unit(x);
    NonBlocking.Par<List<Integer>> p1 = NonBlocking.parMap(List.range(1, 10), g);
    NonBlocking.Par<List<Integer>> p2 = NonBlocking.parMap(List.range(1, 10), f3);
    NonBlocking.Par<List<Integer>> p3 = NonBlocking.parMap(List.range(1, 10), f4);
    NonBlocking.Par<List<Integer>> pl = NonBlocking.choiceN(pb, List.list(p1, p2, p3));
    List<Integer> result = NonBlocking.run(es,  pl).getOrThrow();
    assertEquals("[1, 16, 81, 256, 625, 1296, 2401, 4096, 6561, NIL]", result.toString());
  }

  @Test
  public void testChoiceViaChoiceN() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 2;
    NonBlocking.Par<Boolean> pb = NonBlocking.unit(x % 2 == 0);
    NonBlocking.Par<List<Integer>> p1 = NonBlocking.parMap(List.range(1, 10), f2);
    NonBlocking.Par<List<Integer>> p2 = NonBlocking.parMap(List.range(1, 10), f3);
    NonBlocking.Par<List<Integer>> pl = NonBlocking.choiceViaChoiceN(pb, p1, p2);
    List<Integer> result = NonBlocking.run(es,  pl).getOrThrow();
    assertEquals("[1, 4, 9, 16, 25, 36, 49, 64, 81, NIL]", result.toString());
  }

  @Test
  public void testChoiceViaChoiceN2() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 3;
    NonBlocking.Par<Boolean> pb = NonBlocking.unit(x % 2 == 0);
    NonBlocking.Par<List<Integer>> p1 = NonBlocking.parMap(List.range(1, 10), f2);
    NonBlocking.Par<List<Integer>> p2 = NonBlocking.parMap(List.range(1, 10), f3);
    NonBlocking.Par<List<Integer>> pl = NonBlocking.choiceViaChoiceN(pb, p1, p2);
    List<Integer> result = NonBlocking.run(es,  pl).getOrThrow();
    assertEquals("[1, 8, 27, 64, 125, 216, 343, 512, 729, NIL]", result.toString());
  }

  @Test(expected=IllegalStateException.class)
  public void testChoiceViaChoiceN3() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    int x = 3;
    NonBlocking.Par<Boolean> pb = NonBlocking.unit(x % 2 == 0);
    NonBlocking.Par<List<Integer>> p1 = NonBlocking.parMap(List.range(1, 10), f2);
    NonBlocking.Par<List<Integer>> p2 = NonBlocking.parMap(List.range(1, 10), g);
    NonBlocking.Par<List<Integer>> pl = NonBlocking.choiceViaChoiceN(pb, p1, p2);
    List<Integer> result = NonBlocking.run(es,  pl).getOrThrow();
    assertEquals("[1, 8, 27, 64, 125, 216, 343, 512, 729, NIL]", result.toString());
  }

  private static Function<Integer, Integer> f2 = x -> x * x;

  private static Function<Integer, Integer> f3 = x -> x * x * x;

  private static Function<Integer, Integer> f4 = x -> x * x * x * x;

  private static Function<Integer, Integer> g = x -> {
    if (x == 5) {
      throw new IllegalStateException("x == 5");
    }
    return x * x;
  };

}
