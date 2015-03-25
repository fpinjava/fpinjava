package com.fpinjava.functionalparallelism.listing07;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

import com.fpinjava.common.Either;
import com.fpinjava.common.Function;
import com.fpinjava.common.List;
import com.fpinjava.common.Nothing;
import com.fpinjava.common.Option;
import com.fpinjava.common.Supplier;
import com.fpinjava.common.Tuple;

public class NonBlocking {

  public interface Future<A> {

    void apply(Consumer<A> k);
  }
  
  public interface Par<A> extends Function<ExecutorService, Future<A>> {}

  public static int terminatingCount;
  
  public static <A> A run(ExecutorService es, Par<A> p) {
    AtomicReference<A> ref = new AtomicReference<>();
    CountDownLatch latch = new CountDownLatch(1);
    p.apply(es).apply(a -> {
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

//  public static <A> Par<A> unit(A a) {
//    return (ExecutorService es) -> new Future<A>() {
//    
//      @Override
//      public void apply(Consumer<A> k) {
//        k.accept(a);
//      }
//    };
//  }

  public static <A> Par<A> fork(Supplier<Par<A>> a) {
    return es -> cb -> eval(es, () -> {++terminatingCount;a.get().apply(es).apply(cb);});
  }
  
//  public static <A> Par<A> fork(Supplier<Par<A>> a) {
//    return es -> new Future<A>() {
//      @Override
//      public void apply(Consumer<A> cb) {
//        eval(es, () -> a.get().apply(es).apply(cb)) ;
//      }
//    };
//  }

//  public static <A> Par<A> fork(Supplier<Par<A>> a) {
//    return es -> new Future<A>() {
//    
//      @Override
//      public void apply(Consumer<A> k) {
//        eval(es, new Runnable() {
//    
//          @Override
//          public void run() {
//            a.get().apply(es).apply(k);
//          }
//        });
//      }
//    };
//  }

  public static void eval1(ExecutorService es, Runnable r) {
    es.submit(r);
  }

  public static void eval(ExecutorService es, Runnable r) {
    es.submit(new Callable<Nothing>(){
    
      @Override
      public Nothing call() throws Exception {
        r.run();
        return null;
      }});
  }

  public static <A, B, C> Par<C> map2(Par<A> p1, Par<B> p2, Function<A, Function<B, C>> f) {
    return es -> cb -> {
      AtomicReference<Option<A>> ar = new AtomicReference<>(Option.none());
      AtomicReference<Option<B>> br = new AtomicReference<>(Option.none());
      /*
       * this implementation is a little too liberal in forking of threads -
       * it forks a new logical thread for the actor and for stack-safety,
       * forks evaluation of the callback `cb`
       */
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

//  public static <A, B> Par<B> map(Par<A> p, Function<A, B> f) {
//    return map2(p, unit(Nothing.instance), a -> ignore -> f.apply(a));
//  }

  public static <A, B> Par<B> map(Par<A> p, Function<A, B> f) {
    return es -> cb -> p.apply(es).apply(a -> eval(es, () -> cb.accept(f.apply(a))));
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
    
  public static <A> Par<List<A>> sequence(List<Par<A>> as) {
    return map(sequenceBalanced(as), a -> a);
  }

  public static <A, B> Par<List<B>> parMap(List<A> as, Function<A, B> f) {
    return sequence(as.map(asyncF(f)));
  }

  public static <A, B> Par<List<B>> parMap_(List<A> as, Function<A, B> f) {
    return sequenceBalanced(as.map(asyncF(f)));
  }


}
