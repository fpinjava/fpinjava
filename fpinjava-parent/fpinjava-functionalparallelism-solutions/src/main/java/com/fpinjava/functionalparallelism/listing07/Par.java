package com.fpinjava.functionalparallelism.listing07;

import com.fpinjava.common.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

public interface Par<A> extends Function<ExecutorService, Future<A>> {

//  public static <A> Par<A> unit(A a) {
//    return (ExecutorService es) -> new Future<A>() {
//
//      @Override
//      public void apply(Consumer<A> k) {
//        k.accept(a);
//      }
//    };
//  }

  public static <A> Par<A> unit(A a) {
    return es -> k -> k.accept(a);
  }

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

  public static <A> Par<A> fork(Supplier<Par<A>> a) {
    return es -> k -> eval(es, () -> a.get().apply(es).apply(k));
  }

//public static void eval(ExecutorService es, Runnable r) {
//es.submit(new Callable<Nothing>(){
//
//  @Override
//  public Nothing call() throws Exception {
//    r.run();
//    return null;
//  }});
//}

  /*
   * Helper function, for evaluating an action asynchronously, using the given
   * `ExecutorService`.
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

  public static <A, B, C> Par<C> map2(Par<A> p1, Par<B> p2, Function<A, Function<B, C>> f) {
    //System.out.print("Entering map2...");
    Par<C> result = es -> cb -> {
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
//              System.out.println(Thread.currentThread().getName());
//              cb.accept(f.apply(x.left()).apply(br.get().get()));
          } else {
            ar.set(Option.some(x.left()));
          }
        } else if (x.isRight()) {
          if (ar.get().isSome()) {
            eval(es, () -> cb.accept(f.apply(ar.get().get()).apply(x.right())));
//              System.out.println(Thread.currentThread().getName());
//              cb.accept(f.apply(ar.get().get()).apply(x.right()));
          } else {
            br.set(Option.some(x.right()));
          }
        }
      });
      p1.apply(es).apply(a -> combiner.tell(Either.left(a)));
      p2.apply(es).apply(b -> combiner.tell(Either.right(b)));
    };
    //System.out.println("...out");
    return result;
  }

  /*-
  public static <A, B, C> Par<C> map2_(Par<A> p1, Par<B> p2, Function<A, Function<B, C>> f) {
    return map2Helper(TailCall.ret(p1), TailCall.ret(p2), f).eval();
  }

  public static <A, B, C> TailCall<Par<C>> map2Helper(TailCall<Par<A>> p1, TailCall<Par<B>> p2, Function<A, Function<B, C>> f) {
    Par<C> result = es -> new Future<C>() {

      @Override
      public void apply(Consumer<C> cb) {
        AtomicReference<Option<A>> ar = new AtomicReference<>(Option.none());
        AtomicReference<Option<B>> br = new AtomicReference<>(Option.none());
        /*
         * this implementation is a little too liberal in forking of threads -
         * it forks a new logical thread for the actor and for stack-safety,
         * forks evaluation of the callback `cb`
         *
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
        p1.eval().apply(es).apply(a -> combiner.tell(Either.left(a)));
        p2.eval().apply(es).apply(b -> combiner.tell(Either.right(b)));
      }
    };
    return TailCall.ret(result);
  }*/

  public static <A, B> Par<List<B>> parMap(List<A> ps, Function<A, B> f) {
    final List<Par<B>> fbs = ps.map(asyncF(f));
    return sequenceBalanced(fbs);
  }

  public static <A> Par<A> lazyUnit(Supplier<A> a) {
    return fork(() -> unit(a.get()));
  }

  public static <A, B> Function<A, Par<B>> asyncF(Function<A, B> f) {
    return a -> lazyUnit(() -> f.apply(a));
  }

//  public static <A> Par<List<A>> sequence(List<Par<A>> list) {
//    if (list.isEmpty()) {
//      return unit(List.list());
//    } else if (list.length() == 1) {
//      return map(list.head(), List::list);
//    } else {
//      Tuple<List<Par<A>>, List<Par<A>>> tuple = list.splitAt(list.length() / 2);
//      return fork(() -> map2(sequence_(tuple._1), sequence_(tuple._2), x -> y -> List.concat(x, y)));
//    }
//  }

  public static <A> Par<List<A>> sequenceBalanced(List<Par<A>> as) {
    Tuple<List<Par<A>>, List<Par<A>>> tuple = as.splitAt(as.length() / 2);
    return fork(() -> as.isEmpty()
        ? unit(List.list())
        : as.length() == 1
            ? map(as.head(), List::list)
            : map2(sequenceBalanced(tuple._1), sequenceBalanced(tuple._2), x -> y -> List.concat(x, y)));
  }
  
//  public static <A> Par<List<A>> sequence(List<Par<A>> as) {
//    if (as.isEmpty()) {
//      return fork(() -> unit(List.list()));
//    } else if (as.length() == 1) {
//      return fork(() -> map(as.head(), List::list));
//    } else {
//      Tuple<List<Par<A>>, List<Par<A>>> tuple = as.splitAt(as.length() / 2);
//      return fork(() -> map2(sequence(tuple._1), sequence(tuple._2), x -> y -> List.concat(x, y)));
//    }
//  }

//  public static <A> Par<List<A>> sequence2(List<Par<A>> as) {
//    Function<Par<List<A>>, Function<Par<A>, Par<List<A>>>> f = lpa -> pa -> {
//      return es -> map
//    };
//    
//    return as.foldLeft(unit(List.list()), f);
//  }

  
//  fork {
//    if (as.isEmpty) unit(Vector())
//    else if (as.length == 1) map(as.head)(a => Vector(a))
//    else {
//      val (l, r) = as.splitAt(as.length / 2)
//      map2(sequenceBalanced(l), sequenceBalanced(r))(_ ++ _)
//    }
//  }

//  public static <A, B> Par<B> map(Par<A> pa, Function<A, B> f) {
//    return map2(pa, unit(Nothing.instance), a -> ignore -> f.apply(a));
//  }

  public static <A, B> Par<B> map(Par<A> p, Function<A, B> f) {
    return es -> cb -> p.apply(es).apply(a -> eval(es, () -> cb.accept(f.apply(a))));
  }

  /*-
  public static <A> Par<List<A>> sequenceRec(List<Par<A>> list) {
    return sequenceHelper(list).eval();
  }

  public static <A> TailCall<Par<List<A>>> sequenceHelper(List<Par<A>> list) {
    if (list.isEmpty()) {
      return TailCall.ret(unit(List.list()));
    } else if (list.length() == 1) {
      return TailCall.ret(map(list.head(), a -> List.list(a)));
    } else {
      Tuple<List<Par<A>>, List<Par<A>>> tuple = list.splitAt(list.length() / 2);
      TailCall<Par<List<A>>> a = sequenceHelper(tuple._1);
      TailCall<Par<List<A>>> b = sequenceHelper(tuple._2);
      return TailCall.sus(() -> map2Helper(a, b, x -> y -> List.concat(x, y)));
    }
  }*/

//  public static <A> Par<List<A>> sequence_(List<Par<A>> list) {
//    if (list.isEmpty()) {
//      return fork(() -> unit(List.list()));
//    } else if (list.length() == 1) {
//      return fork(() -> map(list.head(), a -> List.list(a)));
//    } else {
//      Tuple<List<Par<A>>, List<Par<A>>> tuple = list.splitAt(list.length() / 2);
//      return fork(() -> map2(sequence_(tuple._1), sequence_(tuple._2), x -> y -> List.concat(x, y)));
//    }
//  }

  public static <A> Par<List<A>> sequence2(List<Par<A>> list) {
    if (list.isEmpty()) {
      return fork(() -> unit(List.list()));
    } else if (list.length() == 1) {
      return fork(() -> map(list.head(), a -> List.list(a)));
    } else {
      return list.foldRight(unit(List.list()), h -> t -> map2(h, t, x -> y -> y.cons(x)));
    }
  }
  
  public static <A> Par<List<A>> sequence3(List<Par<A>> list) {
    if (list.isEmpty()) {
      return fork(() -> unit(List.list()));
    } else if (list.length() == 1) {
      return fork(() -> map(list.head(), a -> List.list(a)));
    } else {
      return list.foldLeft(unit(List.list()), h -> t -> map2(t, h, x -> y -> y.cons(x)));
    }
  }
  
//
//  public static <A, B, C> Par<C> map2_(Par<A> a, Par<B> b, Function<A, Function<B, C>> f) {
//    return (ExecutorService es) -> new Future<C>() {
//
//      @Override
//      public void apply(Consumer<C> k) {
//        A aa = Par.run(es, a);
//        B bb = Par.run(es, b);
//        k.accept(f.apply(aa).apply(bb));
//      }
//    };
//  }
//
//  public static <A, B, C> Par<C> map2__(Par<A> a, Par<B> b, Function<A, Function<B, C>> f) {
//    return (ExecutorService es) -> {
//      return ex -> new Future<C>() {
//
//        @Override
//        public void apply(Consumer<C> k) {
//          eval(es, new Runnable() {
//
//            @Override
//            public void run() {
//              A aa = Par.run(es, a);
//              B bb = Par.run(es, b);
//              k.accept(f.apply(aa).apply(bb));
//            }
//          });
//        }
//      };
//    };
//  }

}
