package com.fpinjava.functionalparallelism.listing07;

import com.fpinjava.common.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

public interface Par<A> extends Function<ExecutorService, Future<A>> {

  static <A> Par<A> unit(A a) {
    return es -> cb -> cb.accept(a);
  }

  static <A> Par<A> fork(Supplier<Par<A>> a) {
    return es -> cb -> eval(es, () -> a.get().apply(es).apply(cb));
  }

  /*
   * Helper function, for evaluating an action asynchronously, using the given
   * `ExecutorService`.
   */
  static void eval(ExecutorService es, Runnable r) {
    es.submit(r);
  }

  static <A> A run(ExecutorService es, Par<A> p) {
    /*
     * A mutable, thread safe reference, to use for storing the result
     */
    AtomicReference<A> ref = new AtomicReference<>();
    /*
     * A latch which, when decremented, implies that `ref` has the result
     */
    CountDownLatch latch = new CountDownLatch(1);
    /*
     * Asynchronously set the result, and decrement the latch
     */
    p.apply(es).apply(a -> {
      ref.set(a);
      latch.countDown();
    });
    /*
     * Block until the `latch.countDown` is invoked asynchronously
     */
    try {
      latch.await();
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }
    return ref.get();
  }

  static <A, B, C> Par<C> map2(Par<A> p1, Par<B> p2, Function<A, Function<B, C>> f) {
    return es -> cb -> {
      Wrapper<A> ar = new Wrapper<>(Option.none());
      Wrapper<B> br = new Wrapper<>(Option.none());

      Actor<Either<A, B>> combiner = Actor.apply(es, x -> {
        if (x.isLeft()) {
          if (br.get().isSome()) {
            eval(es, () -> cb.accept(f.apply(x.left()).apply(br.get().get())));
          } else {
            ar.set(Option.some(x.left()));
          }
        } else if (x.isRight()) {
          if (ar.get().isSome()) {
            eval(es, () -> cb.accept(f.apply(ar.get().get()).apply(x.right())));
          } else {
            br.set(Option.some(x.right()));
          }
        }
      });
      p1.apply(es).apply(a -> combiner.tell(Either.left(a)));
      p2.apply(es).apply(b -> combiner.tell(Either.right(b)));
    };
  }

  class Wrapper<A> {
    public Option<A> value;

    public Wrapper(Option<A> value) {
      super();
      this.value = value;
    }

    public Option<A> get() {
      return value;
    }

    public void set(Option<A> value) {
      this.value = value;
    }
  }

  static <A, B> Par<B> map(Par<A> p, Function<A, B> f) {
    return es -> cb -> p.apply(es).apply(a -> eval(es, () -> cb.accept(f.apply(a))));
  }

  static <A, B> Par<List<B>> parMap(List<A> as, Function<A, B> f) {
    return sequence(as.map(asyncF(f)));
  }

  static <A> Par<A> lazyUnit(Supplier<A> a) {
    return fork(() -> unit(a.get()));
  }

  static <A, B> Function<A, Par<B>> asyncF(Function<A, B> f) {
    return a -> lazyUnit(() -> f.apply(a));
  }

  static <A> Par<List<A>> sequence(List<Par<A>> as) {
     return map(sequenceBalanced(as), x -> x);
  }

  static <A> Par<List<A>> sequenceBalanced(List<Par<A>> as) {
    Tuple<List<Par<A>>, List<Par<A>>> tuple = as.splitAt(as.length() / 2);
    return fork(() -> as.isEmpty()
        ? unit(List.list())
        : as.length() == 1
            ? map(as.head(), List::list)
            : map2(sequenceBalanced(tuple._1), sequenceBalanced(tuple._2), x -> y -> List.concat(x, y)));
  }

}
