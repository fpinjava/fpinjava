package test;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fj.F;
import fj.P;
import fj.P2;
import fj.control.parallel.ParModule;
import fj.control.parallel.Promise;
import fj.control.parallel.Strategy;
import fj.data.List;
import fj.data.Option;


public class ParModuleTestApp {

  private static boolean printThreadName = false;
  
  public static void main(String... args) throws InterruptedException {
    int testLimit = 50000; // Erroneous result above 50000
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
    int limit = testLimit;
    F<Integer, Option<P2<Integer, Integer>>> range = x -> x < limit ? Option.some(P.p(x + 1, x + 1)) : Option.none();
    List<Integer> testList = List.unfold(range, 0); // List range will stack overflow
    long expected = testList.map(x -> x * x).foldRightC((x, y) -> x + y, 0L).run();
    System.out.println("Expected: " + expected);

    List<Long> l = testList.map(x -> (long) x);
    Strategy<fj.Unit> st = Strategy.errorStrategy(Strategy.executorStrategy(es), x -> x.printStackTrace());
    Promise<List<Long>> p = ParModule.parModule(st).parMap(l, g);
    /*
     * Apply the function and wait for the Future to complete, then return the result.
     * This is where the program might hang.
     */
    List<Long> list = p.claim();
    long result = list.foldRightC((x, y) -> x + y, 0L).run();
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
