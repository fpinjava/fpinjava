package com.fpinjava.advancedlisthandling.exercise08_24;


import com.fpinjava.common.Function;

import java.math.BigInteger;

public class SerialMapBenchmark {

  /*
   * Time for serial map: Mac 64 bits: 91787 ms Linux 32 bits: 256345 ms
   * Result: 1552643551
   */
  public static void main(String... args) throws InterruptedException {
    int testLimit = 35000;

    List<Long> testList = SimpleRNG.doubles(testLimit, new SimpleRNG.Simple(1))._1.map(x -> (long) (x * 30));

    Function<Long, Long> f = SerialMapBenchmark::fibo;

    Function<BigInteger, Function<Long, BigInteger>> g = x -> y -> x.add(BigInteger.valueOf(y));

    for (int i = 0; i < 5; i ++) {
      List<Long> result = testList.map(f);
      System.out.println("Result:   " + result.foldLeft(BigInteger.ZERO, g));
    }
    long start = System.currentTimeMillis();
    for (int i = 0; i < 10; i ++) {
      List<Long> result = testList.map(f);
      System.out.println("Result:   " + result.foldLeft(BigInteger.ZERO, g));
    }
    System.out.println(System.currentTimeMillis() - start);
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
