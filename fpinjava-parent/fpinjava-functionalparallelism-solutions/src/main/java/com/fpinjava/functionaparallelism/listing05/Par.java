package com.fpinjava.functionaparallelism.listing05;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.fpinjava.common.Function;
import com.fpinjava.common.Supplier;

public interface Par<A> extends Function<ExecutorService, Future<A>> {

  public static <A> Par<A> unit(Supplier<A> a) {
    return (ExecutorService es) -> new UnitFuture<>(a.get());
  }

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

  public static <A> Par<A> fork(Supplier<Par<A>> a) {
    return es -> es.submit(new Callable<A>() {

      @Override
      public A call() throws Exception {
        return a.get().apply(es).get();
      }
    });
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
