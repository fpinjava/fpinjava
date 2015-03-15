package com.fpinjava.functionalstate.exercise08_09;

import com.fpinjava.common.Function;
import com.fpinjava.common.Tuple;

public interface State<S, A> extends Function<S, Tuple<A, S>> {}
