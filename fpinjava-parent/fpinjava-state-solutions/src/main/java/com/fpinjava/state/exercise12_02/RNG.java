package com.fpinjava.state.exercise12_02;


import com.fpinjava.common.Tuple;

public interface RNG {
  Tuple<Integer, RNG> nextInt();
}
