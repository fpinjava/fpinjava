package com.fpinjava.state.exercise12_10_;

import com.fpinjava.common.Function;
import com.fpinjava.common.Tuple;

public class Random<A> extends State<RNG, A> {

  public Random(Function<RNG, Tuple<A, RNG>> run) {
    super(run);
  }
}
