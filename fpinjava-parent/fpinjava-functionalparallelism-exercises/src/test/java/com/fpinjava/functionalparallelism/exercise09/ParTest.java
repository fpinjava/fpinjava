package com.fpinjava.functionalparallelism.exercise09;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fpinjava.functionalparallelism.exercise09.Par;

public class ParTest {

  /*
   * This method will hang with a deadlock. For a thread pool of size 2,
   * `fork(fork(fork(x)))` will deadlock, and so on. Another, perhaps more
   * interesting example is `fork(map2(fork(x), fork(y)))`. In this case, the
   * outer task is submitted first and occupies a thread waiting for both
   * `fork(x)` and `fork(y)`. The `fork(x)` and `fork(y)` tasks are submitted
   * and run in parallel, except that only one thread is available, resulting in
   * deadlock.
   */
  public static void main(String... args) {
    Par<Integer> a = Par.lazyUnit(() -> 42 + 1);
    ExecutorService es = Executors.newFixedThreadPool(1);
    System.out.println(Par.equal(es, a, Par.fork(() -> a)));
  }
}
