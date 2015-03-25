package com.fpinjava.functionalparallelism.listing07;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class MyExecutorService implements ExecutorService {

  private final ExecutorService es;
  private final ExecutorService es2;
  private int callables;
  private int runnables2;
  private int runnables;
  
  public MyExecutorService(int num) {
    super();
    this.es = Executors.newFixedThreadPool(num / 2);
    this.es2 = Executors.newFixedThreadPool(num / 2);
  }

  @Override
  public void execute(Runnable command) {
    this.es.execute(command);
  }

  @Override
  public void shutdown() {
    this.es.shutdown();
  }

  @Override
  public List<Runnable> shutdownNow() {
    return this.es.shutdownNow();
  }

  @Override
  public boolean isShutdown() {
    return this.es.isShutdown();
  }

  @Override
  public boolean isTerminated() {
    return this.es.isTerminated();
  }

  @Override
  public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
    return this.es.awaitTermination(timeout, unit);
  }

  @Override
  public <T> Future<T> submit(Callable<T> task) {
//    System.out.println("Callables: " + (++callables) + " - Total: " + (runnables + callables));
//    try {
//      Thread.sleep(1);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
    return es.submit(task);
  }

  @Override
  public <T> Future<T> submit(Runnable task, T result) {
//    System.out.println("Runnables + Results: " + (++runnables2));
    return es.submit(task, result);
  }

  @Override
  public Future<?> submit(Runnable task) {
//    System.out.println("Runnables: " + (++runnables) + " - Total: " + (runnables + callables));
//    try {
//      Thread.sleep(1);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
    return es2.submit(task);
  }

  @Override
  public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
    return es.invokeAll(tasks);
  }

  @Override
  public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
    return es.invokeAll(tasks, timeout, unit);
  }

  @Override
  public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
    return es.invokeAny(tasks);
  }

  @Override
  public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
    return es.invokeAny(tasks, timeout, unit);
  }

}
