package com.fpinjava.functionaparallelism.listing01;

import java.util.concurrent.TimeUnit;


public interface Future<A> {

  boolean cancel(boolean evenIfRunning);

  boolean isCancelled();

  boolean isDone();

  A get();

  A get(long timeout, TimeUnit unit);
}
