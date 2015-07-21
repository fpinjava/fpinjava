package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import fj.F;
import fj.P;
import fj.P2;
import fj.control.parallel.ParModule;
import fj.control.parallel.Promise;
import fj.control.parallel.Strategy;
import fj.data.List;
import fj.data.Option;


public class ParModuleTestApp {

  private static boolean printThreadName = true;

  public static void main(String... args) throws InterruptedException {
    int testLimit = 30000;
    int numberOfThreads = 2;
    ExecutorService es = Executors.newFixedThreadPool(numberOfThreads);
    F<Long, Option<P2<Long, Long>>> range = x -> x < testLimit ? Option.some(P.p(x + 1, x + 1)) : Option.none();
    List<Long> testList = List.unfold(range, 0L); // List range will stack overflow

    Strategy<fj.Unit> st = Strategy.errorStrategy(Strategy.executorStrategy(es), Throwable::printStackTrace);
    Promise<List<Long>> p = ParModule.parModule(st).parMap(testList, g);
    /*
     * Apply the function and wait for the Future to complete, then return the result.
     * This is where the program might hang.
     */
    List<Long> list = p.claim();
    long result = list.foldRightC((x, y) -> x + y, 0L).run();
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
