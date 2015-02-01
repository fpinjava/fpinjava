package com.fpinjava.recursion.listing04_03;

import static com.fpinjava.recursion.listing04_03.TailCall.*;

public class Add {

  static TailCall<Integer> add(int x, int y) {
    return y == 0
        ? ret(x)
        : sus(() -> add(x + 1, y - 1));
  }
}
