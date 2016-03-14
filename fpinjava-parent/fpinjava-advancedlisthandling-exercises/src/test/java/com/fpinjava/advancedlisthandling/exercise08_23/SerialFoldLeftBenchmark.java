package com.fpinjava.advancedlisthandling.exercise08_23;



import java.math.BigInteger;


/*
 * Count the time necessary to fold a list of 35 000 random longs between 0 and 30 by adding the result of applying a particularly slow
 * implementation of the fibonacci function.
 *
 * On a four cores Macintosh with Java 8 64 bits, the result is 83 seconds for 10 iterations. The parallel version using 4 threads take only 22 seconds.
 */
public class SerialFoldLeftBenchmark {


  public static void main(String[] args) {
    int testLimit = 35000;

    List<Long> testList = SimpleRNG.doubles(testLimit, new SimpleRNG.Simple(1))._1.map(x -> (long) (x * 30));

    test(5, testList);
    long start = System.currentTimeMillis();
    test(10, testList);
    System.out.println(System.currentTimeMillis() - start);
  }

  private static void test(final int n,
                           final List<Long> list) {
    for (int i = 0; i < n; i++) {
      BigInteger result = list.foldLeft(BigInteger.ZERO, a -> b -> a.add(BigInteger.valueOf(fibo(b))));
      System.out.println("Result:   " + result);
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
