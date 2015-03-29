package com.fpinjava.functionalparallelism.exercise03;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.fpinjava.common.Function;
import com.fpinjava.common.Option;
import com.fpinjava.common.Supplier;

public interface Par<A> extends Function<ExecutorService, Future<A>> {

  public static <A, B, C> Par<C> map2(Par<A> a, Par<B> b, Function<A, Function<B, C>> f) {
    return (ExecutorService es) -> {
      Future<A> af = a.apply(es);
      Future<B> bf = b.apply(es);
      return new Map2Future<>(af, bf, f);
    };
  }

  public static class Map2Future<A, B, C> implements Future<C> {

    private volatile Option<C> cache = Option.none();

    private final Future<A> a;
    private final Future<B> b;
    private final Function<A, Function<B, C>> f;

    public Map2Future(Future<A> a, Future<B> b, Function<A, Function<B, C>> f) {
      super();
      this.a = a;
      this.b = b;
      this.f = f;
    }

    @Override
    public boolean cancel(boolean evenIfRunning) {
      return a.cancel(evenIfRunning) || b.cancel(evenIfRunning);
    }

    @Override
    public boolean isCancelled() {
      return a.isCancelled() || b.isCancelled();
    }

    @Override
    public boolean isDone() {
      return cache == null;
    }

    @Override
    public C get() throws InterruptedException, ExecutionException {
      throw new RuntimeException("To be implemented");
    }

    @Override
    public C get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
      throw new RuntimeException("To be implemented");
    }
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

  /*
   * This is the simplest and most natural implementation of `fork`, but there
   * are some problems with it--for one, the outer `Callable` will block waiting
   * for the "inner" task to complete. Since this blocking occupies a thread in
   * our thread pool, or whatever resource backs the `ExecutorService`, this
   * implies that we're losing out on some potential parallelism. Essentially,
   * we're using two threads when one should suffice. This is a symptom of a
   * more serious problem with the implementation, and we will discuss this
   * later in the chapter.
   */
  public static <A> Par<A> fork(Supplier<Par<A>> a) {
    return es -> es.submit(() -> a.get().apply(es).get());
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
