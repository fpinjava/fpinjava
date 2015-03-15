package com.fpinjava.functionalstate.exercise08_10;

import com.fpinjava.common.Function;
import com.fpinjava.common.Tuple;

public class Rand<A> extends State<RNG, A> {

  public Rand(Function<RNG, Tuple<A, RNG>> run) {
    super(run);
  }
}
