package com.fpinjava.functionalparallelism.exercise10;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import com.fpinjava.common.Either;
import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Nothing;
import com.fpinjava.common.Option;
import com.fpinjava.common.Supplier;
import com.fpinjava.common.Tuple;

public interface Par<A> extends Function<ExecutorService, Future<A>> {

  public static <A> Par<A> unit(A a) {
    return (ExecutorService es) -> new Future<A>() {

      @Override
      void apply(Consumer<A> k) {
        k.accept(a);
      }
    };
  }

  public static <A> Par<A> fork(Supplier<Par<A>> a) {
    return es -> new Future<A>() {
       @Override
      void apply(Consumer<A> k) {
        eval(es, new Runnable() {

          @Override
          public void run() {
            a.get().apply(es).apply(k);
          }
        });
      }
    };
  }

  /*
   * Helper function, for evaluating an action
   * asynchronously, using the given `ExecutorService`.
   */
  public static void eval(ExecutorService es, Runnable r) {
    es.submit(r);
  }

  public static <A> A run(ExecutorService es, Par<A> p) {
    /*
     * A mutable, thread safe reference, to use for storing the result
     */
    AtomicReference<A> ref = new AtomicReference<>();
    /*
     *  A latch which, when decremented, implies that `ref` has the result
     */
    CountDownLatch latch = new CountDownLatch(1);
    /*
     *  Asynchronously set the result, and decrement the latch
     */
    p.apply(es).apply(a -> {
      ref.set(a);
      latch.countDown();
    });
    /*
     *  Block until the `latch.countDown` is invoked asynchronously
     */
    try {
      latch.await();
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }
    return ref.get();
  }

  public static <A, B, C> Par<C> map2(Par<A> p1, Par<B> p2, Function<A, Function<B, C>> f) {
      return es -> new Future<C>() {

        @Override
        void apply(Consumer<C> cb) {
//          Option<A> ar = Option.none(); // must be final
//          Option<B> br = Option.none();
          Wrapper<A, B> wr = new Wrapper<>();
          /*
           *  this implementation is a little too liberal in forking of threads -
           *  it forks a new logical thread for the actor and for stack-safety,
           *  forks evaluation of the callback `cb`
           */
          Consumer<Either<A, B>> handler = x -> {
            if (x.isLeft()) {
              if (wr.br.isSome()) {
                eval(es, () -> cb.accept(f.apply(x.left()).apply(wr.br.get())));
              } else {
                wr.ar = Option.some(x.left());
              }
            } else if (x.isRight()) {
              if (wr.ar.isSome()) {
                eval(es, () -> cb.accept(f.apply(wr.ar.get()).apply(x.right())));
              } else {
                wr.br = Option.some(x.right());
              }
            }
          };
          Actor<Either<A,B>> combiner = Actor.apply(es, handler);
          p1.apply(es).apply(a -> combiner.tell(Either.left(a)));
          p2.apply(es).apply(b -> combiner.tell(Either.right(b)));
        }
      };
  }

  public static class Wrapper<A, B> {
    public Option<A> ar = Option.none();
    public Option<B> br = Option.none();
  }

  public static <A, B> Par<List<B>> parMap(List<A> as, Function<A, B> f) {
    final List<Par<B>> fbs = as.map(asyncF(f));
//    fbs.forEach(x -> x.apply(Executors.newFixedThreadPool(2)).apply(z -> System.out.println(z)));      
//    fbs.forEach(y -> y.apply(java.util.concurrent.Executors.newFixedThreadPool(2)).apply(x -> System.out.println(x)));
//    System.out.println(fbs);
//    System.out.println(fbs.length());
    return sequence(fbs);
  }

  public static <A> Par<A> lazyUnit(Supplier<A> a) {
    return fork(() -> unit(a.get()));
  }

  public static <A, B> Function<A, Par<B>> asyncF(Function<A, B> f) {
    return a -> lazyUnit(() -> f.apply(a));
  }
  
//  public static <A> Par<List<A>> sequence_simple(List<Par<A>> list) {
//    return list.foldRight(unit(() -> List.list()), h -> t -> map2(h, t, x -> y -> y.cons(x)));
//  }

  public static <A> Par<List<A>> sequence(List<Par<A>> list) {
    if (list.isEmpty()) {
      return unit(List.list());
    } else if (list.length() == 1) {
      return map(list.head(), a -> List.list(a));
    } else {
      Tuple<List<Par<A>>, List<Par<A>>> tuple = list.splitAt(list.length() / 2);
      System.out.println(tuple._1.length() + " - " + tuple._2.length());
      return fork(() -> map2(sequence(tuple._1), sequence(tuple._2), x -> y -> List.concat(x, y)));
    }
  }

  public static <A, B> Par<B> map(Par<A> pa, Function<A, B> f) {
    return map2(pa, unit(Nothing.instance), a -> ignore -> f.apply(a));
  }

}
