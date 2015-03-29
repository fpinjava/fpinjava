package com.fpinjava.functionalparallelism.exercise02;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.fpinjava.common.Function;
import com.fpinjava.common.Supplier;

public interface Par<A> extends Function<ExecutorService, Future<A>> {

  public static <A, B, C> Par<C> map2(Par<A> a, Par<B> b, Function<A, Function<B, C>> f) {
    throw new RuntimeException("To be implemented");
  }

  public static <A> Par<A> unit(Supplier<A> a) {
    throw new RuntimeException("To be implemented");
  }

  public static <A> Par<A> fork(Supplier<Par<A>> a) {
    throw new RuntimeException("To be implemented");
  }

  public static <A> Par<A> fork_(Supplier<Par<A>> a) {
    throw new RuntimeException("To be implemented");
  }

  public static <A> Par<A> lazyUnit(Supplier<A> a) {
    throw new RuntimeException("To be implemented");
  }

  public static <A> Future<A> run(ExecutorService s, Par<A> a) {
    throw new RuntimeException("To be implemented");
  }

  /*
   * Here is an implementation of future you may use for implementing 
   * unit and map2.
   */
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
