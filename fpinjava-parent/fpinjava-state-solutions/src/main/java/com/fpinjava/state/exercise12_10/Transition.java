package com.fpinjava.state.exercise12_10;


import com.fpinjava.common.Function;

public interface Transition<A, S> extends Function<StateTuple<A, S>, S> {}
