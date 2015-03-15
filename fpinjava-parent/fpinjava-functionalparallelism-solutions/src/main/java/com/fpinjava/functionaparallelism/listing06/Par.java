package com.fpinjava.functionaparallelism.listing06;

import com.fpinjava.common.Function;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

public interface Par<A> extends Function<ExecutorService, Future<A>> {


  public static <A> A run(ExecutorService es, Par<A> p) {

    AtomicReference<A> ref = new AtomicReference<>();

    CountDownLatch latch = new CountDownLatch(1);

    p.apply(es).apply(a -> {
      ref.set(a);
      latch.countDown();
    });

    try {
      latch.await();
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }
    return ref.get();
  }
}
