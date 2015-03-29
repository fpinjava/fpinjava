package com.fpinjava.functionalparallelism.exercise11;

import com.fpinjava.common.Effect;


/*
 * Future.apply takes an error handler as its second parameter
 */
public interface Future<A> {
   void apply(Effect<A> cb, Effect<Throwable> onError);
}
