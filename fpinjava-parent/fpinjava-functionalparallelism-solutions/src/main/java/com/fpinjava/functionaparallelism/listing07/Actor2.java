package com.fpinjava.functionaparallelism.listing07;


import com.fpinjava.common.Function;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/*
* Implementation is taken from `scalaz` library, with only minor changes. See:
*
* https://github.com/scalaz/scalaz/blob/scalaz-seven/concurrent/src/main/scala/scalaz/concurrent/Actor.scala
*
* This code is copyright Andriy Plokhotnyuk, Runar Bjarnason, and other contributors,
* and is licensed using 3-clause BSD, see LICENSE file at:
*
* https://github.com/scalaz/scalaz/blob/scalaz-seven/etc/LICENCE
*/

/**
* Processes messages of type `A`, one at a time. Messages are submitted to
* the actor with the method `!`. Processing is typically performed asynchronously,
* this is controlled by the provided `strategy`.
*
* Memory consistency guarantee: when each message is processed by the `handler`, any memory that it
* mutates is guaranteed to be visible by the `handler` when it processes the next message, even if
* the `strategy` runs the invocations of `handler` on separate threads. This is achieved because
* the `Actor` reads a volatile memory location before entering its event loop, and writes to the same
* location before suspending.
*
* Implementation based on non-intrusive MPSC node-based queue, described by Dmitriy Vyukov:
* [[http://www.1024cores.net/home/lock-free-algorithms/queues/non-intrusive-mpsc-node-based-queue]]
*
* @see scalaz.concurrent.Promise for a use case.
*
* @param handler The message handler
* @param onError Exception handler, called if the message handler throws any `Throwable`.
* @param strategy Execution strategy, for example, a strategy that is backed by an `ExecutorService`
* @tparam A The type of messages accepted by this actor.
*/
public class Actor2<A> {

  private A tail = null;
  private int suspended = 1;
  private A head = tail;
  private final Strategy strategy;
  private final Consumer<A> handler;
  private final Consumer<Throwable> onError;

  public Actor2(Strategy strategy, Consumer<A> handler) {
    this(strategy, handler, x -> {throw new IllegalStateException(x);});
  }

  public Actor2(Strategy strategy, Consumer<A> handler, Consumer<Throwable> onError) {
    this.strategy = strategy;
    this.handler = handler;
    this.onError = onError;
  }

  /*
   *  Pass the message `a` to the mailbox of this actor
   */
  public void tell(A a) {
    A n = head;
    head = a;
    //lazySet(n);
    trySchedule();
  }

//  public <B> Actor<B> contramap(Function<B, A> f) {
//    Consumer<B> cb = (B b) -> this.tell(f.apply(b));
//    return new Actor<B>(strategy, cb, onError);
//  }

  private void trySchedule() {
    if (suspended == 1) {
      suspended = 0;
      schedule();
    }
  }

  private void schedule() {
    act();
    strategy.apply(() -> null);
  }

  private void act() {
//    A t = tail;
//    A n = batchHandle(t, 1024);
//    if (n != t) {
//      n.a = null;
//      tail.lazySet(n);
//      schedule();
//    } else {
//      suspended = 1;
//      if (n.get() != null) trySchedule();
//    }
  }

  //@tailrec
  private A batchHandle(A t, int i) {
    A n = t;
    if (n != null) {
      try {
        handler.accept(n);
      } catch (Throwable ex) {
        onError.accept(ex);
      }
      if (i > 0) {
        return batchHandle(n, i - 1);
      } else {
        return n;
      }
    } else {
      return t;
    }
  }

//  @SuppressWarnings("serial")
//  static class Node<A> extends AtomicReference<Node<A>> {
//
//    private A a;
//
//    public Node() {
//      super();
//    }
//
//    public Node(A a) {
//      super();
//      this.a = a;
//    }
//
//    public A getA() {
//      return a;
//    }
//
//    public void setA(A a) {
//      this.a = a;
//    }
//
//  }

  /*
   *  Create an `Actor` backed by the given `ExecutorService`.
   */
  public static <A> Actor2<A> apply(ExecutorService es, Consumer<A> handler, Consumer<Throwable> onError) {
    return new Actor2<>(Strategy.fromExecutorService(es), handler, onError);
  }
}
