package com.fpinjava.laziness.listing07_04;

import com.fpinjava.common.Supplier;

public class Head<T> {
  
  private Supplier<T> nonEvaluated;
  private T evaluated;
  
  public Head(Supplier<T> nonEvaluated) {
    super();
    this.nonEvaluated = nonEvaluated;
  }

  public Head(Supplier<T> nonEvaluated, T evaluated) {
    super();
    this.nonEvaluated = nonEvaluated;
    this.evaluated = evaluated;
  }

  public Supplier<T> getNonEvaluated() {
    return nonEvaluated;
  }

  public T getEvaluated() {
    if (evaluated == null) {
      evaluated = nonEvaluated.get();
    }
    return evaluated;
  }
}
