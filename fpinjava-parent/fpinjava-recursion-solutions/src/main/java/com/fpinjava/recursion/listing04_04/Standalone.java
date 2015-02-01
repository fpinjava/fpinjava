package com.fpinjava.recursion.listing04_04;

import com.fpinjava.common.Function;
import com.fpinjava.recursion.listing04_03.TailCall;
import static com.fpinjava.recursion.listing04_03.TailCall.*;

public class Standalone {

  static Function<Integer, Function<Integer, Integer>> add = x -> y -> {
    class AddHelper {
      Function<Integer, Function<Integer, TailCall<Integer>>> addHelper =
          a -> b -> b == 0
              ? ret(a)
              : sus(() -> this.addHelper.apply(a + 1).apply(b - 1));
    }
    return new AddHelper().addHelper.apply(x).apply(y).eval();
  };
}
