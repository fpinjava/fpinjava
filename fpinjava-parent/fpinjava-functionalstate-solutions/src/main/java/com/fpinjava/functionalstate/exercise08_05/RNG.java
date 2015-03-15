package com.fpinjava.functionalstate.exercise08_05;

import com.fpinjava.common.Tuple;

public interface RNG {

  Tuple<Integer, RNG> nextInt();
}
