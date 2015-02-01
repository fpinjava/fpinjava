package com.fpinjava.recursion.listing04_01;

import com.fpinjava.common.Supplier;

public interface TailCall<T> {

  TailCall<T> resume();

  T eval();

  boolean isSuspend();

  public class Return<T> implements TailCall<T> {

    private final T t;

    public Return(T t) {
      this.t = t;
    }

    @Override
    public T eval() {
      return t;
    }

    @Override
    public boolean isSuspend() {
      return false;
    }

    @Override
    public TailCall<T> resume() {
      throw new IllegalStateException("Return has no resume");
    }
  }

  public class Suspend<T> implements TailCall<T> {

    private final Supplier<TailCall<T>> resume;

    public Suspend(Supplier<TailCall<T>> resume) {
      this.resume = resume;
    }

    @Override
    public T eval() {
      throw new IllegalStateException("Suspend has no value");
    }

    @Override
    public boolean isSuspend() {
      return true;
    }

    @Override
    public TailCall<T> resume() {
      return resume.get();
    }
  }
}
