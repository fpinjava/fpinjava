package com.fpinjava.laziness;

import com.fpinjava.common.Function;


public abstract class MayBe<A> implements Functor<A> {


  public static class Nothing<A> extends MayBe<A> {
    
    @Override
    public <B> Functor<B> fmap(Function<A, B> f) {
      return new Nothing<B>();
    }
  }
  
  public static class Just<A> extends MayBe<A> {
    
    private final A value;
    
    public Just(A value) {
      super();
      this.value = value;
    }

    @Override
    public <B> Functor<B> fmap(Function<A, B> f) {
      return new Just<B>(f.apply(value));
    }
  }
  
}
