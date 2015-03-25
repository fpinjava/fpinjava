package test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import fj.F;
import fj.F0;
import fj.P2;
import fj.control.parallel.Actor;
import fj.control.parallel.Strategy;
import fj.data.Either;
import fj.data.List;
import fj.data.Option;
import fj.function.Effect1;

public class NonBlocking {

  public interface Future<A> {

    void apply(Consumer<A> k);
  }
  
  public interface Par<A> extends F<ExecutorService, Future<A>> {}
  
  public static <A> A run(ExecutorService es, Par<A> p) {
    AtomicReference<A> ref = new AtomicReference<>();
    CountDownLatch latch = new CountDownLatch(1);
    p.f(es).apply(a -> {
      ref.set(a);
      latch.countDown();
    });
    try {
      latch.await();
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }
    return ref.get();
  }
  
  public static <A> Par<A> unit(A a) {
    return es -> cb -> cb.accept(a);
  }

  public static <A> Par<A> fork(F0<Par<A>> a) {
    return es -> cb -> eval(es, () -> a.f().f(es).apply(cb));
  }

  public static void eval(ExecutorService es, Runnable r) {
    es.submit(r);
  }

  public static <A, B, C> Par<C> map2(Par<A> p1, Par<B> p2, F<A, F<B, C>> f) {
    return es -> cb -> {
      AtomicReference<Option<A>> ar = new AtomicReference<>(Option.none());
      AtomicReference<Option<B>> br = new AtomicReference<>(Option.none());
      /*
       * this implementation is a little too liberal in forking of threads -
       * it forks a new logical thread for the actor and for stack-safety,
       * forks evaluation of the callback `cb`
       */
      Strategy<fj.Unit> st = Strategy.errorStrategy(Strategy.executorStrategy(es), x -> x.printStackTrace());
      Effect1<Either<A, B>> effect = (Either<A, B> x) -> {
        if (x.isLeft()) {
          if (br.get().isSome()) {
            eval(es, () -> cb.accept(f.f(x.left().value()).f(br.get().some())));
          } else {
            ar.set(Option.some(x.left().value()));
          }
        } else if (x.isRight()) {
          if (ar.get().isSome()) {
            eval(es, () -> cb.accept(f.f(ar.get().some()).f(x.right().value())));
          } else {
            br.set(Option.some(x.right().value()));
          }
        }
      };
      Actor<Either<A, B>> combiner = Actor.actor(st, effect);
      p1.f(es).apply(a -> combiner.act(Either.left(a)));
      p2.f(es).apply(b -> combiner.act(Either.right(b)));
    };
  }

//  public static <A, B> Par<B> map(Par<A> p, Function<A, B> f) {
//    return map2(p, unit(Nothing.instance), a -> ignore -> f.apply(a));
//  }

  public static <A, B> Par<B> map(Par<A> p, F<A, B> f) {
    return es -> cb -> p.f(es).apply(a -> eval(es, () -> cb.accept(f.f(a))));
  }

  public static <A> Par<A> lazyUnit(F0<A> a) {
    return fork(() -> unit(a.f()));
  }

  public static <A, B> F<A, Par<B>> asyncF(F<A, B> f) {
    return a -> lazyUnit(() -> f.f(a));
  }
  
  public static <A> Par<List<A>> compose(Par<List<A>> p1, Par<List<A>> p2) {
    return map2(p1, p2, x -> y -> x.append(y));
  }

  @SuppressWarnings("unchecked")
  public static <A> Par<List<A>> sequenceBalanced(List<Par<A>> as) {
    if (as.isEmpty()) {
      return unit(List.list());
    } else if (as.length() == 1) {
      return fork(() -> map(as.head(), List::list));
    } else {
      P2<List<Par<A>>, List<Par<A>>> tuple = as.splitAt(as.length() / 2);
      return fork(() -> map2(sequenceBalanced(tuple._1()), sequenceBalanced(tuple._2()), x -> y -> x.append(y)));
    }
  }
  
//  public static <A, B> Par<List<B>> parMap_(List<A> as, F<A, B> f) {
//    return sequence(as.map(asyncF(f)));
//  }

  public static <A, B> Par<List<B>> parMap(List<A> as, F<A, B> f) {
    return sequenceBalanced(as.map(asyncF(f)));
  }


}
