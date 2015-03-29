package com.fpinjava.functionalparallelism.listing07;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;

public class ParTestApp {

  private static boolean printThreadName = false;
  
  public static void main(String... args) throws InterruptedException {
    long testLimit = 10_000;
    int numberOfThreads = 2;
    if (args.length > 0) {
      try {
        testLimit = Long.parseLong(args[0]);
      } catch (NumberFormatException e) {
        System.out.println(String.format("Invalid argument for test limit: %s. Using default %s", args[0], testLimit));
      }
    }
    if (args.length > 1) {
      try {
        numberOfThreads = Integer.parseInt(args[1]);
      } catch (NumberFormatException e) {
        System.out.println(String.format("Invalid argument for number of threads: %s. Using default %s", args[1], numberOfThreads));
      }
    }
    if (args.length > 2) {
      printThreadName = Boolean.parseBoolean(args[2]);
    }
    ExecutorService es = Executors.newFixedThreadPool(numberOfThreads);
    List<Long> testList = List.range(0L, testLimit);
    long expected = testList.map(x -> x * x).foldRight(0L, x -> y -> x + y);
    System.out.println("Expected: " + expected);
    /*
     * Construct a Function<ExecutorService, Future<List<Long>> (our Future, not java.util.concurrent.Future)
     * where List<Long> is the original list lazily mapped with x -> x * x.
     */
    Par<List<Long>> p = Par.parMap(testList, g);
    /*
     * Apply the function and wait for the Future to complete, then return the result.
     * This is where the program might hang.
     */
    List<Long> list = Par.run(es, p);
    long result = list.foldRight(0L, x -> y -> x + y);
    System.out.println("Result:   " + result);
    es.shutdown();
  }
  
  private static Function<Long, Long> g = x -> {
    if (printThreadName) {
      System.out.println(Thread.currentThread().getName());
    }
    if (x == 10) {
      //throw new IllegalStateException("x == 10");
    }
    return x * x;
  };

}
