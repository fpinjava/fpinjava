package com.fpinjava.functionalparallelism.listing07;

import com.fpinjava.common.Supplier;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/**
 * Provides a function for evaluating expressions, possibly asynchronously. The
 * `apply` function should typically begin evaluating its argument immediately.
 * The returned thunk can be used to block until the resulting `A` is available.
 */
public interface Strategy {

  <A> Supplier<A> apply(Supplier<A> a);

  /**
   * We can create a `Strategy` from any `ExecutorService`. It's a little more
   * convenient than submitting `Callable` objects directly.
   */
  public static Strategy fromExecutorService(ExecutorService es) {
    return new Strategy() {

      @Override
      public <A> Supplier<A> apply(Supplier<A> a) {

        java.util.concurrent.Future<A> f = es.submit(new Callable<A>() {

          @Override
          public A call() throws Exception {
            return a.get();
          }
        });
        return () -> {
          try {
            return f.get();
          } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException(e);
          }
        };
      }};
  }

  /**
   * A `Strategy` which begins executing its argument immediately in the calling
   * thread.
   */
  public static Strategy sequential() {
    return new Strategy() {

      @Override
      public <A> Supplier<A> apply(Supplier<A> a) {
        A r = a.get();
        return () -> r;
      }
    };
  }
}
