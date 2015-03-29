package com.fpinjava.functionalparallelism.exercise02;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.fpinjava.common.Function;
import com.fpinjava.common.Supplier;

/*
 * What if `run` were backed by a `java.util.concurrent.ExecutorService`? You
 * may want to spend some time looking through the `java.util.concurrent`
 * package to see what other useful things you can find.
 */
public interface Par<A> extends Function<ExecutorService, Future<A>> {

  /*-
   * `map2` doesn't evaluate the call to `f` in a separate logical thread, in
   * accord with our design choice of having `fork` be the sole function in the
   * API for controlling parallelism. We can always do `fork(map2(a,b)(f))` if
   * we want the evaluation of `f` to occur in a separate thread.
   *
   * This implementation of `map2` does _not_ respect timeouts. It simply passes
   * the `ExecutorService` on to both `Par` values, waits for the results of the
   * Futures `af` and `bf`, applies `f` to them, and wraps them in a
   * `UnitFuture`. In order to respect timeouts, we'd need a new `Future`
   * implementation that records the amount of time spent evaluating `af`, then
   * subtracts that time from the available time allocated for evaluating `bf`.
   */
  public static <A, B, C> Par<C> map2(Par<A> a, Par<B> b, Function<A, Function<B, C>> f) {
    return (ExecutorService es) -> {
      Future<A> af = a.apply(es);
      Future<B> bf = b.apply(es);
      try {
        return new UnitFuture<>(f.apply(af.get()).apply(bf.get()));
      } catch (InterruptedException | ExecutionException e) {
        throw new IllegalStateException(e);
      }
    };
  }

  /*
   * `unit` is represented as a function that returns a `UnitFuture`, which is a
   * simple implementation of `Future` that just wraps a constant value. It
   * doesn't use the `ExecutorService` at all. It's always done and can't be
   * cancelled. Its `get` method simply returns the value that we gave it.;
   */
  public static <A> Par<A> unit(Supplier<A> a) {
    return es -> new UnitFuture<>(a.get());
  }

  /*-
   * This is the simplest and most natural implementation of `fork`, but there
   * are some problems with it--for one, the outer `Callable` will block waiting
   * for the "inner" task to complete. Since this blocking occupies a thread in
   * our thread pool, or whatever resource backs the `ExecutorService`, this
   * implies that we're losing out on some potential parallelism. Essentially,
   * we're using two threads when one should suffice. This is a symptom of a
   * more serious problem with the implementation, and we will discuss this
   * later in the chapter.
   *
   * This problem is difficult to see when using lambdas, so we include a
   * second version fork_ using an explicit anonymous class.
   */
  public static <A> Par<A> fork(Supplier<Par<A>> a) {
    return es -> es.submit(() -> a.get().apply(es).get());
  }

  /*
   * The fork implementation using an anonymous class
   */
  public static <A> Par<A> fork_(Supplier<Par<A>> a) {
    return es -> es.submit(new Callable<A>() {

      @Override
      public A call() throws Exception {
        return a.get().apply(es).get();
      }
    });
  }

  public static <A> Par<A> lazyUnit(Supplier<A> a) {
    return fork(() -> unit(a));
  }

  public static <A> Future<A> run(ExecutorService s, Par<A> a) {
    return a.apply(s);
  }

  public static class UnitFuture<A> implements Future<A> {

    private final A get;

    public UnitFuture(A get) {
      super();
      this.get = get;
    }

    @Override
    public boolean cancel(boolean evenIfRunning) {
      return false;
    }

    @Override
    public boolean isCancelled() {
      return false;
    }

    @Override
    public boolean isDone() {
      return true;
    }

    @Override
    public A get(long timeout, TimeUnit unit) {
      return get();
    }

    @Override
    public A get() {
      return this.get;
    }
  }
}
