package com.fpinjava.state.exercise12_04_;


import com.fpinjava.common.Function;
import com.fpinjava.common.Tuple;

public interface State<S, A> extends Function<S, Tuple<A, S>> {}
