package com.fpinjava.state.exercise12_10;


import com.fpinjava.common.Function;

public interface Condition<I, S> extends Function<StateTuple<I, S>, Boolean> {}
