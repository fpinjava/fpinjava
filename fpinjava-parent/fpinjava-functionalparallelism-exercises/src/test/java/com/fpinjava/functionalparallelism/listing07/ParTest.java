package com.fpinjava.functionalparallelism.listing07;


import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.fpinjava.common.List;

public class ParTest {

  @Test
  public void testSequence() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    List<Long> testList = List.range(0L, 10_000);
    long expected = testList.foldRight(0L, x -> y -> x + y);
    Par<List<Long>> p = Par.sequenceBalanced(testList.map(Par::unit));
    List<Long> list;
    list = Par.run(es, p);
    long result = list.foldRight(0L, x -> y -> x + y);
    assertEquals(expected, result);
  }

 
  public static void main(String... args) {
      ExecutorService es = Executors.newFixedThreadPool(2);
      NonBlocking.Par<List<Double>> p = NonBlocking.parMap(List.range(1, 10_000), x -> Math.sqrt(x));
      List<Double> list = NonBlocking.run(es, p);
      System.out.println(list);
      es.shutdown();
  }

}
