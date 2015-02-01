package com.fpinjava.recursion.listing04_02;

import com.fpinjava.recursion.listing04_01.TailCall;

public class Add {

  static TailCall<Integer> add(int x, int y) {
    return y == 0
        ? new TailCall.Return<>(x)
        : new TailCall.Suspend<>(() -> add(x + 1, y - 1));
  }
}
