package com.fpinjava.functionaparallelism.listing06;

import java.util.function.Consumer;


public abstract class Future<A> {

  abstract void apply(Consumer<A> k);
}
