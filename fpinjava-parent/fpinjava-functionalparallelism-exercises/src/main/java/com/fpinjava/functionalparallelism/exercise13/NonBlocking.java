package com.fpinjava.functionalparallelism.exercise13;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

import com.fpinjava.common.Effect;
import com.fpinjava.common.Either;
import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Map;
import com.fpinjava.common.Option;
import com.fpinjava.common.Supplier;
import com.fpinjava.common.Try;
import com.fpinjava.common.Tuple;

public class NonBlocking {

  public interface Par<A> extends Function<ExecutorService, Future<A>> {}

  public static <A> Par<A> unit(A a) {
    return es -> (cb, ce) -> {
      try {
        cb.apply(a);
      } catch (Exception e) {
        ce.apply(e);
      }
    };
  }

  /*
   * In case of an exception, the error handler is invoked
   */
  public static <A> Par<A> fork(Supplier<Par<A>> a) {
    return es -> (cb, ce) -> eval(es, () -> {
      try {
        a.get().apply(es).apply(cb, ce);
      } catch (Exception e) {
        ce.apply(e);
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
    }, e -> {
      ref.set(Try.failure(e.getMessage())); // Set the result to the error
      latch.countDown(); // Decrement the latch to allow termination
    });
    try {
      latch.await();
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }
    return ref.get();
  }

  public static <A, B, C> Par<C> map2(Par<A> p1, Par<B> p2, Function<A, Function<B, C>> f) {
    return es -> (cb, ce) -> {
      Wrapper<A> ar = new Wrapper<>(Option.none());
      Wrapper<B> br = new Wrapper<>(Option.none());

      Actor<Either<A, B>> combiner = Actor.apply(es, x -> {
        if (x.isLeft()) {
          if (br.get().isSome()) {
            eval(es, () -> cb.apply(f.apply(x.left()).apply(br.get().get())));
          } else {
            ar.set(Option.some(x.left()));
          }
        } else if (x.isRight()) {
          if (ar.get().isSome()) {
            eval(es, () -> cb.apply(f.apply(ar.get().get()).apply(x.right())));
          } else {
            br.set(Option.some(x.right()));
          }
        }
      });
      p1.apply(es).apply(a -> combiner.tell(Either.left(a)), ce);
      p2.apply(es).apply(b -> combiner.tell(Either.right(b)), ce);
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
    return es -> (cb, ce) -> p.apply(es).apply(a -> eval(es, () -> cb.apply(f.apply(a))), ce);
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

  public static <A> Par<A> choice(Par<Boolean> p, Par<A> t, Par<A> f) {
    return es -> new Future<A>() {
      public void apply(Effect<A> cb, Effect<Throwable> ce) {
        p.apply(es).apply(b -> {
          if (b) {
            eval(es, () -> t.apply(es).apply(cb, ce));
          }
          else {
            eval(es, () -> f.apply(es).apply(cb, ce));
          }
        }, ce);
       }
    };
  }

  public static <A> Par<A> choiceN(Par<Integer> n, List<Par<A>> ps) {
    return es -> new Future<A>() {

      @Override
      public void apply(Effect<A> cb, Effect<Throwable> ce) {
        n.apply(es).apply(ind -> eval(es, () -> {
          try {
            ps.getAt(ind).get().apply(es).apply(cb, ce);
          } catch (Exception e) {
            ce.apply(e);
          }
        }), ce);
      }
    };
  }

  public static <A> Par<A> choiceViaChoiceN(Par<Boolean> a, Par<A> ifTrue, Par<A> ifFalse) {
    return choiceN(map(a, b -> b ? 0 : 1), List.list(ifTrue, ifFalse));
  }

  public static <K, V> Par<V> choiceMap(Par<K> p, Map<K,Par<V>> ps) {
    return es -> new Future<V>() {

        @Override
        public void apply(Effect<V> cb, Effect<Throwable> ce) {
          p.apply(es).apply(k -> ps.get(k).forEachOrException(x -> x.apply(es).apply(cb, ce)).forEach(ce::apply), ce);
        }
      };
  }

  /*
   * `chooser` is usually called `flatMap` or `bind`.
   */
  public static <A, B> Par<B> chooser(Par<A> p, Function<A, Par<B>> f) {
    return flatMap(p, f);
  }

  public static <A, B> Par<B> flatMap(Par<A> p, Function<A, Par<B>> f) {
    throw new RuntimeException("To be implemented");
  }

  public static <A> Par<A> choiceViaFlatMap(Par<Boolean> p, Par<A> t, Par<A> f) {
    throw new RuntimeException("To be implemented");
  }

  public static <A> Par<A> choiceNViaFlatMap(Par<Integer> p, List<Par<A>> choices) {
    throw new RuntimeException("To be implemented");
  }

}
