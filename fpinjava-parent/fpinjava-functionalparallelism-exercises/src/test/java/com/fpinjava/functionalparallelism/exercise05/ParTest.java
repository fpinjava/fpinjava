package com.fpinjava.functionalparallelism.exercise05;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

import com.fpinjava.common.List;
import com.fpinjava.common.Tuple;

public class ParTest {

  private static BigInteger fibo(int x) {
    return x == 0
        ? BigInteger.ZERO
        : x == 1
            ? BigInteger.ONE
            : fibo(x - 1).add(fibo(x - 2));
  }

  private static List<Integer> list = List.range(0, 40);
  private static String expected = "[0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418, 317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465, 14930352, 24157817, 39088169, 63245986, NIL]";

  @Test
  public void testParMap() {
    ExecutorService s = Executors.newFixedThreadPool(8);
    try {
      assertEquals("[NIL]", Par.run(s, Par.parMap(List.<Integer> list(), x -> fibo(x))).get().toString());
      assertEquals("[0, NIL]", Par.run(s, Par.parMap(List.list(0), x ->  fibo(x))).get().toString());
      assertEquals("[0, 1, NIL]", Par.run(s, Par.parMap(List.list(0, 1), x -> fibo(x))).get().toString());
      assertEquals("[0, 1, 1, NIL]", Par.run(s, Par.parMap(List.list(0, 1, 2), x -> fibo(x))).get().toString());
      assertEquals("[0, 1, 1, 2, NIL]", Par.run(s, Par.parMap(List.list(0, 1, 2, 3), x -> fibo(x))).get().toString());
      assertEquals("[0, 1, 1, 2, 3, NIL]", Par.run(s, Par.parMap(List.list(0, 1, 2, 3, 4), x -> fibo(x))).get().toString());
      assertEquals("[0, 1, 1, 2, 3, 5, NIL]", Par.run(s, Par.parMap(List.list(0, 1, 2, 3, 4, 5), x -> fibo(x))).get().toString());
      assertEquals("[0, 1, 1, 2, 3, 5, 8, NIL]", Par.run(s, Par.parMap(List.list(0, 1, 2, 3, 4, 5, 6), x -> fibo(x))).get().toString());
      assertEquals("[0, 1, 1, 2, 3, 5, 8, 13, NIL]", Par.run(s, Par.parMap(List.list(0, 1, 2, 3, 4, 5, 6, 7), x -> fibo(x))).get().toString());
      assertEquals("[0, 1, 1, 2, 3, 5, 8, 13, 21, NIL]", Par.run(s, Par.parMap(List.list(0, 1, 2, 3, 4, 5, 6, 7, 8), x -> fibo(x))).get().toString());
      assertEquals(expected, Par.run(s, Par.parMap(list, x -> fibo(x))).get().toString());
    } catch (InterruptedException | ExecutionException e) {
      fail();
    }
  }

  /*-
   * Compare performance between single-threaded serial and multi-threaded
   * parallel computation of fibonacci numbers from 0 to 39 by the slowest
   * possible method. Using Java 8 streams in serial and parallel mode is 
   * also compared.
   * 
   * Typical result on an 8 cores machine (in milliseconds):
   * 
   * Single-threaded serial computation of fibonnaci numbers from 0 to 39 (10 times): 66434
   * Multi-threaded parallel computation of fibonnaci numbers from 0 to 39 (10 times): 26126
   * Java 8 single-threaded serial computation of fibonnaci numbers from 0 to 39 (10 times): 66115
   * Java 8 multi-threaded parallel computation of fibonnaci numbers from 0 to 39 (10 times): 26256
   */
  public static void main(String[] args) {
    int count = 10;
    ExecutorService s = Executors.newCachedThreadPool();
    /*
     * Warming up the JVM
     */
    for (int i = 0; i < 5; i++) {
      list.map(x -> fibo(x)).foldRight(BigInteger.ZERO, x -> y -> x.add(y));
    }
    /*
     * Starting single-threaded serial computation
     */
    long time1 = System.currentTimeMillis();
    for (int i = 0; i < count; i++) {
      list.map(x -> fibo(x)).foldRight(BigInteger.ZERO, x -> y -> x.add(y));
    }
    System.out.println(String.format("Single-threaded serial computation of fibonnaci numbers from 0 to 39 (%s times): %s", count, System.currentTimeMillis() - time1));
    /*
     * Warming up the JVM
     */
    for (int i = 0; i < 5; i++) {
      Future<List<BigInteger>> f = Par.run(s, Par.parMap(list, x -> fibo(x)));
      try {
        Par.run(s, sum(f.get()));
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }
    /*
     * Starting multi-threaded parallel computation
     */
    long time2 = System.currentTimeMillis();
    for (int i = 0; i < count; i++) {
      Future<List<BigInteger>> f = Par.run(s, Par.parMap(list, x -> fibo(x)));
      try {
        Par.run(s, sum(f.get()));
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }
    System.out.println(String.format("Multi-threaded parallel computation of fibonnaci numbers from 0 to 39 (%s times): %s", count, System.currentTimeMillis() - time2));
    java.util.List<Integer> jlist = new ArrayList<>();
    for (int i = 0; i < 40; i++) {
      jlist.add(i);
    }
    /*
     * Warming up the JVM
     */
    for (int i = 0; i < 5; i++) {
      jlist.stream().map(x -> fibo(x)).reduce((x, y) -> x.add(y));
    }
    /*
     * Starting Java 8 single-threaded parallel computation
     */
    long time3 = System.currentTimeMillis();
    for (int i = 0; i < count; i++) {
      jlist.stream().map(x -> fibo(x)).reduce((x, y) -> x.add(y));
    }
    System.out.println(String.format("Java 8 single-threaded serial computation of fibonnaci numbers from 0 to 39 (%s times): %s", count, System.currentTimeMillis() - time3));
    /*
     * Warming up the JVM
     */
    for (int i = 0; i < 5; i++) {
      jlist.stream().parallel().map(x -> fibo(x)).reduce((x, y) -> x.add(y));
    }
    /*
     * Starting Java 8 multi-threaded parallel computation
     */
    long time4 = System.currentTimeMillis();
    for (int i = 0; i < count; i++) {
      jlist.stream().parallel().map(x -> fibo(x)).reduce((x, y) -> x.add(y));
    }
    System.out.println(String.format("Java 8 multi-threaded parallel computation of fibonnaci numbers from 0 to 39 (%s times): %s", count, System.currentTimeMillis() - time4));
  }

  public static Par<BigInteger> sum(List<BigInteger> ints) {
    if (ints.length() <= 1) {
      return Par.unit(() -> ints.headOption().getOrElse(BigInteger.ZERO));
    } else {
      final Tuple<List<BigInteger>, List<BigInteger>> tuple = ints.splitAt(ints.length() / 2);
      return Par.map2(Par.fork(() -> sum(tuple._1)), Par.fork(() -> sum(tuple._2)), x -> y -> x .add(y));
    }
  }

}
