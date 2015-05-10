package com.fpinjava.functionalparallelism.listing05;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.fpinjava.common.Function;
import com.fpinjava.common.Supplier;

public interface Par<A> extends Function<ExecutorService, Future<A>> {

  static <A> Par<A> unit(Supplier<A> a) {
    return es -> new UnitFuture<>(a.get());
  }

  static <A, B, C> Par<C> map2(Par<A> a, Par<B> b, Function<A, Function<B, C>> f) {
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
  static <A> Par<A> fork(Supplier<Par<A>> a) {
    return es -> es.submit(() -> a.get().apply(es).get());
  }

  static <A> Future<A> run(ExecutorService s, Par<A> a) {
    return a.apply(s);
  }

  class UnitFuture<A> implements Future<A> {

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
