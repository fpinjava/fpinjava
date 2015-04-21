package com.fpinjava.state;

import com.fpinjava.common.Tuple;


public interface RNG {

  Tuple<Integer, RNG> nextInt();
}
