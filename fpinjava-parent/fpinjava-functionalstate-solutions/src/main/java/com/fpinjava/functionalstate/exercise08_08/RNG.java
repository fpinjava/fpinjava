package com.fpinjava.functionalstate.exercise08_08;

import com.fpinjava.common.Tuple;

public interface RNG {

  Tuple<Integer, RNG> nextInt();
}
