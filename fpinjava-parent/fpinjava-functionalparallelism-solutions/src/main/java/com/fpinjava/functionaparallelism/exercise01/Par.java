package com.fpinjava.functionaparallelism.exercise01;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Supplier;
import com.fpinjava.common.Tuple;

public interface Par<A> extends Function<ExecutorService, Future<A>> {

  public static Integer sum_(List<Integer> ints) {
    if (ints.length() <= 1) {
      return ints.headOption().getOrElse(0);
    } else {
      final Tuple<List<Integer>, List<Integer>> tuple = ints.splitAt(ints.length() / 2);
      return get(Par.map2(Par.unit(() -> sum_(tuple._1)), Par.unit(() -> sum_(tuple._2)), x -> y -> x + y));
    }
  }

  public static Par<Integer> sum(List<Integer> ints) {
    if (ints.length() <= 1) {
      return Par.unit(() -> ints.headOption().getOrElse(0));
    } else {
      final Tuple<List<Integer>, List<Integer>> tuple = ints.splitAt(ints.length() / 2);
      return Par.map2(sum(tuple._1), sum(tuple._2), x -> y -> x + y);
    }
  }

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
    return (ExecutorService es) -> new UnitFuture<>(a.get());
  }

  public static <A> A get(Par<A> a) {
    try {
      return a.apply(Executors.newCachedThreadPool()).get();
    } catch (InterruptedException | ExecutionException e) {
      throw new IllegalStateException(e);
    }
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
