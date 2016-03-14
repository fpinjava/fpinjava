package com.fpinjava.advancedlisthandling.exercise08_23;


import com.fpinjava.common.Result;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Count the time necessary to fold a list of 35 000 random longs between 0 and 30 by adding the result of applying a particularly slow
 * implementation of the fibonacci function.
 *
 * On a four cores Macintosh with Java 8 64 bits, using 4 threads, the result is 22 seconds for 10 iterations. The serial version takes 83 seconds.
 */
public class ParallelFoldLeftBenchmark {


  public static void main(String[] args) {
    int testLimit = 35000;
    int numberOfThreads = 4;

    List<Long> testList = SimpleRNG.doubles(testLimit, new SimpleRNG.Simple(1))._1.map(x -> (long) (x * 30));
    ExecutorService es = Executors.newFixedThreadPool(numberOfThreads);

    test(5, testList, es);
    long start = System.currentTimeMillis();
    test(10, testList, es);
    System.out.println(System.currentTimeMillis() - start);
    es.shutdown();
  }

  private static void test(final int n,
                           final List<Long> list,
                           final ExecutorService es) {
    for (int i = 0; i < n; i++) {
      Result<BigInteger> result = list.parFoldLeft(es, BigInteger.ZERO, a -> b -> a.add(BigInteger.valueOf(fibo(b))), a -> a::add);
      result.forEachOrThrow(r -> System.out.println("Result:   " + r));
    }
  }

  private static long fibo(long x) {
    return x == 0
        ? 0
        : x == 1
            ? 1
            : fibo(x - 1) + fibo(x - 2);
  }

}
