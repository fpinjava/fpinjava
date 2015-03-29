package com.fpinjava.functionalparallelism.listing07;

import java.util.function.Consumer;


public interface Future<A> {
   void apply(Consumer<A> cb);
}
