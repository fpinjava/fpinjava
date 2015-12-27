package com.fpinjava.actors.listing14_03;


import com.fpinjava.actors.DaemonThreadFactory;
import com.fpinjava.actors.listing14_01.Actor;
import com.fpinjava.actors.listing14_02.ActorContext;
import com.fpinjava.actors.listing14_02.MessageProcessor;
import com.fpinjava.common.Result;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;


public abstract class AbstractActor<T> implements Actor<T> {

  private final ActorContext<T> context;
  protected final String id;
  private final ExecutorService executor;

  public AbstractActor(String id, Actor.Type type) {
    super();
    this.id = id;
    this.executor = type == Type.SERIAL
        ? Executors.newSingleThreadExecutor(new DaemonThreadFactory())
        : Executors.newCachedThreadPool(new DaemonThreadFactory());

    this.context = new ActorContext<T>() {
      private MessageProcessor<T> behavior =
          AbstractActor.this::onReceive;
      @Override
      public synchronized void become(MessageProcessor<T> behavior) {
        this.behavior = behavior;
      }

      @Override
      public MessageProcessor<T> getBehavior() {
        return behavior;
      }
    };
  }

  public abstract void onReceive(T message, Result<Actor<T>> sender);

  public Result<Actor<T>> self() {
    return Result.success(this);
  }

  public ActorContext<T> getContext() {
    return this.context;
  }

  @Override
  public void shutdown() {
    this.executor.shutdown();
  }

  public synchronized void tell(final T message, Result<Actor<T>> sender) {
    executor.execute(() -> {
      try {
        context.getBehavior().process(message, sender);
      } catch (RejectedExecutionException e) {
        /*
         * This is probably normal and means all pending tasks
         * were canceled because the actor was stopped.
         */
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }
}
