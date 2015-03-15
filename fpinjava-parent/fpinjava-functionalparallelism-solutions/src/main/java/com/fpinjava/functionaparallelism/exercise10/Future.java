package com.fpinjava.functionaparallelism.exercise10;

import java.util.function.Consumer;


public abstract class Future<A> {
  
  abstract void apply(Consumer<A> k);
}
