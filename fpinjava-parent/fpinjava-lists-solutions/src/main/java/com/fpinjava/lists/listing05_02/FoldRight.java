package com.fpinjava.lists.listing05_02;

import com.fpinjava.common.Function;
import com.fpinjava.lists.exercise05_06.List;

public class FoldRight {

  public static <A, B> B foldRight(List<A> list, B n,
                                   Function<A, Function<B, B>> f ) {
    return list.isEmpty()
        ? n
        : f.apply(list.head()).apply(foldRight(list.tail(), n, f));
  }

  public static Integer sum(List<Integer> list) {
    return foldRight(list, 0,  x -> y -> x + y);
  }

  public static Double product(List<Double> list) {
    return foldRight(list, 1.0,  x -> y -> x * y);
  }
}
