package com.fpinjava.functionalparallelism.exercise10;

import java.util.function.Consumer;

/*
 * Future.apply takes an error handler as its second parameter
 */
public interface Future<A> {
   void apply(Consumer<A> cb, Consumer<Throwable> onError);
}
