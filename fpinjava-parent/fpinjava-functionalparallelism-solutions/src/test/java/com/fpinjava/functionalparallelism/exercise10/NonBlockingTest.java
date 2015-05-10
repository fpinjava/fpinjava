package com.fpinjava.functionalparallelism.exercise10;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Try;

public class NonBlockingTest {

  @Test
  public void test1()  {
    ExecutorService es = Executors.newFixedThreadPool(2);
    NonBlocking.Par<List<Integer>> p = NonBlocking.parMap(List.range(1, 20), f);
    Try<List<Integer>> result = NonBlocking.run(es, p);
    assertEquals("Success([1, 4, 9, 16, 25, 36, 49, 64, 81, 100, 121, 144, 169, 196, 225, 256, 289, 324, 361, NIL])", result.toString());
  }

  @Test
  public void test2()  {
    ExecutorService es = Executors.newFixedThreadPool(2);
    NonBlocking.Par<List<Integer>> p = NonBlocking.parMap(List.range(1, 20), g);
    Try<List<Integer>> result = NonBlocking.run(es, p);
    assertEquals("Failure(x == 5)", result.toString());
  }

  private static Function<Integer, Integer> f = x -> x * x;

  private static Function<Integer, Integer> g = x -> {
    if (x == 5) {
      throw new IllegalStateException("x == 5");
    }
    return x * x;
  };

}
