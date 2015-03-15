package com.fpinjava.datastructures.exercise05_24;

import com.fpinjava.common.Function;
import com.fpinjava.datastructures.exercise05_23.List;

public class Filter {

  public static <A> List<A> filterViaFlatMap(List<A> list, Function<A, Boolean> p) {
    return list.flatMap(a -> p.apply(a)
        ? List.list(a)
        : List.list());
  }
}
