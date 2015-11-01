package com.fpinjava.state.exercise12_04_;


import com.fpinjava.common.Tuple;

public interface RNG {
  Tuple<Integer, RNG> nextInt();
  int limit();
  int length();
}
