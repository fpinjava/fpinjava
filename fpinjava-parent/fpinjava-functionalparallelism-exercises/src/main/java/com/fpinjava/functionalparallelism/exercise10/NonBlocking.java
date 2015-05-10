package com.fpinjava.functionalparallelism.exercise10;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

import com.fpinjava.common.Either;
import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Option;
import com.fpinjava.common.Supplier;
import com.fpinjava.common.Try;
import com.fpinjava.common.Tuple;

public class NonBlocking {

  public interface Par<A> extends Function<ExecutorService, Future<A>> {}

  public static <A> Par<A> unit(A a) {
    return es -> cb -> cb.accept(a);
  }

  /*
   * In case of an exception, the error handler is invoked
   */
  public static <A> Par<A> fork(Supplier<Par<A>> a) {
    return es -> cb -> eval(es, () -> {
      try {
        a.get().apply(es).apply(cb);
      } catch (Exception e) {
        // To be implemented;
      }
    });
  }

  public static void eval(ExecutorService es, Runnable r) {
      es.submit(r);
  }

  /*
   * The run method provides the error handler. The returned type has
   * been changed to possibly return the error.
   */
  public static <A> Try<A> run(ExecutorService es, Par<A> p) {
    AtomicReference<Try<A>> ref = new AtomicReference<>();
    CountDownLatch latch = new CountDownLatch(1);
    p.apply(es).apply(a -> {
      ref.set(Try.success(a));
      latch.countDown();
    });
    try {
      latch.await();
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }
    return ref.get();
  }

  public static <A, B, C> Par<C> map2(Par<A> p1, Par<B> p2, Function<A, Function<B, C>> f) {
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

  public static class Wrapper<A> {
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

  public static <A, B> Par<B> map(Par<A> p, Function<A, B> f) {
    return es -> cb -> p.apply(es).apply(a -> eval(es, () -> cb.accept(f.apply(a))));
  }

  public static <A, B> Par<List<B>> parMap(List<A> as, Function<A, B> f) {
    return sequenceBalanced(as.map(asyncF(f)));
  }

  public static <A> Par<A> lazyUnit(Supplier<A> a) {
    return fork(() -> unit(a.get()));
  }

  public static <A, B> Function<A, Par<B>> asyncF(Function<A, B> f) {
    return a -> lazyUnit(() -> f.apply(a));
  }

  public static <A> Par<List<A>> compose(Par<List<A>> p1, Par<List<A>> p2) {
    return map2(p1, p2, x -> y -> List.concat(x, y));
  }

  public static <A> Par<List<A>> sequenceBalanced(List<Par<A>> as) {
    Tuple<List<Par<A>>, List<Par<A>>> tuple = as.splitAt(as.length() / 2);
    return fork(() -> as.isEmpty()
        ? unit(List.list())
        : as.length() == 1
            ? map(as.head(), List::list)
            : map2(sequenceBalanced(tuple._1), sequenceBalanced(tuple._2), x -> y -> List.concat(x, y)));
  }

}
