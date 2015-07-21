package test;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import fj.F;
import fj.data.List;


public class ParTestApp {

  private static boolean printThreadName = false;

  public static void main(String... args) throws InterruptedException {
    int testLimit = 40;
    int numberOfThreads = 2;
    ExecutorService es = Executors.newFixedThreadPool(numberOfThreads);
    List<Long> testList = List.range(0, testLimit).map(x -> (long) x);
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

  private static AtomicInteger count = new AtomicInteger();

  private static F<Long, Long> g = x -> {
    if (printThreadName) {
      System.out.println(Thread.currentThread().getName() + " - " + count.incrementAndGet());
    }
    return x * x;
  };

}
