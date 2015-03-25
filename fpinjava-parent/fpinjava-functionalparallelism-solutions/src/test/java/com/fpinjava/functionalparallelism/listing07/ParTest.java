package com.fpinjava.functionalparallelism.listing07;


import com.fpinjava.common.List;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class ParTest {

  @Test
  public void testSequence() {
    ExecutorService es = Executors.newFixedThreadPool(2);
    List<Long> testList = List.range(0L, 30_000); // <= program hangs fo value > 37 000 (may vary over time and depending on the platform)
    long expected = testList.foldRight(0L, x -> y -> x + y);
    Par<List<Long>> p = Par.sequenceBalanced(testList.map(Par::unit));
    List<Long> list;
    list = Par.run(es, p);
    long result = list.foldRight(0L, x -> y -> x + y);
    assertEquals(expected, result);
  }

  public static void main(String... args) {
    ExecutorService es = Executors.newFixedThreadPool(2);
    List<Long> testList = List.range(0L, 30_000); // <= program hangs fo value > 37 000 (may vary over time and depending on the platform)
    long expected = testList.foldRight(0L, x -> y -> x + y);
    Par<List<Long>> p = Par.sequenceBalanced(testList.map(Par::unit));
    List<Long> list;
    list = Par.run(es, p);
    long result = list.foldRight(0L, x -> y -> x + y);
    System.out.println(expected == result ? "Success" : String.format("Failure: expected %s, was %s"));
    es.shutdown();
  }
}
