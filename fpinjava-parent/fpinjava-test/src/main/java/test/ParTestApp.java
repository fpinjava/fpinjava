package test;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fj.F;
import fj.data.List;


public class ParTestApp {

  private static boolean printThreadName = false;
  
  public static void main(String... args) throws InterruptedException {
    int testLimit = 40; // <= program hangs fo value > 60
    int numberOfThreads = 2;
    if (args.length > 0) {
      try {
        testLimit = Integer.parseInt(args[0]);
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
    List<Integer> testList = List.range(0, testLimit);
    long expected = testList.map(x -> x * x).foldRight(x -> y -> x + y, 0L);
    System.out.println("Expected: " + expected);
    /*
     * Construct a Function<ExecutorService, Future<List<Long>> (our Future, not java.util.concurrent.Future)
     * where List<Long> is the original list lazily mapped with x -> x * x.
     */
    NonBlocking.Par<List<Long>> p = NonBlocking.parMap(testList.map(x -> (long) x), g);
    /*
     * Apply the function and wait for the Future to complete, then return the result.
     * This is where the program might hang.
     */
    List<Long> list = NonBlocking.run(es, p);
    long result = list.foldRight(x -> y -> x + y, 0L);
    System.out.println("Result:   " + result);
    es.shutdown();
  }
  
  private static F<Long, Long> g = x -> {
    if (printThreadName) {
      System.out.println(Thread.currentThread().getName());
    }
//    if (x == 10) {
//      throw new IllegalStateException("Value is 10");
//    }
    return x * x;
  };

}
