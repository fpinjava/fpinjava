package com.fpinjava.state.exercise12_09;


import com.fpinjava.common.Tuple;

public interface RNG {
  Tuple<Integer, RNG> nextInt();
}
