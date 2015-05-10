package com.fpinjava.functionalparallelism.listing06;

import com.fpinjava.common.Function;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

public interface Par<A> extends Function<ExecutorService, Future<A>> {


  static <A> A run(ExecutorService es, Par<A> p) {

    AtomicReference<A> ref = new AtomicReference<>(); //#A

    CountDownLatch latch = new CountDownLatch(1);  //#B

    p.apply(es).apply(a -> {      //#C
      ref.set(a);
      latch.countDown();
    });

    try {
      latch.await();   //#D
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }
    return ref.get();  //#E
  }
}
