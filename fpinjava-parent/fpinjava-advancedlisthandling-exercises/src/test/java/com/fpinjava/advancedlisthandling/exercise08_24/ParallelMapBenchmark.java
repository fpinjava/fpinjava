package com.fpinjava.advancedlisthandling.exercise08_24;


import com.fpinjava.common.Function;
import com.fpinjava.common.Result;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelMapBenchmark {

  /*
   * Time for parallel (A,B, 4 threads, parallel map): Mac 64 bits: 22142 ms Linux 32 bits: 36378 ms
   * Result: 1552643551
   */
  public static void main(String... args) throws InterruptedException {
    int testLimit = 35000;

    List<Long> testList = SimpleRNG.doubles(testLimit, new SimpleRNG.Simple(1))._1.map(x -> (long) (x * 30));

    int numberOfThreads = 4;
    ExecutorService es = Executors.newFixedThreadPool(numberOfThreads);

    Function<Long, Long> f = ParallelMapBenchmark::fibo;

    Function<BigInteger, Function<Long, BigInteger>> g = x -> y -> x.add(BigInteger.valueOf(y));

    for (int i = 0; i < 5; i ++) {
      Result<List<Long>> result = testList.parMap(es, f);
      result.forEachOrThrow(r -> System.out.println("Result:   " + r.foldLeft(BigInteger.ZERO, g)));
    }
    long start = System.currentTimeMillis();
    for (int i = 0; i < 10; i ++) {
      Result<List<Long>> result = testList.parMap(es, f);
      result.forEachOrThrow(r -> System.out.println("Result:   " + r.foldLeft(BigInteger.ZERO, g)));
    }
    System.out.println(System.currentTimeMillis() - start);

    es.shutdown();
  }

  private static long fibo(long x) {
    if (x == 30) System.out.println(x);
    return x == 0
        ? 0
        : x == 1
            ? 1
            : fibo(x - 1) + fibo(x - 2);
  };
}
