package com.fpinjava.laziness;

import com.fpinjava.common.Function;


public interface Functor<A> {

  <B> Functor<B> fmap(Function<A, B> f);
}
